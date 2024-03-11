package com.spzx.admin.service.impl;

import com.spzx.admin.mapper.CategoryBrandMapper;
import com.spzx.admin.service.CategoryBrandService;
import com.spzx.model.dto.product.CategoryBrandDto;
import com.spzx.model.entity.product.CategoryBrand;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: CategoryBrandServiceImpl
 * @author: yck
 * @create: 2024-02-27
 */

@Service
public class CategoryBrandServiceImpl implements CategoryBrandService {

    @Autowired
    private CategoryBrandMapper categoryBrandMapper;

    @Override
    public PageInfo<CategoryBrand> insert(CategoryBrand categoryBrand) {
        categoryBrandMapper.inset(categoryBrand);
        return null;
    }

    @Override
    public void delete(CategoryBrandDto categoryBrandDto) {
        categoryBrandMapper.delete(categoryBrandDto);
    }

    @Override
    public void update(CategoryBrand categoryBrand) {
        categoryBrandMapper.update(categoryBrand);
    }

    @Override
    public PageInfo<CategoryBrand> selectByPage(Integer page, Integer limit, CategoryBrandDto categoryBrandDto) {
        PageHelper.startPage(page, limit);
        List<CategoryBrand> list = categoryBrandMapper.select(categoryBrandDto);
        PageInfo<CategoryBrand> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
