package org.bbaemin.config.response;


import org.springframework.http.HttpStatus;

import static org.bbaemin.config.response.ApiResult.ResultCode.FAIL;
import static org.bbaemin.config.response.ApiResult.ResultCode.SUCCESS;

public class ApiResult<T> {

    private static final ApiResult<Void> OK = new ApiResult<>(SUCCESS);

    enum ResultCode {
        SUCCESS(200), FAIL(500);

        private int code;

        ResultCode(int code) {
            this.code = code;
        }
    }

    public static class Error<R> {
        private HttpStatus httpStatus;
        private R cause;

        public Error(HttpStatus httpStatus, R cause) {
            this.httpStatus = httpStatus;
            this.cause = cause;
        }
    }

    private ResultCode code;
    private T result;

    private ApiResult(ResultCode code) {
        this.code = code;
    }

    private ApiResult(ResultCode code, T result) {
        this.code = code;
        this.result = result;
    }

    public static ApiResult<Error<String>> internalServerError(Exception e) {
        return ApiResult.error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    public static <R> ApiResult<Error<R>> badRequest(R cause) {
        return ApiResult.error(HttpStatus.BAD_REQUEST, cause);
    }

    public static <R> ApiResult<Error<R>> error(HttpStatus httpStatus, R cause) {
        return new ApiResult<>(FAIL, new Error<>(httpStatus, cause));
    }

    public static ApiResult<Void> ok() {
        return ApiResult.OK;
    }

    public static <T> ApiResult<T> ok(T result) {
        return new ApiResult<>(SUCCESS, result);
    }
}
