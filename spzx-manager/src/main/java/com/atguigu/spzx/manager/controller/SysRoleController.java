package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysRoleService;
import com.atguigu.spzx.model.dto.system.AssignMenuDto;
import com.atguigu.spzx.model.dto.system.AssignRoleDto;
import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
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

@Tag(name = "用户角色管理接口")
@RestController
@RequestMapping(value = "admin/system/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @Operation(summary = "查询指定用户的角色以及所有角色")
    @GetMapping(value = "/findAllRoles/{userId}")
    public Result<Map<String, Object>> findAllRoles(@PathVariable(value = "userId") Long userId) {
        Map<String, Object> map = sysRoleService.findAllRoles(userId);  // 包含所有角色以及指定用户具有的角色
        return Result.build(map, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "分页查询角色")
    @PostMapping(value = "/findByPage/{current}/{limit}")
    public Result<PageInfo<SysRole>> findByPage(@PathVariable(value = "current") Integer current,
                                                @PathVariable(value = "limit") Integer limit,
                                                @RequestBody SysRoleDto sysRoleDto) {
        PageInfo<SysRole> pageInfo = sysRoleService.findByPage(sysRoleDto, current, limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "角色分配菜单")
    @PostMapping(value = "/assignMenu")
    public Result assignRole(@RequestBody AssignMenuDto assignMenuDto) {
        sysRoleService.assignMenu(assignMenuDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "添加角色")
    @PostMapping(value = "/saveSysRole")
    public Result saveSysRole(@RequestBody SysRole sysRole) {
        sysRoleService.saveSysRole(sysRole);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "修改角色接口")
    @PutMapping(value = "/updateSysRole")
    public Result updateSysRole(@RequestBody SysRole sysRole) {
        sysRoleService.updateSysRole(sysRole);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "删除角色接口")
    @DeleteMapping(value = "/deleteById/{roleId}")
    public Result deleteSysRole(@PathVariable(value = "roleId") Long roleId) {
        sysRoleService.deleteSysRole(roleId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
