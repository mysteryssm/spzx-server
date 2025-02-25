package com.spzx.product.mapper;

import com.spzx.model.entity.common.ProductDetails;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ljl
 * @create 2023-11-02-20:37
 */
@Mapper
public interface ProductDetailsMapper {
    ProductDetails getByProductId(Long productId);
}
