package com.spzx.common.service.exception;

import com.spzx.model.globalConstant.ResultCodeEnum;
import lombok.Data;

/**
 * @author ljl
 * @create 2023-10-24-22:13
 */
@Data
public class GlobalException extends RuntimeException {

    private Integer code;          // 错误状态码
    private String message;        // 错误消息
    private ResultCodeEnum resultCodeEnum;     // 封装错误状态码和错误消息

    public GlobalException(ResultCodeEnum resultCodeEnum) {
        this.resultCodeEnum = resultCodeEnum;
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }

    public GlobalException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}