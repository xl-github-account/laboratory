package priv.xl.springboot.core.common;

import lombok.Getter;

/**
 * 全局异常
 *
 * @author lei.xu
 * 2023/1/12 4:48 下午
 */
@Getter
public class GlobalException extends RuntimeException {

    private static final long serialVersionUID = -7500127321822560448L;

    private final int code;

    protected GlobalException(ErrorCodeBase errorCodeEnum, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = errorCodeEnum.getCode();
    }

    protected GlobalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        this(GlobalErrorCodeEnum.SYSTEM_ERROR, message, cause, enableSuppression, writableStackTrace);
    }

    public GlobalException(ErrorCodeBase errorCodeEnum, String message, Throwable cause) {
        super(message, cause);
        this.code = errorCodeEnum.getCode();
    }
    public GlobalException(String message, Throwable cause) {
        this(GlobalErrorCodeEnum.SYSTEM_ERROR, message, cause);
    }

    public GlobalException(ErrorCodeBase errorCodeEnum, Throwable cause) {
        super(cause);
        this.code = errorCodeEnum.getCode();
    }

    public GlobalException(Throwable cause) {
        this(GlobalErrorCodeEnum.SYSTEM_ERROR, cause);
    }

    public GlobalException(ErrorCodeBase errorCodeEnum, String message) {
        super(message);
        this.code = errorCodeEnum.getCode();
    }

    public GlobalException(ErrorCodeBase errorCodeEnum) {
        this(errorCodeEnum, errorCodeEnum.getMessage());
    }

    public GlobalException(String message) {
        this(GlobalErrorCodeEnum.SYSTEM_ERROR, message);
    }

    public GlobalException() {
        this(GlobalErrorCodeEnum.SYSTEM_ERROR.getMessage());
    }

}
