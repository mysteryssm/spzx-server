package com.atguigu.spzx.common.exception;

import com.atguigu.spzx.model.vo.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description: GlobalExceptionHandler
 * @author: yck
 * @create: 2024-02-15
 */

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = GlobalException.class)
    @ResponseBody // 使得返回的数据为json格式
    public Result loginError(GlobalException globalException) {
        return Result.build(null, globalException.getResultCodeEnum());
    }
}
