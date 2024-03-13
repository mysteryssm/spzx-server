package com.spzx.admin.mapper;

import com.spzx.model.dto.admin.CategoryBrandDto;
import com.spzx.model.entity.common.CategoryBrand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: CategoryBrandMapper
 * @author: yck
 * @create: 2024-02-27
 */

@Mapper
public interface CategoryBrandMapper {
    List<CategoryBrand> select(CategoryBrandDto categoryBrandDto);

    void insert(CategoryBrand categoryBrand);

    void update(CategoryBrand categoryBrand);

    void delete(CategoryBrandDto categoryBrandDto);
}
