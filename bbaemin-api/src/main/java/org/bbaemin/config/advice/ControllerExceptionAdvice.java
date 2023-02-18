package org.bbaemin.config.advice;

import org.bbaemin.config.exception.BindException;
import org.bbaemin.config.response.ApiResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler(BindException.class)
    public ApiResult<?> handleBindException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        return ApiResult.badRequest(fieldErrors);
    }
}
