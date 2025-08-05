package com.buzhou.jizu.core;

import com.buzhou.jizu.core.constant.AppConstant;
import com.buzhou.jizu.core.service.LauncherService;
import com.buzhou.jizu.core.utils.PropsUtil;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.*;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BuzhouApplication {
    /**
     * 运行Spring应用程序并返回应用上下文
     *
     * @param appName       应用程序名称
     * @param primarySource 主要配置源类
     * @param args          应用程序启动参数
     * @return 配置好的应用上下文对象
     */
    public static ConfigurableApplicationContext run(String appName, Class<?> primarySource, String... args) {
        // 创建Spring应用构建器实例
        SpringApplicationBuilder builder = createSpringApplicationBuilder(appName, primarySource, args);
        // 使用构建器启动应用并返回应用上下文
        return builder.run(args);
    }

    /**
     * 创建Spring应用构建器
     *
     * @param appName       应用名称
     * @param primarySource 主要源类
     * @param args          应用启动参数
     * @return Spring应用构建器实例
     */
    public static SpringApplicationBuilder createSpringApplicationBuilder(String appName, Class<?> primarySource, String... args) {
        return createSpringApplicationBuilder(appName, null, primarySource, args);
    }

    /**
     * 创建Spring应用构建器实例
     *
     * @param appName       应用名称
     * @param builder       Spring应用构建器
     * @param primarySource 主要源类
     * @param args          应用启动参数
     * @return Spring应用构建器实例
     */
    public static SpringApplicationBuilder createSpringApplicationBuilder(String appName, SpringApplicationBuilder builder, Class<?> primarySource, String... args) {
        Assert.hasText(appName, "[appName]服务名不能为空");
        // 读取环境变量，使用spring boot的规则
        ConfigurableEnvironment environment = new StandardEnvironment();
        MutablePropertySources propertySources = environment.getPropertySources();
        propertySources.addFirst(new SimpleCommandLinePropertySource(args));
        propertySources.addLast(new MapPropertySource(StandardEnvironment.SYSTEM_PROPERTIES_PROPERTY_SOURCE_NAME, environment.getSystemProperties()));
        propertySources.addLast(new SystemEnvironmentPropertySource(StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME, environment.getSystemEnvironment()));
        // 获取配置的环境变量
        String[] activeProfiles = environment.getActiveProfiles();
        // 判断环境:dev、test、prod
        List<String> profiles = Arrays.asList(activeProfiles);
        // 预设的环境
        List<String> presetProfiles = new ArrayList<>(Arrays.asList(AppConstant.DEV_CODE, AppConstant.TEST_CODE, AppConstant.PROD_CODE));
        // 交集
        presetProfiles.retainAll(profiles);
        // 当前使用
        List<String> activeProfileList = new ArrayList<>(profiles);
        Function<Object[], String> joinFun = StringUtils::arrayToCommaDelimitedString;
        if (builder == null) {
            // 如果builder为空 创建 此操作将启动内嵌web容器 以jar模式启动
            builder = new SpringApplicationBuilder(primarySource);
        }
        String profile;
        if (activeProfileList.isEmpty()) {
            // 默认dev开发
            profile = AppConstant.DEV_CODE;
            activeProfileList.add(profile);
            builder.profiles(profile);
        } else if (activeProfileList.size() == 1) {
            profile = activeProfileList.get(0);
        } else {
            // 同时存在dev、test、prod环境时
            throw new RuntimeException("同时存在环境变量:[" + StringUtils.arrayToCommaDelimitedString(activeProfiles) + "]");
        }
        String startJarPath = Objects.requireNonNull(BuzhouApplication.class.getResource("/")).getPath().split("!")[0];
        String activePros = joinFun.apply(activeProfileList.toArray());
        System.out.printf("----启动中，读取到的环境变量:[%s]，jar地址:[%s]----%n", activePros, startJarPath);
        // 获取系统属性
        Properties props = System.getProperties();
        // 设置应用名称属性
        String applicationName = PropsUtil.getProps(props, "spring.application.name", appName);
        PropsUtil.seProps(props, "spring.application.name", applicationName);
        PropsUtil.seProps(props, "spring.profiles.active", profile);
        // 设置应用版本属性
        PropsUtil.seProps(props, "info.version", AppConstant.APPLICATION_VERSION);
        PropsUtil.seProps(props, "info.desc", applicationName);
        PropsUtil.seProps(props, "buzhou.env", profile);
        PropsUtil.seProps(props, "buzhou.name", applicationName);
        PropsUtil.seProps(props, "blade.is-local", String.valueOf(isLocalDev()));
        PropsUtil.seProps(props, "buzhou.dev-mode", profile.equals(AppConstant.PROD_CODE) ? "false" : "true");
        PropsUtil.seProps(props, "spring.main.allow-bean-definition-overriding", "true");
        PropsUtil.seProps(props, "buzhou.service.version", AppConstant.APPLICATION_VERSION);
        // 加载自定义组件
        List<LauncherService> launcherList = new ArrayList<>();
        ServiceLoader.load(LauncherService.class).forEach(launcherList::add);
        SpringApplicationBuilder finalBuilder = builder;
        launcherList.stream().sorted(Comparator.comparing(LauncherService::getOrder)).collect(Collectors.toList())
                .forEach(launcherService -> launcherService.launcher(finalBuilder, appName, profile));
        return finalBuilder;
    }

    /**
     * 判断是否为本地开发环境
     *
     * @return boolean
     */
    public static boolean isLocalDev() {
        String osName = System.getProperty("os.name");
        return StringUtils.hasText(osName) && !(AppConstant.OS_NAME_LINUX.equalsIgnoreCase(osName));
    }

}
