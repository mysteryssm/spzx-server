package com.spzx.product.controller;

import com.spzx.feign.user.UserFeignClient;
import com.spzx.model.dto.webapp.ProductSkuDto;
import com.spzx.model.dto.webapp.SkuSaleDto;
import com.spzx.model.entity.common.ProductSku;
import com.spzx.model.entity.webapp.UserBrowseHistory;
import com.spzx.model.vo.common.Result;
import com.spzx.model.globalConstant.ResultCodeEnum;
import com.spzx.model.vo.webapp.ProductItemVo;
import com.spzx.product.service.ProductService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "商品接口")
@RestController
@RequestMapping(value = "/api/product")
@SuppressWarnings({"unchecked", "rawtypes"})
public class ProductController {
	
	@Autowired
	private ProductService productService;

	@Autowired
	private UserFeignClient userFeignClient;

	@Operation(summary = "商品分页查询")
	@PostMapping(value = "/{page}/{limit}")
	public Result<PageInfo<ProductSku>> select(@PathVariable(name = "page") Integer page,
											   @PathVariable(name = "limit") Integer limit,
											   @RequestBody ProductSkuDto productSkuDto) {
		PageInfo<ProductSku> pageInfo = productService.select(page, limit, productSkuDto);
		return Result.build(pageInfo , ResultCodeEnum.SUCCESS) ;
	}

	@Operation(summary = "商品详情")
	@GetMapping(value = "/item/{skuId}")
	public Result<ProductItemVo> item(@PathVariable(value = "skuId") String skuId) {
		ProductItemVo productItemVo;
		if (!"undefined".equals(skuId)) {
			// 如果没有传入有效的skuId，可以返回收藏最多的商品信息
			//远程调用查询收藏最多的商品
			productItemVo = productService.item(skuId);
		}else {
			//远程调用获取浏览量最多的商品
			UserBrowseHistory browseHistory = userFeignClient.getByBrowseHistory();
			Long id = browseHistory.getSkuId();
			String skuidString = String.valueOf(id);
			productItemVo = productService.item(skuidString);
		}

		return Result.build(productItemVo , ResultCodeEnum.SUCCESS);
	}

	/**
	 * @Description: 远程调用：根据商品skuId获取商品sku信息
	 * @param skuId
	 */
	@Operation(summary = "获取商品sku信息")
	@GetMapping(value = "/getBySkuId/{skuId}")
	public Result<ProductSku> getBySkuId(@PathVariable(value = "skuId") Long skuId) {
		ProductSku productSku = productService.getBySkuId(skuId);
		return Result.build(productSku, ResultCodeEnum.SUCCESS);
	}

	@Operation(summary = "更新商品sku销量")
	@PostMapping(value = "/updateSkuSaleNum")
	public Boolean update(@RequestBody List<SkuSaleDto> skuSaleDtoList) {
		return productService.update(skuSaleDtoList);
	}

}