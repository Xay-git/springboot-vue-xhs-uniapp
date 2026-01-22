package com.dd.admin.common.exception.enums;

public enum SystemExceptionEnum implements AbstractBaseExceptionEnum{
    DEPT_ALREADY_EXIST(500,"该机构已存在"),
    DEPT_ALREADY_EXPIRE(500,"门店有效期已过,请及时联系管理员续费~"),
    USERNAME_ALREADY_EXIST(500,"该用户名已被占用"),
    TECH_LOGIN_WX(500,"服务人员请在公众号登陆~"),
    USER_IS_FREEZE(500,"该用户已锁定,请联系管理员"),
    USER_PASSWORD_ERROR(500,"用户历史密码错误");
    ;

    SystemExceptionEnum(Integer code, String message) {
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
