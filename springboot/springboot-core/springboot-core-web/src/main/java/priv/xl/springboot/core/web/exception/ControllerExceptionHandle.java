package priv.xl.springboot.core.web.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import priv.xl.springboot.core.common.GlobalException;
import priv.xl.springboot.core.web.http.HttpResult;

import javax.servlet.http.HttpServletRequest;

/**
 * 接口层异常处理器
 *
 * @author lei.xu
 * 2023/1/12 5:04 下午
 */
@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandle {

    /**
     * 全局异常处理
     *
     * @param ex 捕获的全局异常
     * @return HTTP响应结果
     */
    @ExceptionHandler(GlobalException.class)
    public HttpResult globalExceptionHandler(GlobalException ex) {
        return HttpResult.fail(ex.getCode(), ex.getMessage());
    }

    /**
     * 其他服务异常
     */
    @ExceptionHandler(Exception.class)
    public HttpResult allExceptionHandle(HttpServletRequest request, Object handler, Exception e) {
        String message;
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            message = String.format("接口 [%s] 出现异常，方法：%s.%s，异常摘要：%s",
                    request.getRequestURI(),
                    handlerMethod.getBean().getClass().getName(),
                    handlerMethod.getMethod().getName(),
                    e.getMessage());
        } else {
            message = e.getMessage();
        }
        log.error(message, e);
        return HttpResult.fail(e.getMessage());
    }

}
