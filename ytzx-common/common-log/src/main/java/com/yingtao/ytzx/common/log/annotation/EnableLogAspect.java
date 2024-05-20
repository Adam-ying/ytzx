package com.yingtao.ytzx.common.log.annotation;

import com.yingtao.ytzx.common.log.aspect.LogAspect;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Adam
 * @create 2024-04-24 19:47
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(LogAspect.class)
public @interface EnableLogAspect {
}
