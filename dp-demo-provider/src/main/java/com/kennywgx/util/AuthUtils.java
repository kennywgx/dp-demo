package com.kennywgx.util;

import com.github.taccisum.shiro.web.autoconfigure.stateless.support.jwt.JWTPrincipal;
import com.github.taccisum.shiro.web.autoconfigure.stateless.support.jwt.Payload;
import org.apache.shiro.SecurityUtils;

public abstract class AuthUtils {
    public static String getToken() {
        return getPrincipal().getToken();
    }

    public static Payload getPayload() {
        JWTPrincipal principal = getPrincipal();
        return principal == null ? null : principal.getPayload();
    }

    private static JWTPrincipal getPrincipal() {
        return (JWTPrincipal) SecurityUtils.getSubject().getPrincipal();
    }
}
