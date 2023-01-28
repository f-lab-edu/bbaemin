package org.bbaemin.config.response;

import org.springframework.http.HttpStatus;

public class ApiResult<T> {

    private Integer code;
    private HttpStatus httpStatus;

    private String message;
    private T result;

    // TODO - error

    public ApiResult(Integer code, HttpStatus httpStatus, String message, T result) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
        this.result = result;
    }
}
