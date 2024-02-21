package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysMenuService;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.globalEnum.ResultCodeEnum;
import com.atguigu.spzx.model.vo.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @Operation(summary = "查询所有菜单接口")
    @GetMapping(value = "/findAllNodes")
    public Result<List<SysMenu>> findAllNodes() {
        List<SysMenu> sysMenuList = sysMenuService.findAllNodes();
        return Result.build(sysMenuList, ResultCodeEnum.SUCCESS);
    }

}
