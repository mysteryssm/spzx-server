package com.spzx.user.controller;

import com.spzx.model.vo.common.Result;
import com.spzx.model.globalConstant.ResultCodeEnum;
import com.spzx.user.service.SmsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "短信验证码接口")
@RestController
@RequestMapping(value = "api/user/sms")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @Operation(summary = "短信验证码获取")
    @GetMapping(value = "/sendCode/{phone}")
    public Result captcha(@PathVariable(name = "phone") String phone) {
        smsService.captcha(phone);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

}