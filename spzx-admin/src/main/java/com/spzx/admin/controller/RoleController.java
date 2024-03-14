package com.spzx.admin.controller;

import com.spzx.model.entity.admin.Role;
import com.spzx.admin.service.RoleService;
import com.spzx.model.dto.admin.AssignMenuDto;
import com.spzx.model.dto.admin.RoleDto;
import com.spzx.model.globalConstant.ResultCodeEnum;
import com.spzx.model.vo.common.Result;
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
    public Result insert(@RequestBody Role role) {
        roleService.insert(role);
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
    public Result update(@RequestBody Role role) {
        roleService.update(role);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "指定管理员角色查询")
    @GetMapping(value = "/select/{administratorId}")
    public Result<Map<String, Object>> selectByAdministratorId(@PathVariable(value = "administratorId") Long administratorId) {
        Map<String, Object> map = roleService.selectByAdministratorId(administratorId);  // 包含所有角色以及指定用户具有的角色
        return Result.build(map, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "角色分页查询")
    @PostMapping(value = "/select/{page}/{size}")
    public Result<PageInfo<Role>> selectByPage(@PathVariable(value = "page") Integer page,
                                               @PathVariable(value = "size") Integer size,
                                               @RequestBody RoleDto roleDto) {
        PageInfo<Role> pageInfo = roleService.selectByPage(roleDto, page, size);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "角色菜单分配")
    @PostMapping(value = "/menu/assign")
    public Result assignMenu(@RequestBody AssignMenuDto assignMenuDto) {
        roleService.assignMenu(assignMenuDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
