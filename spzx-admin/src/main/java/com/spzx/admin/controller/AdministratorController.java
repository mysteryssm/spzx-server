package com.spzx.admin.controller;

import com.spzx.common.log.annotation.Log;
import com.spzx.admin.service.AdministratorService;
import com.spzx.model.dto.admin.AssignRoleDto;
import com.spzx.model.dto.admin.AdministratorDto;
import com.spzx.model.entity.admin.Administrator;
import com.spzx.model.globalConstant.ResultCodeEnum;
import com.spzx.model.vo.common.Result;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description: AdministratorController
 * @author: yck
 * @create: 2024-02-19
 */

@Tag(name = "管理员管理接口")
@RestController
@RequestMapping(value = "/admin/administrator")
public class AdministratorController {
    @Autowired
    private AdministratorService administratorService;

    @Log(title = "管理员添加", businessType = 1)
    @Operation(summary = "管理员添加")
    @PostMapping(value = "/insert")
    public Result insert(@RequestBody Administrator administrator) {
        administratorService.insert(administrator);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


    @Log(title = "管理员删除", businessType = 2)
    @Operation(summary = "管理员删除")
    @DeleteMapping(value = "/delete/{administratorId}")
    public Result delete(@PathVariable(value = "administratorId") Long administratorId) {
        administratorService.delete(administratorId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Log(title = "管理员修改", businessType = 3)
    @Operation(summary = "管理员修改")
    @PutMapping(value = "/update")
    public Result update(@RequestBody Administrator administrator) {
        administratorService.update(administrator);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "管理员角色分配")
    @PostMapping(value = "/role/assign")
    public Result assignRole(@RequestBody AssignRoleDto assignRoleDto) {
        administratorService.assignRole(assignRoleDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "管理员分页查询")
    @PostMapping(value = "/select/{page}/{size}")
    public Result<PageInfo<Administrator>> selectByPage(@PathVariable(value = "page") Integer page,
                                                      @PathVariable(value = "size") Integer size,
                                                      @RequestBody AdministratorDto administratorDto) {
        PageInfo<Administrator> pageInfo = administratorService.selectByPage(administratorDto, page, size);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }
}
