package com.spzx.admin.service;

import com.atguigu.spzx.model.dto.product.ProductDto;
import com.atguigu.spzx.model.entity.product.Product;
import com.github.pagehelper.PageInfo;

/**
 * @author ljl
 * @create 2023-10-29-16:03
 */
public interface ProductService {
    PageInfo<Product> selectByPage(Integer page, Integer limit, ProductDto productDto);

    void insert(Product product);

    Product selectByProductId(Long id);

    void updateById(Product product);

    void deleteById(Long id);

    void updateAuditStatus(Long id, Integer auditStatus);

    void updateStatus(Long id, Integer status);
}
