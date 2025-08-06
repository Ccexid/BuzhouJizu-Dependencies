package com.weakwater.framework.core.constant;

public interface AppConstant {
    /**
     * 应用版本
     */
    String APPLICATION_VERSION = "4.0.0";
    /**
     * 开发环境
     */
    String DEV_CODE = "dev";
    /**
     * 生产环境
     */
    String PROD_CODE = "prod";
    /**
     * 测试环境
     */
    String TEST_CODE = "test";

    /**
     * Linux操作系统标识
     */
    String OS_LINUX_CODE = "LINUX";
    /**
     * 默认为空消息
     */
    String DEFAULT_NULL_MESSAGE = "暂无承载数据";
    /**
     * 默认成功消息
     */
    String DEFAULT_SUCCESS_MESSAGE = "操作成功";
    /**
     * 默认失败消息
     */
    String DEFAULT_FAILURE_MESSAGE = "操作失败";
    /**
     * 默认未授权消息
     */
    String DEFAULT_UNAUTHORIZED_MESSAGE = "签名认证失败";
}
