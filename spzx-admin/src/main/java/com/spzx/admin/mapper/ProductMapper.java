package com.spzx.admin.mapper;

import com.spzx.model.dto.admin.ProductDto;
import com.spzx.model.entity.common.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ljl
 * @create 2023-10-29-16:03
 */
@Mapper
public interface ProductMapper {
    public abstract List<Product> findByPage(ProductDto productDto);

    void save(Product product);

    Product selectById(Long id);

    void updateById(Product product);

    void deleteById(Long id);
}