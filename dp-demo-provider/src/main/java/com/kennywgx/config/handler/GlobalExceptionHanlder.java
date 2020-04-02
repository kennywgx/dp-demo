package com.kennywgx.config.handler;

import com.kennywgx.config.web.DemoResponse;
import com.kennywgx.exception.DemoException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.Iterator;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHanlder {

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public DemoResponse<String> handleException(Exception e) {
        log.error("系统内部异常，异常信息", e);
        return DemoResponse.fail("后台出现未知错误");
    }

    @ExceptionHandler(value = DemoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public DemoResponse<String> handleDemoException(DemoException e) {
        return DemoResponse.fail(e.getCode(), null, e.getMessage());
    }


    /**
     * 登录失败异常处理
     */
    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public DemoResponse<String> handleAuthenticationException(Exception e) {
        return DemoResponse.fail(e.getMessage());
    }

    /**
     * 无权限异常处理
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = UnauthorizedException.class)
    public DemoResponse<String> handleUnauthorizedException(Exception e) {
//        return DemoResponse.fail("无权限");
        return DemoResponse.fail(e.getMessage());
    }

    /**
     * JSR-303 validation参数检验失败异常处理
     */
    @ExceptionHandler(value = {
            ConstraintViolationException.class,
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public DemoResponse<String> handleConstraintViolationException(ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream()
                .map(cv -> {
                    if (cv == null) {
                        return "";
                    } else {
                        Iterator<Path.Node> iterator = cv.getPropertyPath().iterator();
                        Path.Node node = null;
                        while (iterator.hasNext()) {
                            node = iterator.next();
                        }
                        return node != null ? node.getName() + ":" + cv.getMessage() : "null";
                    }
                })
                .collect(Collectors.joining("; "));
        return DemoResponse.fail(message);
    }

    /**
     * Spring validation参数校验失败异常处理
     */
    @ExceptionHandler(value = {BindException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public DemoResponse<String> handleBindException(Exception e) {
        BindingResult bindingResult;
        if (e instanceof BindException)
            bindingResult = ((BindException) e).getBindingResult();
        else if (e instanceof MethodArgumentNotValidException)
            bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
        else
            return DemoResponse.fail(e.getMessage());

        String message = bindingResult.getAllErrors().stream()
                .map(err -> {
                    // 转为DefaultMessageSourceResolvable再一层一层往下找到真正的error信息
                    DefaultMessageSourceResolvable cause = err;
                    while (cause.getArguments() != null && cause.getArguments().length > 0 && !(cause.getArguments()[0] instanceof String)) {
                        cause = (DefaultMessageSourceResolvable) cause.getArguments()[0];
                    }
                    return cause.getDefaultMessage() + ":" + err.getDefaultMessage();
                })
                .collect(Collectors.joining("; "));
        return DemoResponse.fail(message);
    }
}
