package com.lemon.framework.context;

import com.lemon.baseutils.util.TokenUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;
import java.util.function.BiFunction;

/**
 * description: request上下文
 *
 * @author lemon
 * @date 2019-07-29 15:22:06 创建
 */
public class RequestContext {

    private static ThreadLocal<HttpServletRequest> localRequest = new ThreadLocal<HttpServletRequest>();

    private static final String SESSION_USER_KEY = "userInfo";

    public static void setRequest(HttpServletRequest request) {
        localRequest.set(request);
    }

    public static HttpServletRequest getLocalRequest(){
        return localRequest.get();
    }

    public static String getFeignUsername(){
        return (String) getFeignValue(TokenUtils::getUsernameFromToken);
    }

    public static List<String> getFeignAuthorities(){
        return (List<String>) getFeignValue(TokenUtils::getAuthoritiesFromToken);
    }

    private static <T> Object getFeignValue(BiFunction<String, String, T> biFunction){
        String token = null;
        String secret = null;
        HttpServletRequest request = getLocalRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                String value = request.getHeader(name);
                if ("lemon_cookie".equalsIgnoreCase(name)) {
                    token = value;
                }
                if ("secret".equalsIgnoreCase(name)) {
                    secret = value;
                }
            }
        }
        return biFunction.apply(token, secret);
    }

    public static void setSession(String key, Object value){
        getLocalRequest().getSession().setAttribute(key, value);
    }

    public static Object getSession(String key){
        return getLocalRequest().getSession().getAttribute(key);
    }

    public static void setSessionUser(Object value){
        setSession(SESSION_USER_KEY, value);
    }

    public static Object getSessionUser(){
        return getSession(SESSION_USER_KEY);
    }
}
