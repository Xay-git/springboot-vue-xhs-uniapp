package com.dd.admin.common.model.result;


import com.dd.admin.common.exception.enums.AbstractBaseExceptionEnum;

public class ResultError<T> extends ResultBean {

    public ResultError(Integer code, String message, T data) {
        super(code, message, data);
    }

    public ResultError(AbstractBaseExceptionEnum exception) {
        super(exception.getCode(), exception.getMessage(), null);
    }

    public ResultError(String message) {
        super(DEFAULT_ERROR_CODE, message,null);
    }
}
