package com.spzx.admin.service;

import com.spzx.model.dto.product.CategoryBrandDto;
import com.spzx.model.entity.product.CategoryBrand;
import com.github.pagehelper.PageInfo;

/**
 * @description: CategoryBrandService
 * @author: yck
 * @create: 2024-02-27
 */

public interface CategoryBrandService {
    PageInfo<CategoryBrand> insert(CategoryBrand categoryBrand);

    PageInfo<CategoryBrand> selectByPage(Integer page, Integer limit, CategoryBrandDto categoryBrandDto);

    void update(CategoryBrand categoryBrand);

    void delete(CategoryBrandDto categoryBrandDto);
}
