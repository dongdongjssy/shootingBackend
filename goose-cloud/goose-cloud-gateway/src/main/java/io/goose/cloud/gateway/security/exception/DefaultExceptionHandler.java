package io.goose.cloud.gateway.security.exception;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.goose.cloud.gateway.security.domain.ResultCode;
import io.goose.cloud.gateway.security.domain.ResultJson;

/**
 * @author goose
 * 异常处理类
 * controller层异常无法捕获处理，需要自己处理
 * Created at 2019/4/1.
 */
@RestControllerAdvice
@Slf4j
public class DefaultExceptionHandler {

    /**
     * 处理所有自定义异常
     * @param e
     * @return
     */

    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(CustomException.class)
    public ResultJson handleCustomException(CustomException e){
        log.error(e.getResultJson().getMsg().toString());

        return e.getResultJson();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(TokenExpiredException.class)
    public ResultJson handleTokenExpiredException(TokenExpiredException e){
        log.error(e.getResultJson().getMsg().toString());

        return ResultJson.failure(ResultCode.EXCEPTION_TOKEN_EXPIRED, "Token is expired");
    }


    /**
     * 处理参数校验异常
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultJson handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.error(e.getBindingResult().getFieldError().getField() + e.getBindingResult().getFieldError().getDefaultMessage());
        return ResultJson.failure(ResultCode.BAD_REQUEST, e.getBindingResult().getFieldError().getDefaultMessage());
    }
}
