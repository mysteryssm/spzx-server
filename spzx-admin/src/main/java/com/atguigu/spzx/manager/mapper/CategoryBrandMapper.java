package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.product.CategoryBrandDto;
import com.atguigu.spzx.model.entity.product.CategoryBrand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: CategoryBrandMapper
 * @author: yck
 * @create: 2024-02-27
 */

@Mapper
public interface CategoryBrandMapper {
    List<CategoryBrand> query(CategoryBrandDto categoryBrandDto);

    void add(CategoryBrand categoryBrand);

    void update(CategoryBrand categoryBrand);

    void delete(CategoryBrandDto categoryBrandDto);
}
