package com.buzhou.jizu.core.utils;

import org.apache.commons.lang3.StringUtils;

public class FuncUtil {

    /**
     * 空值检查函数，如果输入值为null则返回默认值，否则返回原值
     *
     * @param <T>          泛型类型参数
     * @param val          待检查的值
     * @param defaultValue 默认值
     * @return 当val为null时返回defaultValue，否则返回val本身
     */
    public static <T> T nvl(T val, T defaultValue) {
        return val == null ? defaultValue : val;
    }

    /**
     * 判断字符串是否为空
     *
     * @param obj 待判断的字符串对象
     * @return true表示字符串为null或空字符串，false表示字符串不为空
     */
    public static boolean isEmpty(String obj) {
        return StringUtils.isEmpty(obj);
    }

}
