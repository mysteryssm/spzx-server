package com.spzx.admin.controller;

import com.spzx.admin.service.ProductSpecService;
import com.spzx.model.entity.product.ProductSpec;
import com.spzx.model.globalEnum.ResultCodeEnum;
import com.spzx.model.vo.common.Result;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ljl
 * @create 2023-10-29-12:53
 */
@Tag(name = "商品规格管理")
@RestController
@RequestMapping(value="/admin/product/specification")
public class ProductSpecController {

    @Autowired
    private ProductSpecService productSpecService ;

    @Operation(summary = "商品规格分页查询")
    @GetMapping("/select/{page}/{size}")
    public Result<PageInfo<ProductSpec>> selectByPage(@PathVariable Integer page, @PathVariable Integer size) {
        PageInfo<ProductSpec> pageInfo = productSpecService.selectByPage(page, size);
        return Result.build(pageInfo , ResultCodeEnum.SUCCESS) ;
    }

    @Operation(summary = "商品规格添加")
    @PostMapping("/insert")
    public Result insert(@RequestBody ProductSpec productSpec) {
        productSpecService.insert(productSpec);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    @Operation(summary = "规格修改接口")
    @PutMapping("/update")
    public Result update(@RequestBody ProductSpec productSpec) {
        productSpecService.update(productSpec);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    @Operation(summary = "商品规格删除")
    @DeleteMapping("/delete/{productSpecificationId}")
    public Result delete(@PathVariable Long productSpecificationId) {
        productSpecService.delete(productSpecificationId);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    @Operation(summary = "商品规格查询")
    @GetMapping("/select/all")
    public Result selectAll() {
        List<ProductSpec> list = productSpecService.selectAll();
        return Result.build(list , ResultCodeEnum.SUCCESS) ;
    }

}
