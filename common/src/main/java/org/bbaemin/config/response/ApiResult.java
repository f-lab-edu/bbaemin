package org.bbaemin.config.response;


import lombok.Getter;
import org.springframework.http.HttpStatus;

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

        private HttpStatus status;
        private R cause;

        public Error(HttpStatus status, R cause) {
            this.status = status;
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
        return ApiResult.error(HttpStatus.INTERNAL_SERVER_ERROR, String.format("[%s] %s", e.getClass().getSimpleName(), e.getMessage()));
    }

    public static <R> ApiResult<Error<R>> badRequest(R cause) {
        return ApiResult.error(HttpStatus.BAD_REQUEST, cause);
    }

    private static <R> ApiResult<Error<R>> error(HttpStatus status, R cause) {
        return new ApiResult<>(FAIL, new Error<>(status, cause));
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
