package com.spzx.admin.mapper;

import com.spzx.model.entity.product.Category;
import com.spzx.model.vo.product.CategoryExcelVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: CategoryMapper
 * @author: yck
 * @create: 2024-02-24
 */

@Mapper
public interface CategoryMapper {

    List<Category> queryCategoryByParentId(Long parentId);


    int countChildrenByParentId(Long parentId);

    List<Category> queryAllCategory();

    void batchInsert(List<CategoryExcelVo> cachedDataList);
}
