package com.spzx.product.service;

import com.spzx.model.entity.common.Category;

import java.util.List;

/**
 * @author ljl
 * @create 2023-11-01-23:41
 */
// 业务接口
public interface CategoryService {

    List<Category> selectFirstLevelCategory();

    List<Category> selectAllCategory();
}