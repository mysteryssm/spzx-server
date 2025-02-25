package com.spzx.admin.mapper;

import com.spzx.model.entity.common.ProductDetails;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ljl
 * @create 2023-10-29-17:17
 */

@Mapper
public interface ProductDetailsMapper {
    public void save(ProductDetails productDetails);

    ProductDetails selectByProductId(Long id);

    void updateById(ProductDetails productDetails);

    void deleteByProductId(Long id);
}
