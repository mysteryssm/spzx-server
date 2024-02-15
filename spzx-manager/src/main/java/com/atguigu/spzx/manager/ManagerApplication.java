package com.atguigu.spzx.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author yck
 * @create 2023-10-22-16:34
 */

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = {"com.atguigu.spzx"})
public class ManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class, args) ;
    }

}