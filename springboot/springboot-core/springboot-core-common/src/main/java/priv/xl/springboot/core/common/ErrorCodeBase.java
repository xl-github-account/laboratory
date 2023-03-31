package priv.xl.springboot.core.common;

/**
 * 异常码自定义接口
 * 定义为异常信息枚举接口
 *
 * @author lei.xu
 * 2023/1/17 1:25 下午
 */
public interface ErrorCodeBase {

    /**
     * 获取异常码
     *
     * @return 异常码
     */
    int getCode();

    /**
     * 获取异常消息
     *
     * @return 异常消息
     */
    String getMessage();

}
