package com.atguigu.spzx.manager.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.excel.EasyExcel;
import com.atguigu.spzx.common.exception.GlobalException;
import com.atguigu.spzx.manager.mapper.CategoryMapper;
import com.atguigu.spzx.manager.service.CategoryService;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.globalEnum.ResultCodeEnum;
import com.atguigu.spzx.model.vo.product.CategoryExcelVo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
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

    @Override
    public void export(HttpServletResponse httpServletResponse) {
        try {
            //设置响应头信息和其他信息
            httpServletResponse.setContentType("application/vnd.ms-excel");
            httpServletResponse.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("分类数据", "UTF-8");   // 设置URLEncoder.encode防止中文乱码
            //设置响应头信息，表示让文件以下载方式打开
            httpServletResponse.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

            List<Category> categoryList = categoryMapper.queryAllCategory();
            List<CategoryExcelVo> categoryExcelVoList = new ArrayList<>(categoryList.size());

            // 将 Category 对象转换成 CategoryExcelVo 对象
            for(Category category : categoryList) {
                CategoryExcelVo categoryExcelVo = new CategoryExcelVo();
                BeanUtils.copyProperties(category, categoryExcelVo, CategoryExcelVo.class);
                categoryExcelVoList.add(categoryExcelVo);
            }

            // 写出数据到浏览器端
            EasyExcel.write(httpServletResponse.getOutputStream(), CategoryExcelVo.class)
                    .sheet("分类数据")
                    .doWrite(categoryExcelVoList);
        } catch (IOException e) {
            e.printStackTrace();
            throw new GlobalException(ResultCodeEnum.DATA_ERROR);
        }
    }
}
