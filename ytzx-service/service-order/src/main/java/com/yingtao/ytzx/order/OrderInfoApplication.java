package com.yingtao.ytzx.order;

import com.yingtao.ytzx.common.anno.EnableUserLoginAuthInterceptor;
import com.yingtao.ytzx.common.anno.EnableUserTokenFeignInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Adam
 * @create 2024-05-17 21:04
 */
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.yingtao.ytzx.feign.cart", "com.yingtao.ytzx.feign.product",
                                    "com.yingtao.ytzx.feign.user"})
@EnableUserTokenFeignInterceptor
@EnableUserLoginAuthInterceptor
public class OrderInfoApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderInfoApplication.class, args);
    }
}
