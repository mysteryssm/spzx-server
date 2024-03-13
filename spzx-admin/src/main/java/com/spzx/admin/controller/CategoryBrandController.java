package com.spzx.admin.controller;

import com.spzx.common.log.annotation.Log;
import com.spzx.admin.service.CategoryBrandService;
import com.spzx.model.dto.admin.CategoryBrandDto;
import com.spzx.model.entity.common.CategoryBrand;
import com.spzx.model.globalEnum.ResultCodeEnum;
import com.spzx.model.vo.common.Result;
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
@RequestMapping(value = "/admin/category/brand")
public class CategoryBrandController {

    @Autowired
    private CategoryBrandService categoryBrandService;

    @Log(title = "分类添加", businessType = 1)
    @Operation(summary = "分类品牌添加")
    @PostMapping(value = "/insert")
    public Result<PageInfo<CategoryBrand>> insert(@RequestBody CategoryBrand categoryBrand) {
        PageInfo<CategoryBrand> pageInfo = categoryBrandService.insert(categoryBrand);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @Log(title = "指定分类下的所有品牌删除", businessType = 2)
    @Operation(summary = "指定分类下的所有品牌删除")
    @DeleteMapping(value = "/delete")
    public Result delete(CategoryBrandDto categoryBrandDto) {
        categoryBrandService.delete(categoryBrandDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Log(title = "分类品牌修改", businessType = 3)
    @Operation(summary = "分类品牌修改")
    @PutMapping(value = "/update")
    public Result update(CategoryBrand categoryBrand) {
        categoryBrandService.update(categoryBrand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "分类品牌分页查询")
    @GetMapping(value = "/select/{page}/{size}")
    public Result<PageInfo<CategoryBrand>> selectByPage(@PathVariable(value = "page") Integer page,
                                                 @PathVariable(value = "size") Integer size,
                                                 CategoryBrandDto categoryBrandDto) {
        PageInfo<CategoryBrand> pageInfo = categoryBrandService.selectByPage(page, size, categoryBrandDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

//    @GetMapping("/findBrandByCategoryId/{categoryId}")
//    public Result findBrandByCategoryId(@PathVariable Long categoryId) {
//        List<Brand> list = categoryBrandService.findBrandByCategoryId(categoryId);
//        return Result.build(list,ResultCodeEnum.SUCCESS);
//    }
}
