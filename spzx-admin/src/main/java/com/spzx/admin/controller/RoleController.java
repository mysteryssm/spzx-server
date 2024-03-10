package com.spzx.admin.controller;

import com.spzx.admin.service.RoleService;
import com.atguigu.spzx.model.dto.system.AssignMenuDto;
import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.admin.SysRole;
import com.atguigu.spzx.model.globalEnum.ResultCodeEnum;
import com.atguigu.spzx.model.vo.common.Result;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @description: SysRoleController
 * @author: yck
 * @create: 2024-02-18
 */

@Tag(name = "管理员角色管理接口")
@RestController
@RequestMapping(value = "admin/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Operation(summary = "角色添加")
    @PostMapping(value = "/insert")
    public Result insert(@RequestBody SysRole sysRole) {
        roleService.insert(sysRole);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "角色删除")
    @DeleteMapping(value = "/delete/{roleId}")
    public Result delete(@PathVariable(value = "roleId") Long roleId) {
        roleService.delete(roleId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "角色修改")
    @PutMapping(value = "/update")
    public Result update(@RequestBody SysRole sysRole) {
        roleService.update(sysRole);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "指定管理员角色查询")
    @GetMapping(value = "/select/{administratorId}")
    public Result<Map<String, Object>> findAllRoles(@PathVariable(value = "administratorId") Long administratorId) {
        Map<String, Object> map = roleService.findAllRoles(administratorId);  // 包含所有角色以及指定用户具有的角色
        return Result.build(map, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "角色分页查询")
    @PostMapping(value = "/select/{page}/{size}")
    public Result<PageInfo<SysRole>> selectByPage(@PathVariable(value = "page") Integer page,
                                                @PathVariable(value = "size") Integer size,
                                                @RequestBody SysRoleDto sysRoleDto) {
        PageInfo<SysRole> pageInfo = roleService.selectByPage(sysRoleDto, page, size);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "角色菜单分配")
    @PostMapping(value = "/menu/assign")
    public Result assignRole(@RequestBody AssignMenuDto assignMenuDto) {
        roleService.assignMenu(assignMenuDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
