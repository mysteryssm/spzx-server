package com.spzx.admin.service;

import com.spzx.model.dto.admin.CategoryBrandDto;
import com.spzx.model.entity.common.CategoryBrand;
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
