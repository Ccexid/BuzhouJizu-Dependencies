package com.weakwater.framework.core.props;

import com.weakwater.framework.core.utils.PropsUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.env.EnvironmentCapable;
import org.springframework.lang.Nullable;

import java.util.Objects;
import java.util.Properties;

@ConfigurationProperties("weak-water")
public class WeakWaterProperties implements EnvironmentAware, EnvironmentCapable {

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

    /**
     * 根据指定的键获取对应的值
     *
     * @param <T> 泛型类型参数，表示返回值的类型
     * @param key 要获取值的键，不能为空
     * @return 返回与键关联的值，如果键不存在或值为null则返回null
     */
    @Nullable
    public <T> T get(String key) {
        return get(key, null);
    }


    /**
     * 根据指定的键获取属性值，如果键不存在则返回默认值
     *
     * @param <T>          泛型类型参数，表示返回值的类型
     * @param key          属性键，用于查找对应的属性值
     * @param defaultValue 默认值，当指定键不存在时返回此值
     * @return 返回指定键对应的属性值，如果键不存在则返回默认值
     */
    @Nullable
    public <T> T get(String key, @Nullable T defaultValue) {
        return PropsUtil.getProps(prop, key, defaultValue);
    }

    /**
     * 获取配置
     *
     * @param key key
     * @return value
     */
    @Nullable
    public String getString(String key) {
        return get(key, null);
    }

    /**
     * 获取配置
     *
     * @param key key
     * @return int value
     */
    @Nullable
    public Integer getInt(String key) {
        return get(key, null);
    }


    /**
     * 获取配置
     *
     * @param key key
     * @return long value
     */
    @Nullable
    public Long getLong(String key) {
        return get(key, null);
    }


    /**
     * 获取配置
     *
     * @param key key
     * @return Boolean value
     */
    @Nullable
    public Boolean getBoolean(String key) {
        return get(key, null);
    }

    /**
     * 获取配置
     *
     * @param key key
     * @return double value
     */
    @Nullable
    public Double getDouble(String key) {
        return get(key, null);
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
