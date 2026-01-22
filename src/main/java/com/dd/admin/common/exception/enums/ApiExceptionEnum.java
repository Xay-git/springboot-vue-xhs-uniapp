package com.dd.admin.common.exception.enums;


public enum ApiExceptionEnum implements AbstractBaseExceptionEnum {
    ACCESS_DENIED_EXCEPTION(401,"未登录"),
    USERNAME_IS_NOT_NULL(5000,"用户名不能为空"),
    USERNAME_OR_PASSWORD_ERROR(401,"用户名或密码错误"),
    TOKEN_EXPIRED(700, "token过期"),
    TOKEN_ERROR(700, "token验证失败"),
    MESSAGE_NOT_READABLE(400, "参数解析失败"),
    HTTP_NOT_SUPPORT(400, "请求方式不支持"),

    ;


    ApiExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;

    private String message;


    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
