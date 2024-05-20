package com.yingtao.ytzx.pay;

import com.yingtao.ytzx.pay.properties.AlipayProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Adam
 * @create 2024-05-18 21:19
 */
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.yingtao.ytzx.feign.order", "com.yingtao.ytzx.feign.product"})
@EnableConfigurationProperties(value = { AlipayProperties.class})
public class PayApplication {
    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class, args);
    }
}
