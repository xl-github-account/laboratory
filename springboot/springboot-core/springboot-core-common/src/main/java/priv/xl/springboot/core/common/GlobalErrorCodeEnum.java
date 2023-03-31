package priv.xl.springboot.core.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 全局错误码枚举
 *
 * @author lei.xu
 * 2023/1/12 4:26 下午
 */
@Getter
@AllArgsConstructor
public enum GlobalErrorCodeEnum implements ErrorCodeBase {

    /**
     * 程序响应正常
     */
    SUCCESS(0, "Success"),

    /**
     * 服务器异常
     */
    SYSTEM_ERROR(1000, "Server Exception"),

    /**
     * 参数错误
     */
    PARAMS_ERROR(1001, "Parameter Error"),

    /**
     * 远程服务不可用
     */
    REMOTE_SERVICE_ERROR(1002, "Remote Service Error");

    private int code;

    private String message;

    public static GlobalErrorCodeEnum match(int code) {
        return Arrays.stream(values())
                .filter(e -> e.getCode() == code)
                .findFirst().orElse(null);
    }

}
