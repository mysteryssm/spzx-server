package com.spzx.admin.controller;

import com.spzx.admin.service.ProductService;
import com.spzx.model.dto.product.ProductDto;
import com.spzx.model.entity.product.Product;
import com.spzx.model.globalEnum.ResultCodeEnum;
import com.spzx.model.vo.common.Result;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
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
    public Result insert(@RequestBody Product product) {
        productService.insert(product);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    @Operation(summary = "商品删除")
    @DeleteMapping("/delete/{productId}")
    public Result delete(@PathVariable Long productId) {
        productService.delete(productId);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    @Operation(summary = "商品修改")
    @PutMapping("/update")
    public Result update(@RequestBody Product product) {
        productService.update(product);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    @Operation(summary = "商品分页查询")
    @PostMapping(value = "/select/{page}/{size}")
    public Result<PageInfo<Product>> selectByPage(@PathVariable Integer page,
                                                @PathVariable Integer size,
                                                @RequestBody ProductDto productDto) {
        PageInfo<Product> pageInfo = productService.selectByPage(page, size, productDto);
        return Result.build(pageInfo , ResultCodeEnum.SUCCESS) ;
    }

    @Operation(summary = "指定商品查询")
    @GetMapping("/select/{productId}")
    public Result<Product> selectByProductId(@PathVariable Long productId) {
        Product product = productService.selectByProductId(productId);
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
