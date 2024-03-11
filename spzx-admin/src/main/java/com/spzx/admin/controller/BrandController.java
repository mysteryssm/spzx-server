package com.spzx.admin.controller;

import com.spzx.common.log.annotation.Log;
import com.spzx.admin.service.BrandService;
import com.spzx.model.entity.product.Brand;
import com.spzx.model.globalEnum.ResultCodeEnum;
import com.spzx.model.vo.common.Result;
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
@RequestMapping(value = "/admin/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Log(title = "品牌添加", businessType = 1)
    @Operation(summary = "品牌添加")
    @PostMapping(value = "/insert")
    public Result insert(@RequestBody Brand brand) {
        brandService.insert(brand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Log(title = "品牌删除", businessType = 2)
    @Operation(summary = "品牌删除")
    @DeleteMapping(value = "/delete/{brandId}")
    public Result delete(@PathVariable(value = "brandId") Long brandId) {
        brandService.delete(brandId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Log(title = "品牌修改", businessType = 3)
    @Operation(summary = "品牌修改")
    @PutMapping(value = "/update")
    public Result update(@RequestBody Brand brand) {
        brandService.update(brand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "品牌分页查询")
    @GetMapping(value = "/select/{page}/{limit}")
    public Result<PageInfo<Brand>> selectByPage(@PathVariable(value = "page") Integer page,
                                          @PathVariable(value = "limit") Integer limit) {
        PageInfo<Brand> pageInfo = brandService.selectByPage(page, limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "品牌查询")
    @GetMapping(value = "/select/all")
    public Result<List<Brand>> selectAll() {
        List<Brand> list = brandService.selectAll();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }
}
