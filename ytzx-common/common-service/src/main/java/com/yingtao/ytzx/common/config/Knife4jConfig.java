package com.yingtao.ytzx.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Adam
 * @create 2024-04-11 20:13
 */
@Configuration
public class Knife4jConfig {

    @Bean
    public GroupedOpenApi adminApi(){
        return GroupedOpenApi.builder().group("admin_api")
                .pathsToMatch("/admin/**")
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI().info(new Info()
                .title("尚品甑选API接口文档")
                .version("1.0")
                .description("尚品甑选API接口文档")
                .contact(new Contact().name("atguigu")));
    }

}
