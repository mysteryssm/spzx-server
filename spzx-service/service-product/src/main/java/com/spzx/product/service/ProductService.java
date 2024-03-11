package com.spzx.product.service;

import com.spzx.model.dto.h5.ProductSkuDto;
import com.spzx.model.dto.product.SkuSaleDto;
import com.spzx.model.entity.product.ProductSku;
import com.spzx.model.vo.h5.ProductItemVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author ljl
 * @create 2023-11-01-23:41
 */
// 业务接口
public interface ProductService {

    List<ProductSku> findProductSkuBySale();

    PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDto productSkuDto);

    ProductItemVo item(String skuId);

    ProductSku getBySkuId(Long skuId);

    Boolean updateSkuSaleNum(List<SkuSaleDto> skuSaleDtoList);
}