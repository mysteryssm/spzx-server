package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.product.CategoryBrandDto;
import com.atguigu.spzx.model.entity.product.CategoryBrand;
import com.github.pagehelper.PageInfo;

/**
 * @description: CategoryBrandService
 * @author: yck
 * @create: 2024-02-27
 */

public interface CategoryBrandService {
    PageInfo<CategoryBrand> add(CategoryBrand categoryBrand);

    PageInfo<CategoryBrand> query(Integer page, Integer limit, CategoryBrandDto categoryBrandDto);

    void update(CategoryBrand categoryBrand);

    void delete(CategoryBrandDto categoryBrandDto);
}
