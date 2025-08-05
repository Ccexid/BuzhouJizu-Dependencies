package com.buzhou.jizu.core.props;

import com.buzhou.jizu.core.utils.PropsUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.env.EnvironmentCapable;
import org.springframework.lang.Nullable;

import java.util.Objects;
import java.util.Properties;

@ConfigurationProperties("buzhou")
public class BuzhouProperties implements EnvironmentAware, EnvironmentCapable {

    private Environment environment;

    /**
     * 开发环境
     */
    @Getter
    @Setter
    private String env;

    /**
     * 服务名
     */
    @Getter
    @Setter
    private String name;

    /**
     * 判断是否为 本地开发环境
     */
    @Getter
    @Setter
    private Boolean isLocal = Boolean.FALSE;


    @Getter
    private final Properties prop = new Properties();

    @Nullable
    public <T> T get(String key) {
        return get(key, null);
    }

    /**
     * 获取配置
     *
     * @param key          key
     * @param defaultValue 默认值
     * @return value
     */
    @Nullable
    public <T> T get(String key, @Nullable T defaultValue) {
        return PropsUtil.getProps(prop, key, defaultValue);
    }

    /**
     * 判断是否存在key
     *
     * @param key prop key
     * @return boolean
     */
    public boolean containsKey(String key) {
        return prop.containsKey(key);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public Environment getEnvironment() {
        Objects.requireNonNull(environment, "Spring boot 环境下 Environment 不可能为null");
        return this.environment;
    }
}
