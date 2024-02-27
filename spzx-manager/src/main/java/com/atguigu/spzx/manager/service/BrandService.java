package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.entity.product.Brand;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @description: BrandService
 * @author: yck
 * @create: 2024-02-26
 */

public interface BrandService {

    void add(Brand brand);

    void delete(Long brandId);

    void update(Brand brand);

    PageInfo<Brand> query(Integer page, Integer limit);

    List<Brand> queryAll();
}
