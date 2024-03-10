package com.spzx.admin.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @description: FileUploadService
 * @author: yck
 * @create: 2024-02-20
 */

public interface FileUploadService {

    String fileUpload(MultipartFile file);
}
