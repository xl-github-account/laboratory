package priv.xl.springboot.core.minio.exception;

import priv.xl.springboot.core.common.GlobalErrorCodeEnum;

/**
 * MinIO操作异常
 *
 * @author lei.xu
 * 2022/10/24 3:11 下午
 */
public class MinIoOperationException extends RuntimeException {

    private static final int CODE = GlobalErrorCodeEnum.SUCCESS.getCode();

    public MinIoOperationException(String message) {
        super(message);
    }

    public MinIoOperationException() {
        this("MinIO服务器异常");
    }

    public int getCode() {
        return CODE;
    }

}
