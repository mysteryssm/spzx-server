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

import java.util.List;

/**
 * @description: CateGoryController
 * @author: yck
 * @create: 2024-02-24
 */

@Tag(name = "商品分类管理接口")
@RestController
@RequestMapping(value = "/admin/product/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "获取商品分类")
    @GetMapping(value = "/queryCategory/{parentId}")
    public Result<List<Category>> queryCategoryByParentId(@PathVariable(value = "parentId") Long parentId) {
        List<Category> categoryList = categoryService.queryCategoryByParentId(parentId);
        return Result.build(categoryList, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "导出商品分类")
    @GetMapping(value = "/export")
    public Result export(HttpServletResponse httpServletResponse) {
        categoryService.export(httpServletResponse);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

}
