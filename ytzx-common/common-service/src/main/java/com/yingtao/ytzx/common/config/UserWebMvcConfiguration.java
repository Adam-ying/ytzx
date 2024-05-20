package com.yingtao.ytzx.common.config;

import com.yingtao.ytzx.common.interceptor.UserLoginAuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Adam
 * @create 2024-04-29 19:28
 */
@Configuration
public class UserWebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private UserLoginAuthInterceptor userLoginAuthInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userLoginAuthInterceptor).addPathPatterns("/api/**");
    }
}
