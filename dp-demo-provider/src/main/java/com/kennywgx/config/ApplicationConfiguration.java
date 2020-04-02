package com.kennywgx.config;

import com.github.taccisum.shiro.web.autoconfigure.stateless.support.jwt.Payload;
import com.kennywgx.config.mybatisplus.ApplicationMetaObjectHandler;
import com.kennywgx.util.AuthUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public ApplicationMetaObjectHandler.RuntimeData runtimeData() {

        return new ApplicationMetaObjectHandler.RuntimeData() {
            @Override
            public String getUserId() {
                Payload payload = AuthUtils.getPayload();
                if (null == payload || null == payload.get("userId"))
                    return null;
                return payload.get("userId").toString();
            }

            @Override
            public String getTenantId() {
                return "1";
            }
        };
    }
            
}
