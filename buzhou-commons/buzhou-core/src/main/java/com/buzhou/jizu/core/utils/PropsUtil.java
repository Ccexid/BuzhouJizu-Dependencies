package com.buzhou.jizu.core.utils;

import java.util.Properties;

public class PropsUtil {
    /**
     * 设置属性值到Properties对象中
     * 当value不为空时，将key-value对设置到props中
     *
     * @param props Properties对象，用于存储属性键值对
     * @param key   属性键
     * @param value 属性值，当不为空时才会被设置
     */
    public static void seProps(Properties props, String key, String value) {
        // 只有当value不为空时才设置属性值
        if (!FuncUtil.isEmpty(value)) {
            props.setProperty(key, value);
        }
    }

    /**
     * 从Properties对象中获取指定键的值，如果值不存在或为空则返回默认值
     * @param props Properties对象
     * @param key 要获取的属性键
     * @param defaultValue 默认值
     * @return 属性值或默认值
     */
    public static String getProps(Properties props, String key, String defaultValue) {
        // 只有当value不为空时才设置属性值
        String value = props.getProperty(key);
        if (!FuncUtil.isEmpty(value)) {
            return value;
        }
        return defaultValue;
    }


}
