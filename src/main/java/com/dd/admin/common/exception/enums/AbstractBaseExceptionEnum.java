package com.dd.admin.common.exception.enums;

/**
 * 基础的异常枚举类
 */
public interface AbstractBaseExceptionEnum {
    /**
     * 获取异常的状态码
     */
    Integer getCode();

    /**
     * 获取异常的提示信息
     */
    String getMessage();
}
