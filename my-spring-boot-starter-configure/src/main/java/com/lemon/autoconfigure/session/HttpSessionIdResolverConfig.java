package com.lemon.autoconfigure.session;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;

/**
 * description: 这是为了应对feign从头信息获取用户信息的配置类
 *
 * @author lemon
 * @date 2019-08-14 15:45:06 创建
 */
@Configuration
@ConditionalOnClass(HeaderHttpSessionIdResolver.class)
@ConditionalOnProperty("mySession.name")
public class HttpSessionIdResolverConfig {

    @Value("${mySession.name}")
    private String sessionName;

    @Bean
    public HeaderHttpSessionIdResolver httpSessionStrategy() {
        return new HeaderHttpSessionIdResolver(sessionName);
    }
}
