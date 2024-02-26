package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.BrandService;
import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.globalEnum.ResultCodeEnum;
import com.atguigu.spzx.model.vo.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description: BrandController
 * @author: yck
 * @create: 2024-02-26
 */

@Tag(name = "品牌管理接口")
@RestController
@RequestMapping(value = "/admin/system/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Operation(summary = "添加品牌")
    @PostMapping(value = "/add")
    public Result add(@RequestBody Brand) {
        brandService.add();
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "删除品牌")
    @DeleteMapping(value = "/delete")
    public Result delete() {

        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "修改品牌")
    @PutMapping(value = "/update")
    public Result update() {

        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "查询所有品牌")
    @GetMapping(value = "/query/{page}/{limit}")
    public Result query(@PathVariable(value = "page") Integer page, @PathVariable(value = "limit") Integer limit) {
        brandService.query(page, limit);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
