package priv.xl.springboot.core.web.http;

import lombok.Getter;
import lombok.Setter;
import priv.xl.springboot.core.common.ErrorCodeBase;
import priv.xl.springboot.core.common.GlobalErrorCodeEnum;

import java.io.Serializable;

/**
 * HTTP请求响应结果
 *
 * @author lei.xu
 * 2023/1/12 4:23 下午
 */
@Getter
@Setter
public class HttpResult implements Serializable {

    private static final long serialVersionUID = -2913681661650963747L;

    private int code;

    private String message;

    private HttpResult() {}

    @Getter
    @Setter
    public static class HttpResultData<R> extends HttpResult {

        private static final long serialVersionUID = 8406703791032632143L;

        private R data;

    }

    public static <R> HttpResult success(String message, R data) {
        HttpResultData<R> httpResult = new HttpResultData<>();
        httpResult.setCode(GlobalErrorCodeEnum.SUCCESS.getCode());
        httpResult.setMessage(message);
        httpResult.setData(data);
        return httpResult;
    }

    public static <R> HttpResult success(R data) {
        return success(GlobalErrorCodeEnum.SUCCESS.getMessage(), data);
    }

    public static HttpResult success() {
        HttpResult httpResult = new HttpResult();
        httpResult.setCode(GlobalErrorCodeEnum.SUCCESS.getCode());
        httpResult.setMessage(GlobalErrorCodeEnum.SUCCESS.getMessage());
        return httpResult;
    }

    public static HttpResult fail(int code, String message) {
        HttpResult httpResult = new HttpResult();
        httpResult.setCode(code);
        httpResult.setMessage(message);
        return httpResult;
    }

    public static HttpResult fail(ErrorCodeBase errorCodeEnum, String message) {
        return fail(errorCodeEnum.getCode(), message);
    }

    public static HttpResult fail(String message) {
        return fail(GlobalErrorCodeEnum.SYSTEM_ERROR, message);
    }

    public static HttpResult fail(ErrorCodeBase errorCodeEnum) {
        HttpResult httpResult = new HttpResult();
        httpResult.setCode(errorCodeEnum.getCode());
        httpResult.setMessage(errorCodeEnum.getMessage());
        return httpResult;
    }

    public static HttpResult fail() {
        return fail(GlobalErrorCodeEnum.SYSTEM_ERROR);
    }

}
