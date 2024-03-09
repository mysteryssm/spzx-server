package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.ProductService;
import com.atguigu.spzx.model.dto.product.ProductDto;
import com.atguigu.spzx.model.entity.product.Product;
import com.atguigu.spzx.model.globalEnum.ResultCodeEnum;
import com.atguigu.spzx.model.vo.common.Result;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ljl
 * @create 2023-10-29-16:02
 */

@Tag(name = "商品管理接口")
@RestController
@RequestMapping(value="/admin/product")
public class ProductController {

    @Autowired
    private ProductService productService ;

    @Operation(summary = "商品添加")
    @PostMapping("/insert")
    public Result save(@RequestBody Product product) {
        productService.save(product);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    @Operation(summary = "商品删除")
    @DeleteMapping("/delete/{productId}")
    public Result delete(@Parameter(name = "productId", description = "商品id", required = true) @PathVariable Long productId) {
        productService.deleteById(productId);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    @Operation(summary = "商品修改")
    @PutMapping("/update")
    public Result update(@Parameter(name = "product", description = "请求参数实体类", required = true) @RequestBody Product product) {
        productService.updateById(product);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    @Operation(summary = "商品分页查询")
    @GetMapping(value = "/select/{page}/{size}")
    public Result<PageInfo<Product>> findByPage(@PathVariable Integer page, @PathVariable Integer size, ProductDto productDto) {
        PageInfo<Product> pageInfo = productService.findByPage(page, size, productDto);
        return Result.build(pageInfo , ResultCodeEnum.SUCCESS) ;
    }

    @Operation(summary = "指定商品查询")
    @GetMapping("/select/{productId}")
    public Result<Product> getById(@PathVariable Long productId) {
        Product product = productService.getById(productId);
        return Result.build(product , ResultCodeEnum.SUCCESS) ;
    }

    @Operation(summary = "商品审核")
    @GetMapping("/auditStatus/update/{id}/{auditStatus}")
    public Result updateAuditStatus(@PathVariable Long id, @PathVariable Integer auditStatus) {
        productService.updateAuditStatus(id, auditStatus);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    @Operation(summary = "商品上架下架")
    @GetMapping("/status/update/{id}/{status}")
    public Result updateStatus(@PathVariable Long id, @PathVariable Integer status) {
        productService.updateStatus(id, status);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

}
