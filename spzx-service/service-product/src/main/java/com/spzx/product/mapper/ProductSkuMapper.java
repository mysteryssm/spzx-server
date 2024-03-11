package com.spzx.product.mapper;

import com.spzx.model.dto.h5.ProductSkuDto;
import com.spzx.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductSkuMapper {

    List<ProductSku> findProductSkuBySale();

    List<ProductSku> findByPage(ProductSkuDto productSkuDto);

    ProductSku getById(Long skuId);

    List<ProductSku> findByProductId(Long productId);

    void updateSale(Long skuId, Integer num);
}