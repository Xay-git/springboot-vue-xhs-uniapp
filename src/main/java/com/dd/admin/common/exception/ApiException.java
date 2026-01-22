package com.dd.admin.common.exception;

import com.dd.admin.common.exception.enums.AbstractBaseExceptionEnum;
import lombok.Data;

/**
 * 接口异常实现类
 */
@Data
public class ApiException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private Integer code;
    private String msg;

    public ApiException(Integer code, String errorMessage) {
        super(errorMessage);
        this.code = code;
        this.msg = errorMessage;
    }

    public ApiException(String errorMessage) {
        super(errorMessage);
        this.code = 500;
        this.msg = errorMessage;
    }


    public ApiException(AbstractBaseExceptionEnum exception) {
        super(exception.getMessage());
        this.code = exception.getCode();
        this.msg = exception.getMessage();
    }


}
