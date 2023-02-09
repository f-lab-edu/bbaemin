package org.bbaemin.config.response;

import org.springframework.http.HttpStatus;

public class Error<R> {

    private HttpStatus httpStatus;
    private R cause;

    public Error(HttpStatus httpStatus, R cause) {
        this.httpStatus = httpStatus;
        this.cause = cause;
    }
}
