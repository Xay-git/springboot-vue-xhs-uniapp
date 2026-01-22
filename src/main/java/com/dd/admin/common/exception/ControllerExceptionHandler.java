package com.dd.admin.common.exception;

import com.dd.admin.common.model.result.ResultBean;
import com.dd.admin.common.model.result.ResultError;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.dd.admin.common.exception.enums.ApiExceptionEnum.HTTP_NOT_SUPPORT;
import static com.dd.admin.common.exception.enums.ApiExceptionEnum.MESSAGE_NOT_READABLE;

@ControllerAdvice
@Order(-1)
@Slf4j
public class ControllerExceptionHandler {

    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpServletResponse response;

    @ExceptionHandler(ApiException.class)
    @ResponseBody
    public ResultError ApiException(ApiException e) throws IOException {
        log.error("ApiException{}",e.getMsg());
        return ResultError.error(e.getCode(),e.getMessage());
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResultError HttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) throws IOException {
        log.error("HttpRequestMethodNotSupportedException{}",e);
        return ResultError.error(HTTP_NOT_SUPPORT);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResultError HttpMessageNotReadableException(HttpMessageNotReadableException e) throws IOException {
        log.error("HttpMessageNotReadableException{}",e);
        return ResultError.error(MESSAGE_NOT_READABLE);
    }


    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResultError RuntimeException(RuntimeException e) throws IOException {
        log.error("RuntimeException{}",e);
        return ResultError.error(ResultBean.DEFAULT_ERROR_CODE,e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultError Exception(Exception e) throws IOException {
        log.error("Exception{}",e);
        return ResultError.error(ResultBean.DEFAULT_ERROR_CODE,ResultBean.DEFAULT_ERROR_MESSAGE);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseBody
    public ResultError ExpiredJwtException(Exception e) throws IOException {
        log.error("Exception{}",e);
        return ResultError.error(ResultBean.DEFAULT_ERROR_CODE,ResultBean.DEFAULT_ERROR_MESSAGE);
    }

    @ExceptionHandler(BadSqlGrammarException.class)
    @ResponseBody
    public ResultError BadSqlGrammarException(BadSqlGrammarException e) throws IOException {
        log.error("Exception{}",e);
        return ResultError.error(ResultBean.DEFAULT_ERROR_CODE,"数据查询有误");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResultError MethodArgumentNotValidException(MethodArgumentNotValidException e) throws IOException {
        log.error("MethodArgumentNotValidException{}",e);
           List<ObjectError> list = e.getAllErrors();
           String ERROR = list.get(0).getDefaultMessage();
        return ResultError.error(ResultBean.DEFAULT_ERROR_CODE,ERROR);
    }
}
