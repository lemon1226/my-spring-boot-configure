package com.lemon.autoconfigure.web;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Map;

/**
 * description: web配置类
 *
 * @author lemon
 * @date 2019-05-21 14:09:06 创建
 */
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class MyWebAutoConfiguration {

    @Configuration
    protected static class InterceptorAutoConfiguration implements WebMvcConfigurer {

        private ApplicationContext applicationContext;

        public InterceptorAutoConfiguration(ApplicationContext applicationContext) {
            this.applicationContext = applicationContext;
        }

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            Map<String, HandlerInterceptor> handlerInterceptorMap = this.applicationContext.getBeansOfType(HandlerInterceptor.class);
            handlerInterceptorMap.entrySet().stream().map(map -> map.getValue()).forEach(item -> {
                registry.addInterceptor(item);
            });
        }
    }
}
