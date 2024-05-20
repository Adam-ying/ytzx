package com.yingtao.ytzx.manager;

import com.yingtao.ytzx.common.log.annotation.EnableLogAspect;
import com.yingtao.ytzx.manager.properties.MinioProperties;
import com.yingtao.ytzx.manager.properties.UserAuthProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Adam
 * @create 2024-04-13 19:12
 */
@EnableLogAspect
@SpringBootApplication
@EnableConfigurationProperties(value = {UserAuthProperties.class, MinioProperties.class})
@EnableScheduling
public class ManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class, args);
    }
}
