package com.kennywgx.exception;

import lombok.Data;

@BizErrorResponseStatus("D-SC-1001")
@Data
public class DemoException extends RuntimeException {

    private String code = "-1";

    public DemoException() {};

    public DemoException(String msg) {
        super(msg);
    }

    public DemoException(String code, String msg) {
        super(msg);
        this.code = code;
    }

    public DemoException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
