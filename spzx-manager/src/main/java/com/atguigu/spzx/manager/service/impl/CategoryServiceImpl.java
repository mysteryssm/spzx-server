package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.CategoryMapper;
import com.atguigu.spzx.manager.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: CategoryServiceImpl
 * @author: yck
 * @create: 2024-02-24
 */

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;


}
