package com.lemon.autoconfigure.springcloud.feign;

import feign.Feign;
import feign.RequestInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Enumeration;

/**
 * description: 自定义的请求头处理类，处理服务发送时的请求头
 *
 * @author lemon
 * @date 2019-07-29 14:54:06 创建
 */
@Configuration
@ConditionalOnClass(Feign.class)
public class FeignHeadConfiguration {

    private final Logger logger = LoggerFactory.getLogger(FeignHeadConfiguration.class);

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attrs != null) {
                HttpServletRequest request = attrs.getRequest();
                // 如果在Cookie内通过如下方式取
                Cookie[] cookies = request.getCookies();
                if (cookies != null && cookies.length > 0) {
                    for (Cookie cookie : cookies) {
                        if("SESSION".equals(cookie.getName())){
                            byte[] decodedCookieBytes = Base64.getDecoder().decode(cookie.getValue());
                            requestTemplate.header(cookie.getName(), new String(decodedCookieBytes));
                        }else{
                            requestTemplate.header(cookie.getName(), cookie.getValue());
                        }
                    }
                } else {
                    logger.warn("FeignHeadConfiguration", "获取Cookie失败！");
                }
                // 如果放在header内通过如下方式取
                Enumeration<String> headerNames = request.getHeaderNames();
                if (headerNames != null) {
                    while (headerNames.hasMoreElements()) {
                        String name = headerNames.nextElement();
                        String value = request.getHeader(name);
                        /**
                         * 遍历请求头里面的属性字段，将jsessionid添加到新的请求头中转发到下游服务
                         * */
                        if ("jsessionid".equalsIgnoreCase(name)) {
                            logger.debug("添加自定义请求头key:" + name + ",value:" + value);
                            requestTemplate.header(name, value);
                        } else {
                            logger.debug("FeignHeadConfiguration", "非自定义请求头key:" + name + ",value:" + value + "不需要添加!");
                        }
                    }
                } else {
                    logger.warn("FeignHeadConfiguration", "获取请求头失败！");
                }
            }
        };
    }

    @Bean
    public FeignHystrixConcurrencyStrategy getFeignHystrixConcurrencyStrategy(){
        return new FeignHystrixConcurrencyStrategy();
    }
}
