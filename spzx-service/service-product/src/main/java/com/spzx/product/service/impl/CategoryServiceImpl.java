package com.spzx.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.spzx.model.entity.common.Category;
import com.spzx.model.globalConstant.RedisKeyEnum;
import com.spzx.product.mapper.CategoryMapper;
import com.spzx.product.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryMapper categoryMapper;

    @Autowired
    private RedisTemplate<String , String> redisTemplate ;

    @Override
    public List<Category> selectFirstLevelCategory() {

        // 首先尝试从 Redis 中查询一级分类
        String categoryListJSON = redisTemplate.opsForValue().get(RedisKeyEnum.CATEGORY_FIRST.getKeyPrefix());
        if(!StringUtils.isEmpty(categoryListJSON)) {
            // parseArray() 将 JSON 转为 List
            List<Category> categoryList = JSON.parseArray(categoryListJSON, Category.class);
            return categoryList;
        }

        // 否则从 MySQL 中查询一级分类
        List<Category> categoryList = categoryMapper.selectFirstLevelCategory();
        redisTemplate.opsForValue().set(RedisKeyEnum.CATEGORY_FIRST.getKeyPrefix(),
                JSON.toJSONString(categoryList) , 7 , TimeUnit.DAYS);
        return categoryList;
    }

    @Cacheable(value = "category" , key = "'all'")
    @Override
    public List<Category> selectAllCategory() {
        List<Category> categoryList = categoryMapper.selectAllCategory(); //查询所有分类，返回list集合

        //通过 parentId = 0 得到所有的一级分类
        List<Category> firstLevelCategoryList = categoryList.stream()
                .filter(firstLevelCategory -> firstLevelCategory.getParentId().longValue() == 0)
                .collect(Collectors.toList());

        if(!CollectionUtils.isEmpty(firstLevelCategoryList)) {
            //遍历一级分类的集合，通过 id = parentId ，得到对应的二级分类
            firstLevelCategoryList.forEach(firstLevelCategory -> {
                List<Category> secondLevelCategoryList = categoryList.stream()
                        .filter(secondLevelCategory -> secondLevelCategory.getParentId().longValue() == firstLevelCategory.getId().longValue())
                        .collect(Collectors.toList());
                firstLevelCategory.setChildren(secondLevelCategoryList);

                if(!CollectionUtils.isEmpty(secondLevelCategoryList)) {
                    //遍历二级分类的集合，通过 id = parentId ，得到对应的三级分类
                    secondLevelCategoryList.forEach(secondLevelCategory -> {
                        List<Category> thridLevelCategoryList = categoryList.stream()
                                .filter(thridLevelCategory -> thridLevelCategory.getParentId().longValue() == secondLevelCategory.getId().longValue())
                                .collect(Collectors.toList());
                        secondLevelCategory.setChildren(thridLevelCategoryList);
                    });
                }
            });
        }

        return firstLevelCategoryList;
    }
}