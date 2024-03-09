package com.atguigu.spzx.manager.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description: ALiYunOSSProperties
 * @author: yck
 * @create: 2024-02-21
 */

@Data
@ConfigurationProperties(prefix = "spzx.alioss")
public class ALiYunOSSProperties {

    private String endpoint;
    private String bucketName;
    private String accessKeyID;
    private String accessKey;

}
