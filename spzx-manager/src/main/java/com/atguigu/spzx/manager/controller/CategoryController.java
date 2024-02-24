package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



}
