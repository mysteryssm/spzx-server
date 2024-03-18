package com.spzx.product.controller;

import com.spzx.model.entity.common.Brand;
import com.spzx.model.vo.common.Result;
import com.spzx.model.globalConstant.ResultCodeEnum;
import com.spzx.product.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "品牌接口")
@RestController
@RequestMapping(value="/api/product/brand")
@SuppressWarnings({"unchecked", "rawtypes"})
public class BrandController {
   
   @Autowired
   private BrandService brandService;
   
   @Operation(summary = "品牌查询")
   @GetMapping(value = "/findAll")
   public Result<List<Brand>> selectAll() {
      List<Brand> list = brandService.selectAll();
      return Result.build(list, ResultCodeEnum.SUCCESS);
   }

}