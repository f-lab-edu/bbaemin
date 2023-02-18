package org.bbaemin.config.response;


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

    private ResultCode code;
    private T result;

    private ApiResult(ResultCode code) {
        this.code = code;
    }

    private ApiResult(ResultCode code, T result) {
        this.code = code;
        this.result = result;
    }

    public static ApiResult<Void> ok() {
        return ApiResult.OK;
    }

    public static <T> ApiResult<T> ok(T result) {
        return new ApiResult<>(SUCCESS, result);
    }
}
