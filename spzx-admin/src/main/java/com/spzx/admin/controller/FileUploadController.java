package com.spzx.admin.controller;

import com.spzx.admin.service.FileUploadService;
import com.spzx.model.globalConstant.ResultCodeEnum;
import com.spzx.model.vo.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @description: FileUploadController
 * @author: yck
 * @create: 2024-02-20
 */

@Tag(name = "上传文件接口")
@RestController
@RequestMapping(value = "/admin")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @Operation(summary = "上传文件")
    @PostMapping(value = "/fileUpload")
    public Result<String> fileUpload(@RequestParam(value = "file") MultipartFile file) {
        String fileUrl = fileUploadService.fileUpload(file);
        return Result.build(fileUrl, ResultCodeEnum.SUCCESS);
    }
}
