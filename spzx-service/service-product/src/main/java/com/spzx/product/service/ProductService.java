package com.spzx.product.service;

import com.spzx.model.dto.webapp.ProductSkuDto;
import com.spzx.model.dto.webapp.SkuSaleDto;
import com.spzx.model.entity.common.ProductSku;
import com.spzx.model.vo.webapp.ProductItemVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author ljl
 * @create 2023-11-01-23:41
 */
// 业务接口
public interface ProductService {

    List<ProductSku> selectProductSkuBySale();

    PageInfo<ProductSku> selectByPage(Integer page, Integer limit, ProductSkuDto productSkuDto);

    ProductItemVo item(String skuId);

    ProductSku getBySkuId(Long skuId);

    Boolean updateSkuSaleNum(List<SkuSaleDto> skuSaleDtoList);
}