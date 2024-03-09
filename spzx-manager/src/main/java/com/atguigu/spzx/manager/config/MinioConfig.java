package com.atguigu.spzx.manager.config;

import com.atguigu.spzx.manager.properties.MinioProperties;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @description: MinioConfig
 * @author: yck
 * @create: 2024-02-20
 */

//@Component
public class MinioConfig {
//    @Autowired
    private MinioProperties minioProperties;

//    @Bean
    public MinioClient minioClient() throws Exception {
        // 根据 MinioProperties 参数，创建 MinioClient 对象
        MinioClient minioClient = MinioClient.builder()
                .endpoint(minioProperties.getEndpointUrl())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecreKey())
                .build();

        // 判断试图存储文件的桶是否存在，若不存在则创建一个新的桶
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.getBucketName()).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucketName()).build());
        }

        return  minioClient;
    }
}
