package com.spzx.user.controller;

import com.spzx.model.dto.webapp.UserLoginDto;
import com.spzx.model.dto.webapp.UserRegisterDto;
import com.spzx.model.globalConstant.ResultCodeEnum;
import com.spzx.model.vo.common.Result;
import com.spzx.user.service.UserLoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description: UserLoginController
 * @author: yck
 * @create: 2024-03-14
 */

@Tag(name  = "用户登录接口")
@RestController
@RequestMapping(value = "/api/user/userInfo")
public class UserLoginController {

    @Autowired
    private UserLoginService userLoginService;

    @Operation(summary = "用户注册")
    @PostMapping(value = "/register")
    public Result register(@RequestBody UserRegisterDto userRegisterDto) {
        userLoginService.register(userRegisterDto);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    @Operation(summary = "用户登录")
    @PostMapping(value = "/login")
    public Result<String> login(@RequestBody UserLoginDto userLoginDto) {
        String token = userLoginService.login(userLoginDto);
        return Result.build(userLoginService.login(userLoginDto), ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "用户退出登录")
    @PostMapping(value = "/logout")
    public Result logout(@RequestParam(name = "token") String token) {
        userLoginService.logout(token);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

}
