package org.bbaemin.config.advice;

import org.bbaemin.config.response.ApiResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResult<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        return ApiResult.badRequest(fieldErrors);
    }

    @ExceptionHandler(RuntimeException.class)
    public ApiResult<?> handleRuntimeException(RuntimeException e) {
        return ApiResult.internalServerError(e);
    }

    @ExceptionHandler(Exception.class)
    public ApiResult<?> handleException(Exception e) {
        return ApiResult.internalServerError(e);
    }
}
