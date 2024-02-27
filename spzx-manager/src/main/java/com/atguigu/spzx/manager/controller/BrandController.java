package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.BrandService;
import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.globalEnum.ResultCodeEnum;
import com.atguigu.spzx.model.vo.common.Result;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: BrandController
 * @author: yck
 * @create: 2024-02-26
 */

@Tag(name = "品牌管理接口")
@RestController
@RequestMapping(value = "/admin/product/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Operation(summary = "添加品牌")
    @PostMapping(value = "/add")
    public Result add(@RequestBody Brand brand) {
        brandService.add(brand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "删除品牌")
    @DeleteMapping(value = "/delete/{brandId}")
    public Result delete(@PathVariable(value = "brandId") Long brandId) {
        brandService.delete(brandId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "修改品牌")
    @PutMapping(value = "/update")
    public Result update(@RequestBody Brand brand) {
        brandService.update(brand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "分页查询所有品牌")
    @GetMapping(value = "/query/{page}/{limit}")
    public Result<PageInfo<Brand>> query(@PathVariable(value = "page") Integer page, @PathVariable(value = "limit") Integer limit) {
        PageInfo<Brand> pageInfo = brandService.query(page, limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "查询所有品牌")
    @GetMapping(value = "/queryAll")
    public Result<List<Brand>> queryAll() {
        List<Brand> list = brandService.queryAll();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }
}
