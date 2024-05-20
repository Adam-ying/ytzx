package com.yingtao.ytzx.common.anno;

import com.yingtao.ytzx.common.interceptor.UserLoginAuthInterceptor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Adam
 * @create 2024-05-17 20:58
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
@Import(value = UserLoginAuthInterceptor.class)
public @interface EnableUserTokenFeignInterceptor {
}
