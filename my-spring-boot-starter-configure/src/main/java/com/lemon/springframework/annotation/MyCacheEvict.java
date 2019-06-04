package com.lemon.springframework.annotation;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * description: 删除缓存注解
 *
 * @author lemon
 * @date 2019-06-04 14:27:06 创建
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@CacheEvict
public @interface MyCacheEvict {

    String newKey() default "";

    /**
     * Alias for {@link #cacheNames}.
     */
    @AliasFor("cacheNames")
    String[] value() default {};

    /**
     * Names of the caches to use for the cache eviction operation.
     * <p>Names may be used to determine the target cache (or caches), matching
     * the qualifier value or bean name of a specific bean definition.
     * @since 4.2
     * @see #value
     * @see CacheConfig#cacheNames
     */
    @AliasFor("value")
    String[] cacheNames() default {};

    /**
     * Spring Expression Language (SpEL) expression for computing the key dynamically.
     * <p>Default is {@code ""}, meaning all method parameters are considered as a key,
     * unless a custom {@link #keyGenerator} has been set.
     * <p>The SpEL expression evaluates against a dedicated context that provides the
     * following meta-data:
     * <ul>
     * <li>{@code #result} for a reference to the result of the method invocation, which
     * can only be used if {@link #beforeInvocation()} is {@code false}. For supported
     * wrappers such as {@code Optional}, {@code #result} refers to the actual object,
     * not the wrapper</li>
     * <li>{@code #root.method}, {@code #root.target}, and {@code #root.caches} for
     * references to the {@link java.lang.reflect.Method method}, target object, and
     * affected cache(s) respectively.</li>
     * <li>Shortcuts for the method name ({@code #root.methodName}) and target class
     * ({@code #root.targetClass}) are also available.
     * <li>Method arguments can be accessed by index. For instance the second argument
     * can be accessed via {@code #root.args[1]}, {@code #p1} or {@code #a1}. Arguments
     * can also be accessed by name if that information is available.</li>
     * </ul>
     */
    String key() default "";

    /**
     * The bean name of the custom {@link org.springframework.cache.interceptor.KeyGenerator}
     * to use.
     * <p>Mutually exclusive with the {@link #key} attribute.
     * @see CacheConfig#keyGenerator
     */
    String keyGenerator() default "";

    /**
     * The bean name of the custom {@link org.springframework.cache.CacheManager} to use to
     * create a default {@link org.springframework.cache.interceptor.CacheResolver} if none
     * is set already.
     * <p>Mutually exclusive with the {@link #cacheResolver} attribute.
     * @see org.springframework.cache.interceptor.SimpleCacheResolver
     * @see CacheConfig#cacheManager
     */
    String cacheManager() default "";

    /**
     * The bean name of the custom {@link org.springframework.cache.interceptor.CacheResolver}
     * to use.
     * @see CacheConfig#cacheResolver
     */
    String cacheResolver() default "";

    /**
     * Spring Expression Language (SpEL) expression used for making the cache
     * eviction operation conditional.
     * <p>Default is {@code ""}, meaning the cache eviction is always performed.
     * <p>The SpEL expression evaluates against a dedicated context that provides the
     * following meta-data:
     * <ul>
     * <li>{@code #root.method}, {@code #root.target}, and {@code #root.caches} for
     * references to the {@link java.lang.reflect.Method method}, target object, and
     * affected cache(s) respectively.</li>
     * <li>Shortcuts for the method name ({@code #root.methodName}) and target class
     * ({@code #root.targetClass}) are also available.
     * <li>Method arguments can be accessed by index. For instance the second argument
     * can be accessed via {@code #root.args[1]}, {@code #p1} or {@code #a1}. Arguments
     * can also be accessed by name if that information is available.</li>
     * </ul>
     */
    String condition() default "";

    /**
     * Whether all the entries inside the cache(s) are removed.
     * <p>By default, only the value under the associated key is removed.
     * <p>Note that setting this parameter to {@code true} and specifying a
     * {@link #key} is not allowed.
     */
    boolean allEntries() default false;

    /**
     * Whether the eviction should occur before the method is invoked.
     * <p>Setting this attribute to {@code true}, causes the eviction to
     * occur irrespective of the method outcome (i.e., whether it threw an
     * exception or not).
     * <p>Defaults to {@code false}, meaning that the cache eviction operation
     * will occur <em>after</em> the advised method is invoked successfully (i.e.,
     * only if the invocation did not throw an exception).
     */
    boolean beforeInvocation() default false;
}
