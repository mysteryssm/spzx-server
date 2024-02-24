package com.atguigu.spzx.manager.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.atguigu.spzx.manager.mapper.CategoryMapper;
import com.atguigu.spzx.manager.service.CategoryService;
import com.atguigu.spzx.model.entity.product.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @description: CategoryServiceImpl
 * @author: yck
 * @create: 2024-02-24
 */

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> queryCategoryByParentId(Long parentId) {
        List<Category> categoryList = categoryMapper.queryCategoryByParentId(parentId);

        // 遍历 categoryList，判断是否具有子节点
        if(!CollectionUtil.isEmpty(categoryList)) {
            for(Category category : categoryList) {
                category.setHasChildren(categoryMapper.countChildrenByParentId(category.getParentId()) != 0);
            }
        }

        return categoryList;
    }
}
