package com.spzx.admin.service;

import com.spzx.model.entity.common.ProductSpec;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author ljl
 * @create 2023-10-29-12:54
 */
public interface ProductSpecService {
    PageInfo<ProductSpec> selectByPage(Integer page, Integer limit);

    void insert(ProductSpec productSpec);

    void update(ProductSpec productSpec);

    void delete(Long id);

    List<ProductSpec> selectAll();
}
