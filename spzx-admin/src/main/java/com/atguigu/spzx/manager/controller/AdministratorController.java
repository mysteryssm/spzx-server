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

@Tag(name = "管理员管理接口")
@RestController
@RequestMapping(value = "/admin/administrator")
public class AdministratorController {
    @Autowired
    private SysUserService sysUserService;

    @Operation(summary = "管理员添加")
    @PostMapping(value = "/insert")
    public Result insert(@RequestBody SysUser sysUser) {
        sysUserService.save(sysUser);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "管理员修改")
    @PutMapping(value = "/update")
    public Result update(@RequestBody SysUser sysUser) {
        sysUserService.update(sysUser);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "管理员删除")
    @DeleteMapping(value = "/delete/{administratorId}")
    public Result delete(@PathVariable(value = "administratorId") Long administratorId) {
        sysUserService.delete(administratorId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "管理员角色分配")
    @PostMapping(value = "/role/assign")
    public Result assignRole(@RequestBody AssignRoleDto assignRoleDto) {
        sysUserService.assignRole(assignRoleDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "管理员分页查询")
    @PostMapping(value = "/select/{page}/{size}")
    public Result<PageInfo<SysUser>> findByPage(@PathVariable(value = "pageNum") Integer pageNum,
                                                @PathVariable(value = "pageSize") Integer pageSize,
                                                @RequestBody SysUserDto sysUserDto) {
        PageInfo<SysUser> pageInfo = sysUserService.findByPage(sysUserDto, pageNum, pageSize);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }
}
