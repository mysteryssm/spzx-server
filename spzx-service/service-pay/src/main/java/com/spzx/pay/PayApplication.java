package com.spzx.pay;

import com.spzx.common.service.anno.EnableUserLoginAuthInterceptor;
import com.spzx.pay.properties.AlipayProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableUserLoginAuthInterceptor
@EnableFeignClients(basePackages = {
        "com.spzx"
})
@EnableConfigurationProperties(value = { AlipayProperties.class })
public class PayApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class , args) ;
    }

}