package com.kennywgx.config.web;

import lombok.Data;

@Data
public class DemoResponse<T> {

    private DemoResponse() {}

    private boolean success;
    private String code;
    private String message;
    private T payload;

    public static <T> DemoResponse<T> success(T payload) {
        return success(payload, null);
    }

    public static <T> DemoResponse<T> success(T payload, String message) {
        return getInstance(true, "1", payload, message);
    }

    public static <T> DemoResponse<T> fail(String code, T payload, String message) {
        return getInstance(false, code, payload, message);
    }

    public static <T> DemoResponse<T> fail(T payload, String message) {
        return fail("-1", payload, message);
    }

    public static <T> DemoResponse<T> fail(String message) {
        return fail(null, message);
    }

    private static <T> DemoResponse<T> getInstance(boolean success,
                                                   String code,
                                                   T payload,
                                                   String message) {
        DemoResponse<T> objectDemoResponse = new DemoResponse<>();
        objectDemoResponse.setSuccess(success);
        objectDemoResponse.setCode(code);
        objectDemoResponse.setPayload(payload);
        objectDemoResponse.setMessage(message);
        return objectDemoResponse;
    }

}
