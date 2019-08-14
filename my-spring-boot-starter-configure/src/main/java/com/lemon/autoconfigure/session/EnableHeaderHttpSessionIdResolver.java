package com.lemon.autoconfigure.session;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * description: 开启从头信息获取session id
 *
 * @author lemon
 * @date 2019-08-14 15:39:06 创建
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(HttpSessionIdResolverConfig.class)
public @interface EnableHeaderHttpSessionIdResolver {

}
