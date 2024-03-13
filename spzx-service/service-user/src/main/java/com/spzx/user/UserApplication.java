package com.spzx.user;

import com.spzx.common.service.anno.EnableUserLoginAuthInterceptor;
import com.spzx.user.properties.SmsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.spzx"})
@EnableUserLoginAuthInterceptor
@EnableFeignClients(basePackages = {"com.spzx"})
@EnableConfigurationProperties(value = SmsProperties.class)
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

}