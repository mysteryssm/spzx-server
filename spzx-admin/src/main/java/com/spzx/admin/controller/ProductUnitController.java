package com.spzx.admin.controller;

import com.spzx.admin.service.ProductUnitService;
import com.spzx.model.entity.base.ProductUnit;
import com.spzx.model.globalEnum.ResultCodeEnum;
import com.spzx.model.vo.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ljl
 * @create 2023-10-29-17:07
 */

@Tag(name = "商品计量单位数据管理接口")
@RestController
@RequestMapping("/admin/product/unit")
public class ProductUnitController {

    @Autowired
    private ProductUnitService productUnitService;

    @Operation(summary = "商品计量单位数据查询")
    @GetMapping("/select/all")
    public Result<List<ProductUnit>> selectAll() {
        List<ProductUnit> productUnitList = productUnitService.selectAll();
        return Result.build(productUnitList , ResultCodeEnum.SUCCESS) ;
    }
}
