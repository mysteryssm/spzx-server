package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysMenuService;
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
@RequestMapping(value = "/admin/menu")
public class MenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @Operation(summary = "菜单添加")
    @PostMapping(value = "/insert")
    public Result insert(@RequestBody SysMenuDto sysMenuDto) {
        sysMenuService.save(sysMenuDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "菜单删除")
    @DeleteMapping(value = "/delete/{menuId}")
    public Result delete(@PathVariable("menuId") Long menuId) {
        sysMenuService.delete(menuId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "菜单修改")
    @PutMapping(value = "/update")
    public Result update(@RequestBody SysMenuDto sysMenuDto) {
        sysMenuService.update(sysMenuDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "菜单查询")
    @GetMapping(value = "/select/all")
    public Result<List<SysMenu>> selectAll() {
        List<SysMenu> sysMenuList = sysMenuService.findAllNodes();
        return Result.build(sysMenuList, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "指定角色菜单查询")
    @GetMapping(value = "/select/{roleId}")
    public Result<Map<String, Object>> findAllNodes(@PathVariable("roleId") Long roleId) {
        Map<String, Object> map = sysMenuService.findAllNodes(roleId);
        return Result.build(map, ResultCodeEnum.SUCCESS);
    }

}
