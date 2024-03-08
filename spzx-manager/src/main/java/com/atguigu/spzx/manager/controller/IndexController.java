package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysMenuService;
import com.atguigu.spzx.manager.service.SysRoleService;
import com.atguigu.spzx.manager.service.SysUserService;
import com.atguigu.spzx.manager.service.ValidateCodeService;
import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.globalEnum.ResultCodeEnum;
import com.atguigu.spzx.model.vo.system.LoginVo;
import com.atguigu.spzx.model.vo.system.SysMenuVo;
import com.atguigu.spzx.model.vo.system.ValidateCodeVo;
import com.atguigu.spzx.utils.AuthContextUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author yck
 * @create 2023-10-22-16:35
 */

@Tag(name = "管理员登录接口")
@RestController
@RequestMapping(value = "/admin/index")
public class IndexController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private ValidateCodeService validateCodeService;

    @Operation(summary = "验证码生成")
    @GetMapping("/captcha")
    public Result<ValidateCodeVo> captcha() {
        ValidateCodeVo validateCodeVo = validateCodeService.generateValidateCode();
        return Result.build(validateCodeVo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "管理员登录")
    @PostMapping(value = "/login")
    public Result<LoginVo> login(@RequestBody LoginDto loginDto) {
        LoginVo loginVo = sysUserService.login(loginDto);
        return Result.build(loginVo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "管理员信息获取")
    @GetMapping(value = "/userInfo")
    public Result<SysUser> getUserInfo(@RequestHeader(name = "token") String token) {
        SysUser sysUser = sysUserService.getUserInfo(token);    // 利用token获取对应用户的用户信息
        return Result.build(sysUser, ResultCodeEnum.SUCCESS);
    }

    //    @Operation(summary = "从 ThreadLocal 中获取用户信息接口")
//    @GetMapping(value = "/getUserInfo")
//    public Result<SysUser> getUserInfo() {
//        return Result.build(AuthContextUtil.get(), ResultCodeEnum.SUCCESS) ;
//    }

    @Operation(summary = "管理员菜单界面生成")
    @GetMapping("/menus")
    public Result<List<SysMenuVo>> findMenusByUserId(@RequestHeader(name = "token") String token) {
        SysUser sysUser = sysUserService.getUserInfo(token);
        List<SysMenuVo> list = sysMenuService.findMenusByUserId(sysUser.getId());
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "管理员退出登录")
    @GetMapping(value = "/logout")
    public Result logout(@RequestHeader(name = "token") String token) {
        sysUserService.logout(token);    // 利用token获取对应用户的用户信息
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}