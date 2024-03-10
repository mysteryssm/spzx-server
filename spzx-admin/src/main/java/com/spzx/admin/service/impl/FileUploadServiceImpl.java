package com.spzx.admin.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.spzx.admin.properties.ALiYunOSSProperties;
import com.spzx.admin.service.FileUploadService;
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

//    @Autowired
//    private MinioProperties minioProperties;
//
//    @Autowired
//    private MinioClient minioClient;

    @Autowired
    private ALiYunOSSProperties aLiYunOSSProperties;

    @Autowired
    private OSS ossClient;

    @Override
    public String fileUpload(MultipartFile multipartFile) {

        try {
            // 生成存储对象名称以及存储文件路径
            String dateDir = DateUtil.format(new Date(), "yyyyMMdd");
            String uuid = UUID.randomUUID().toString().replace("-", "");
            String fileName = dateDir + "/" + uuid + multipartFile.getOriginalFilename();

            // 生成放入对象参数
//            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
//                    .bucket(minioProperties.getBucketName())
//                    .stream(multipartFile.getInputStream(), multipartFile.getSize(), -1)
//                    .object(fileName)
//                    .build();
//            minioClient.putObject(putObjectArgs);   //根据 putObjectArgs 将文件放入

            // 创建PutObjectRequest对象
            PutObjectRequest putObjectRequest = new PutObjectRequest(aLiYunOSSProperties.getBucketName(),
                    fileName,
                    multipartFile.getInputStream());

            PutObjectResult result = ossClient.putObject(putObjectRequest); // 上传文件

            return "http://" + aLiYunOSSProperties.getBucketName() + "." + aLiYunOSSProperties.getEndpoint() + "/"+ fileName;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
