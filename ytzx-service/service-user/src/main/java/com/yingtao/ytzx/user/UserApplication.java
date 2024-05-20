package com.yingtao.ytzx.user;

import com.yingtao.ytzx.common.anno.EnableUserLoginAuthInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Adam
 * @create 2024-04-27 19:39
 */
@SpringBootApplication
@EnableUserLoginAuthInterceptor
@EnableFeignClients(basePackages = {"com.yingtao.ytzx.feign.product"})
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
