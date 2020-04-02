//package com.kennywgx.config.handler;
//
//import com.kennywgx.config.web.DemoResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@Slf4j
//@RestControllerAdvice
//@Order(value = Ordered.HIGHEST_PRECEDENCE)
//public class GlobalExceptionHanlder {
//
//    @ExceptionHandler(value = RuntimeException.class)
//    public DemoResponse<String> handleException(Exception e) {
//        log.error("系统内部异常，异常信息", e);
//        return DemoResponse.fail(e.getMessage());
//    }
//}
