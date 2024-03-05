package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysMenuService;
import com.atguigu.spzx.model.dto.system.AssignMenuDto;
import com.atguigu.spzx.model.dto.system.AssignRoleDto;
import com.atguigu.spzx.model.dto.system.SysMenuDto;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.globalEnum.ResultCodeEnum;
import com.atguigu.spzx.model.vo.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @description: SysMenuController
 * @author: yck
 * @create: 2024-02-21
 */

@Tag(name = "菜单管理接口")
@RestController
@RequestMapping(value = "/admin/system/sysMenu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @Operation(summary = "添加菜单")
    @PostMapping(value = "/save")
    public Result save(@RequestBody SysMenuDto sysMenuDto) {
        sysMenuService.save(sysMenuDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "删除菜单")
    @DeleteMapping(value = "/removeById/{id}")
    public Result delete(@PathVariable("id") Long id) {
        sysMenuService.delete(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "修改菜单")
    @PutMapping(value = "/update")
    public Result update(@RequestBody SysMenuDto sysMenuDto) {
        sysMenuService.update(sysMenuDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "查询所有菜单")
    @GetMapping(value = "/findNodes")
    public Result<List<SysMenu>> findAllNodes() {
        List<SysMenu> sysMenuList = sysMenuService.findAllNodes();
        return Result.build(sysMenuList, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "查询指定角色的所有菜单以及所有菜单")
    @GetMapping(value = "/findAllNodes/{roleId}")
    public Result<Map<String, Object>> findAllNodes(@PathVariable("roleId") Long roleId) {
        Map<String, Object> map = sysMenuService.findAllNodes(roleId);
        return Result.build(map, ResultCodeEnum.SUCCESS);
    }

}
