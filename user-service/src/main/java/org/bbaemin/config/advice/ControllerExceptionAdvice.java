package org.bbaemin.config.advice;

import org.bbaemin.config.response.ApiResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ApiResult<?> handleRuntimeException(RuntimeException e) {
        return ApiResult.internalServerError(e);
    }

    @ExceptionHandler(Exception.class)
    public ApiResult<?> handleException(Exception e) {
        return ApiResult.internalServerError(e);
    }
}
