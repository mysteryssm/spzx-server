package com.spzx.product.mapper;

import com.spzx.model.entity.product.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    List<Category> findFirstCategory();

    List<Category> findAll();
}