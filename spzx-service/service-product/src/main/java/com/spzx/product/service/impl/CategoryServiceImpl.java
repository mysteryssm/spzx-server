package com.spzx.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.spzx.model.entity.product.Category;
import com.spzx.model.globalEnum.RedisKeyEnum;
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
    public List<Category> findFirstCategory() {

        // 首先尝试从 Redis 中查询一级分类
        String categoryListJSON = redisTemplate.opsForValue().get(RedisKeyEnum.CATEGORY_FIRST.getKeyPrefix());
        if(!StringUtils.isEmpty(categoryListJSON)) {
            List<Category> categoryList = JSON.parseArray(categoryListJSON, Category.class);
            log.info("从 Redis 中查询到一级分类");
            return categoryList;
        }

        List<Category> categoryList = categoryMapper.findFirstCategory();
        log.info("从MySQL中查询到一级分类");
        redisTemplate.opsForValue().set(RedisKeyEnum.CATEGORY_FIRST.getKeyPrefix(),
                JSON.toJSONString(categoryList) , 7 , TimeUnit.DAYS);
        return categoryList;
    }

    @Cacheable(value = "category" , key = "'all'")
    @Override
    public List<Category> findCategoryTree() {
        List<Category> categoryList = categoryMapper.findAll(); //查询所有分类，返回list集合

        //通过条件parentid=0得到所有的一级分类
        List<Category> oneCategoryList = categoryList.stream()
                .filter(item -> item.getParentId().longValue() == 0)
                .collect(Collectors.toList());

        //遍历一级分类的集合，通过id=parentid ，得到下面的二级分类
        //遍历二级分类的集合，通过id=parentid ，得到下面的三级分类
        if(!CollectionUtils.isEmpty(oneCategoryList)) {
            oneCategoryList.forEach(oneCategory -> {
                List<Category> twoCategoryList = categoryList.stream().filter(item -> item.getParentId().longValue() == oneCategory.getId().longValue()).collect(Collectors.toList());
                oneCategory.setChildren(twoCategoryList);

                if(!CollectionUtils.isEmpty(twoCategoryList)) {
                    twoCategoryList.forEach(twoCategory -> {
                        List<Category> threeCategoryList = categoryList.stream().filter(item -> item.getParentId().longValue() == twoCategory.getId().longValue()).collect(Collectors.toList());
                        twoCategory.setChildren(threeCategoryList);
                    });
                }
            });
        }
        return oneCategoryList;
    }
}