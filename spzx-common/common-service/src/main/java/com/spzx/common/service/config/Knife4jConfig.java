package com.spzx.common.service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ljl
 * @create 2023-10-22-16:04
 */

@Configuration
public class Knife4jConfig {

    @Bean
    public GroupedOpenApi webappApi() {
        return GroupedOpenApi.builder()
                .group("webapp-api")         //分组名称
                .pathsToMatch("/api/**")    //接口请求路径规则
                .build();
    }

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("admin-api")         // 分组名称
                .pathsToMatch("/admin/**")  //接口请求路径规则
                .build();
    }

    /***
     * @description 自定义接口信息
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                        .title("尚品甄选API接口文档")
                        .version("1.0")
                        .description("尚品甄选API接口文档")
                        .contact(new Contact().name("姚才康"))); // 设定作者
    }

}
