package com.atguigu.spzx.manager;

import com.atguigu.spzx.manager.properties.UserAuthProperties;
import io.minio.MinioProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author yck
 * @create 2023-10-22-16:34
 */


@SpringBootApplication
@EnableScheduling
//@EnableLogAspect
@ComponentScan(basePackages = {"com.atguigu.spzx"})
@EnableConfigurationProperties(value = {UserAuthProperties.class})
public class ManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class, args) ;
    }

}