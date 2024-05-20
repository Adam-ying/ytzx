package com.yingtao.ytzx.common.anno;

import com.yingtao.ytzx.common.config.UserWebMvcConfiguration;
import com.yingtao.ytzx.common.interceptor.UserLoginAuthInterceptor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Adam
 * @create 2024-04-29 19:31
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
@Import(value = {UserLoginAuthInterceptor.class, UserWebMvcConfiguration.class})
public @interface EnableUserLoginAuthInterceptor {
}
