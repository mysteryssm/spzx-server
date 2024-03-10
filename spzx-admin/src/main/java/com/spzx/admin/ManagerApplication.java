package com.spzx.admin;

import com.atguigu.spzx.common.log.annotation.EnableLogAspect;
import com.spzx.admin.properties.ALiYunOSSProperties;
import com.spzx.admin.properties.UserAuthProperties;
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
@EnableScheduling // 开启定时任务
@EnableLogAspect
@ComponentScan(basePackages = {"com.spzx"})
@EnableConfigurationProperties(value = {UserAuthProperties.class, ALiYunOSSProperties.class})
public class ManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class, args) ;
    }

}