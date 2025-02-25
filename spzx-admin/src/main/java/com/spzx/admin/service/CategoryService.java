package com.spzx.admin.service;

import com.spzx.model.entity.common.Category;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @description: CategoryService
 * @author: yck
 * @create: 2024-02-24
 */

public interface CategoryService {
    List<Category> selectCategoryByParentId(Long id);

    void export(HttpServletResponse httpServletResponse);

    void importCategory(MultipartFile multipartFile);
}
