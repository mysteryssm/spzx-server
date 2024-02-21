package com.atguigu.spzx.manager.config;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.atguigu.spzx.manager.properties.ALiYunOSSProperties;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @description: ALiYunOSSConfig
 * @author: yck
 * @create: 2024-02-21
 */

@Component
public class ALiYunOSSConfig {

    @Autowired
    private ALiYunOSSProperties aLiYunOSSProperties;

    @Bean
    public OSS ossClient() {
        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(aLiYunOSSProperties.getEndpoint(),
                aLiYunOSSProperties.getAccessKeyID(),
                aLiYunOSSProperties.getAccessKey());

        // 判断试图存储文件的桶是否存在，若不存在则创建一个新的桶
        if (!ossClient.doesBucketExist(aLiYunOSSProperties.getBucketName())) {
            ossClient.createBucket(aLiYunOSSProperties.getBucketName());
        }

        return ossClient;
    }
}
