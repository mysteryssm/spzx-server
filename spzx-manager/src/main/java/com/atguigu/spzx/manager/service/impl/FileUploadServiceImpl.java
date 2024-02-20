package com.atguigu.spzx.manager.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import com.atguigu.spzx.manager.properties.MinioProperties;
import com.atguigu.spzx.manager.service.FileUploadService;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * @description: FileUploadServiceImpl
 * @author: yck
 * @create: 2024-02-20
 */

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    private MinioProperties minioProperties;

    @Autowired
    private MinioClient minioClient;

    @Override
    public String fileUpload(MultipartFile multipartFile) {

        try {
            // 生成存储对象名称以及存储文件路径
            String dateDir = DateUtil.format(new Date(), "yyyyMMdd");
            String uuid = UUID.randomUUID().toString().replace("-", "");
            String fileName = dateDir + "/" + uuid + multipartFile.getOriginalFilename();

            // 生成放入对象参数
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(minioProperties.getBucketName())
                    .stream(multipartFile.getInputStream(), multipartFile.getSize(), -1)
                    .object(fileName)
                    .build();

            minioClient.putObject(putObjectArgs);   //根据 putObjectArgs 将文件放入

            return minioProperties.getEndpointUrl() + "/" + minioProperties.getBucketName() + "/" + fileName;   //返回文件路径

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
