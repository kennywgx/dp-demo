package com.kennywgx.config;


import com.auth0.jwt.exceptions.JWTVerificationException;
import com.github.taccisum.shiro.web.autoconfigure.stateless.support.extractor.AuthorizationTokenExtractor;
import com.github.taccisum.shiro.web.autoconfigure.stateless.support.extractor.TokenExtractor;
import com.github.taccisum.shiro.web.autoconfigure.stateless.support.jwt.*;
import com.github.taccisum.shiro.web.autoconfigure.stateless.support.jwt.exception.InvalidTokenException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Set;

@Slf4j
@Configuration
public class ShiroConfiguration {
    @Bean
    public Realm realm(JWTManager jwtManager) {
        return new OnlyParseJWTRealm("one", jwtManager) {

            /**
             * 父类授权方法解析token有bug, 这里重写授权方法修复之
             * @param principalCollection
             * @return
             */
            @SuppressWarnings("unchecked")
            @Override
            protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
                Object principal = principalCollection.fromRealm(this.getName()).iterator().next();
                String token = principal instanceof JWTPrincipal ?
                        ((JWTPrincipal) principal).getToken() : principal.toString();
                Payload payload;
                try {
                    payload = jwtManager.parsePayload(token);
                } catch (JWTVerificationException e) {
                    throw new InvalidTokenException(token);
                }
                SimpleAuthorizationInfo authzInfo = new SimpleAuthorizationInfo();
                if (jwtManager.getPayloadTemplate(issuer).hasField("roles", Set.class)) {
                    authzInfo.addRoles((Set<String>) (payload.get("roles")));
                }
                if (jwtManager.getPayloadTemplate(issuer).hasField("permissions", Set.class)) {
                    authzInfo.addStringPermissions((Set<String>) (payload.get("permissions")));
                }
                return authzInfo;
            }
        };
    }

    @Bean
    public PayloadTemplate payloadTemplate() {
        PayloadTemplate payloadTemplate = new DefaultPayloadTemplate("one");
        payloadTemplate.addField("userId", Integer.class);
        payloadTemplate.addField("tenantId", Integer.class);
        payloadTemplate.addField("username", String.class);
        payloadTemplate.addField("roles", Set.class);
        payloadTemplate.addField("permissions", Set.class);
        return payloadTemplate;
    }

    @Bean
    public TokenExtractor tokenExtractor() {
        return new AuthorizationTokenExtractor();
    }
}
        
