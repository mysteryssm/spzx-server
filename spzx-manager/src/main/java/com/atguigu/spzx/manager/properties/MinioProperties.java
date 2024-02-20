package com.atguigu.spzx.manager.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author ljl
 * @create 2023-10-26-14:54
 */
@Data
@ConfigurationProperties(prefix = "spzx.minio") //读取节点
public class MinioProperties {

    private String bucketName;
    private String endpointUrl;
    private String accessKey;
    private String secreKey;

}