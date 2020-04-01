package com.kennywgx.config;

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
                Optional<Object> userId = Optional.ofNullable(AuthUtils.getPayload().get("userId"));
                return userId.orElse("0").toString();
            }

            @Override
            public String getTenantId() {
                return "1";
            }
        };
    }
            
}
