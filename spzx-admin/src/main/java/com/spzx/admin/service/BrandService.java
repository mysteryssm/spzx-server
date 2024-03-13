package com.spzx.admin.service;

import com.spzx.model.entity.common.Brand;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @description: BrandService
 * @author: yck
 * @create: 2024-02-26
 */

public interface BrandService {

    void insert(Brand brand);

    void delete(Long brandId);

    void update(Brand brand);

    PageInfo<Brand> selectByPage(Integer page, Integer limit);

    List<Brand> selectAll();
}
