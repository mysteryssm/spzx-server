package com.spzx.admin.controller;

import com.spzx.common.log.annotation.Log;
import com.spzx.admin.service.MenuService;
import com.spzx.admin.service.AdministratorService;
import com.spzx.admin.service.CaptchaService;
import com.spzx.model.dto.system.LoginDto;
import com.spzx.model.entity.admin.Administrator;
import com.spzx.model.vo.common.Result;
import com.spzx.model.globalEnum.ResultCodeEnum;
import com.spzx.model.vo.system.CaptchaVo;
import com.spzx.model.vo.system.LoginVo;
import com.spzx.model.vo.system.SysMenuVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yck
 * @create 2023-10-22-16:35
 */

@Tag(name = "管理员登录接口")
@RestController
@RequestMapping(value = "/admin/index")
public class IndexController {

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private CaptchaService captchaService;

    @Operation(summary = "验证码生成")
    @GetMapping("/captcha")
    public Result<CaptchaVo> getCaptcha() {
        CaptchaVo captchaVo = captchaService.generateValidateCode();
        return Result.build(captchaVo, ResultCodeEnum.SUCCESS);
    }

    @Log(title = "管理员登录", businessType = 0)
    @Operation(summary = "管理员登录")
    @PostMapping(value = "/login")
    public Result<LoginVo> login(@RequestBody LoginDto loginDto) {
        LoginVo loginVo = administratorService.login(loginDto);
        return Result.build(loginVo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "管理员信息获取")
    @GetMapping(value = "/userInfo")
    public Result<Administrator> getUserInfo(@RequestHeader(name = "token") String token) {
        Administrator administrator = administratorService.getUserInfo(token);    // 利用token获取对应用户的用户信息
        return Result.build(administrator, ResultCodeEnum.SUCCESS);
    }

    //    @Operation(summary = "从 ThreadLocal 中获取用户信息接口")
//    @GetMapping(value = "/getUserInfo")
//    public Result<SysUser> getUserInfo() {
//        return Result.build(AuthContextUtil.get(), ResultCodeEnum.SUCCESS) ;
//    }

    @Operation(summary = "管理员菜单界面生成")
    @GetMapping("/menus")
    public Result<List<SysMenuVo>> getMenus(@RequestHeader(name = "token") String token) {
        Administrator administrator = administratorService.getUserInfo(token);
        List<SysMenuVo> list = menuService.findMenusByUserId(administrator.getId());
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

    @Log(title = "管理员退出登录", businessType = -1)
    @Operation(summary = "管理员退出登录")
    @GetMapping(value = "/logout")
    public Result logout(@RequestHeader(name = "token") String token) {
        administratorService.logout(token);    // 利用token获取对应用户的用户信息
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}