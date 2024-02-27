package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.CategoryBrandService;
import com.atguigu.spzx.model.dto.product.CategoryBrandDto;
import com.atguigu.spzx.model.entity.product.CategoryBrand;
import com.atguigu.spzx.model.globalEnum.ResultCodeEnum;
import com.atguigu.spzx.model.vo.common.Result;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description: CategoryBrandController
 * @author: yck
 * @create: 2024-02-27
 */

@Tag(name = "分类品牌管理接口")
@RestController
@RequestMapping(value = "/admin/product/categoryBrand")
public class CategoryBrandController {

    @Autowired
    private CategoryBrandService categoryBrandService;

    @Operation(summary = "添加分类品牌")
    @PostMapping(value = "/add")
    public Result<PageInfo<CategoryBrand>> add(@RequestBody CategoryBrand categoryBrand) {
        PageInfo<CategoryBrand> pageInfo = categoryBrandService.add(categoryBrand);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "删除指定分类下的所有品牌")
    @DeleteMapping(value = "/delete")
    public Result delete(CategoryBrandDto categoryBrandDto) {
        categoryBrandService.delete(categoryBrandDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "修改分类品牌")
    @PutMapping(value = "/update")
    public Result update(CategoryBrand categoryBrand) {
        categoryBrandService.update(categoryBrand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "分页查询分类品牌")
    @GetMapping(value = "/query/{page}/{limit}")
    public Result<PageInfo<CategoryBrand>> query(@PathVariable(value = "page") Integer page,
                                                 @PathVariable(value = "limit") Integer limit,
                                                 CategoryBrandDto categoryBrandDto) {
        PageInfo<CategoryBrand> pageInfo = categoryBrandService.query(page, limit, categoryBrandDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }
}
