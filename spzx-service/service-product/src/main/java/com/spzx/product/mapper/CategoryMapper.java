package com.spzx.product.mapper;

import com.spzx.model.entity.common.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    List<Category> selectFirstLevelCategory();

    List<Category> selectAllCategory();
}