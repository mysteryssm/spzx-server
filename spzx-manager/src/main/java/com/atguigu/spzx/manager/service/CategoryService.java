package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.entity.product.Category;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @description: CategoryService
 * @author: yck
 * @create: 2024-02-24
 */

public interface CategoryService {
    List<Category> queryCategoryByParentId(Long parentId);

    void export(HttpServletResponse httpServletResponse);

    void importCategory(MultipartFile multipartFile);
}
