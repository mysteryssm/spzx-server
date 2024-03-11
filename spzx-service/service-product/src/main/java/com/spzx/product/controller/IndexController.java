package com.spzx.product.controller;

import com.spzx.model.entity.product.Category;
import com.spzx.model.entity.product.ProductSku;
import com.spzx.model.vo.common.Result;
import com.spzx.model.globalEnum.ResultCodeEnum;
import com.spzx.model.vo.h5.IndexVo;
import com.spzx.product.service.CategoryService;
import com.spzx.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ljl
 * @create 2023-11-01-20:51
 */

@Tag(name = "首页接口")
@RestController
@RequestMapping(value="/api/index")
public class IndexController {


    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Operation(summary = "获取首页数据")
    @GetMapping("/data")
    public Result<IndexVo> findData(){

        List<Category> categoryList = categoryService.findFirstCategory();    //获取一级分类

        List<ProductSku> productSkuList = productService.findProductSkuBySale();    //根据销量排序，获取前20条记录

        IndexVo indexVo = new IndexVo();
        indexVo.setCategoryList(categoryList);
        indexVo.setProductSkuList(productSkuList);
        return Result.build(indexVo, ResultCodeEnum.SUCCESS);
    }

}