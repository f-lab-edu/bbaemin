package org.bbaemin.config.response;


import lombok.Getter;

import static org.bbaemin.config.response.ApiResult.ResultCode.CREATED;
import static org.bbaemin.config.response.ApiResult.ResultCode.FAIL;
import static org.bbaemin.config.response.ApiResult.ResultCode.SUCCESS;

@Getter
public class ApiResult<T> {

    private static final ApiResult<Void> OK = new ApiResult<>(SUCCESS);

    public enum ResultCode {
        SUCCESS(200),
        CREATED(201),
        FAIL(500);

        private int code;

        ResultCode(int code) {
            this.code = code;
        }
    }

    @Getter
    public static class Error<R> {

        private Integer statusCode;
        private R cause;

        public Error(Integer statusCode, R cause) {
            this.statusCode = statusCode;
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
        return ApiResult.error(500, String.format("[%s] %s", e.getClass().getSimpleName(), e.getMessage()));
    }

    public static <R> ApiResult<Error<R>> badRequest(R cause) {
        return ApiResult.error(400, cause);
    }

    private static <R> ApiResult<Error<R>> error(Integer statusCode, R cause) {
        return new ApiResult<>(FAIL, new Error<>(statusCode, cause));
    }

    public static ApiResult<Void> ok() {
        return ApiResult.OK;
    }

    public static <T> ApiResult<T> created(T result) {
        return new ApiResult<>(CREATED, result);
    }

    public static <T> ApiResult<T> ok(T result) {
        return new ApiResult<>(SUCCESS, result);
    }
}
