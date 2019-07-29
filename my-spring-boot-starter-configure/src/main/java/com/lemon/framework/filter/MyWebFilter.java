package com.lemon.framework.filter;

import com.lemon.framework.context.RequestContext;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * description: 公共过滤器
 *
 * @author lemon
 * @date 2019-07-29 15:37:06 创建
 */
public class MyWebFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        RequestContext.setRequest((HttpServletRequest) servletRequest);
    }
}
