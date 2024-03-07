package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysUserService;
import com.atguigu.spzx.model.dto.system.AssignRoleDto;
import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.globalEnum.ResultCodeEnum;
import com.atguigu.spzx.model.vo.common.Result;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description: SysUserController
 * @author: yck
 * @create: 2024-02-19
 */

@Tag(name = "用户管理接口")
@RestController
@RequestMapping(value = "/admin/system/sysUser")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @Operation(summary = "添加用户")
    @PostMapping(value = "/saveSysUser")
    public Result save(@RequestBody SysUser sysUser) {
        sysUserService.save(sysUser);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "修改用户")
    @PutMapping(value = "/updateSysUser")
    public Result update(@RequestBody SysUser sysUser) {
        sysUserService.update(sysUser);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "删除用户")
    @DeleteMapping(value = "/deleteById/{userId}")
    public Result delete(@PathVariable(value = "userId") Long userId) {
        sysUserService.delete(userId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "用户角色分配")
    @PostMapping(value = "/doAssign")
    public Result assignRole(@RequestBody AssignRoleDto assignRoleDto) {
        sysUserService.assignRole(assignRoleDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "分页查询用户")
    @PostMapping(value = "/findByPage/{pageNum}/{pageSize}")
    public Result<PageInfo<SysUser>> findByPage(@PathVariable(value = "pageNum") Integer pageNum,
                                                @PathVariable(value = "pageSize") Integer pageSize,
                                                @RequestBody SysUserDto sysUserDto) {
        PageInfo<SysUser> pageInfo = sysUserService.findByPage(sysUserDto, pageNum, pageSize);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }
}
