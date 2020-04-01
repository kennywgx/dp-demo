package com.kennywgx.exception;

@BizErrorResponseStatus("D-SC-1001")
public class DemoException extends RuntimeException {

    public DemoException() {};

    public DemoException(String msg) {
        super(msg);
    }

    public DemoException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
