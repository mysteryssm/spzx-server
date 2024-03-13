package com.spzx.admin.controller;

import com.spzx.common.log.annotation.Log;
import com.spzx.admin.service.MenuService;
import com.spzx.model.dto.admin.MenuDto;
import com.spzx.model.entity.admin.Menu;
import com.spzx.model.globalEnum.ResultCodeEnum;
import com.spzx.model.vo.common.Result;
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
    private MenuService menuService;

    @Log(title = "菜单添加", businessType = 1)
    @Operation(summary = "菜单添加")
    @PostMapping(value = "/insert")
    public Result insert(@RequestBody MenuDto menuDto) {
        menuService.insert(menuDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Log(title = "菜单删除", businessType = 2)
    @Operation(summary = "菜单删除")
    @DeleteMapping(value = "/delete/{menuId}")
    public Result delete(@PathVariable("menuId") Long menuId) {
        menuService.delete(menuId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Log(title = "菜单修改", businessType = 3)
    @Operation(summary = "菜单修改")
    @PutMapping(value = "/update")
    public Result update(@RequestBody MenuDto menuDto) {
        menuService.update(menuDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "菜单查询")
    @GetMapping(value = "/select/all")
    public Result<List<Menu>> selectAll() {
        List<Menu> menuList = menuService.selectAll();
        return Result.build(menuList, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "指定角色菜单查询")
    @GetMapping(value = "/select/{roleId}")
    public Result<Map<String, Object>> selectAll(@PathVariable("roleId") Long roleId) {
        Map<String, Object> map = menuService.selectAll(roleId);
        return Result.build(map, ResultCodeEnum.SUCCESS);
    }

}
