package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.CategoryService;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.globalEnum.ResultCodeEnum;
import com.atguigu.spzx.model.vo.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @description: CateGoryController
 * @author: yck
 * @create: 2024-02-24
 */

@Tag(name = "分类管理接口")
@RestController
@RequestMapping(value = "/admin/category")
public class ProductCategoryController {

    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "父类的子分类查询")
    @GetMapping(value = "/select/{parentId}")
    public Result<List<Category>> queryCategoryByParentId(@PathVariable(value = "parentId") Long parentId) {
        List<Category> categoryList = categoryService.queryCategoryByParentId(parentId);
        return Result.build(categoryList, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "分类导出")
    @GetMapping(value = "/export")
    public Result exportCategory(HttpServletResponse httpServletResponse) {
        categoryService.export(httpServletResponse);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "分类导入")
    @PostMapping(value = "/import")
    public Result importCategory(@RequestParam(value = "file") MultipartFile multipartFile) {
        categoryService.importCategory(multipartFile);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
