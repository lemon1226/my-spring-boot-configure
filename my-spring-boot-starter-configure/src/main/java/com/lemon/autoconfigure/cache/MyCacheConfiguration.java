package com.lemon.autoconfigure.cache;

import com.lemon.framework.annotation.MyCacheEvict;
import com.lemon.framework.annotation.MyCacheable;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * description: cacheConfiguration
 *
 * @author lemon
 * @date 2019-06-26 13:19:06 创建
 */
@Configuration
public class MyCacheConfiguration {
    private static ExpressionParser parser = new SpelExpressionParser();

    @Bean(name = "myParamKeyGenerator")
    public KeyGenerator myParamKeyGenerator(){
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                MyCacheable myCacheable = AnnotationUtils.findAnnotation(method, MyCacheable.class);
                MyCacheEvict myCacheEvict = AnnotationUtils.findAnnotation(method, MyCacheEvict.class);

                if(null != myCacheable || null != myCacheEvict){
                    String newKey = myCacheable != null? myCacheable.newKey() : myCacheEvict.newKey();

                    Parameter[] parameters = method.getParameters();

                    if(parameters.length == 0){
                        return "all";
                    }

                    StandardEvaluationContext context = new StandardEvaluationContext();

                    for (int i = 0; i< parameters.length; i++) {
                        context.setVariable(parameters[i].getName(), params[i]);
                    }

                    Expression expression = parser.parseExpression(newKey);
                    return expression.getValue(context, String.class);
                }
                return params[0].toString();
            }
        };
    }
}