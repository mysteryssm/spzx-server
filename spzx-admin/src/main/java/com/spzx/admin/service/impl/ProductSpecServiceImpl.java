package com.spzx.admin.service.impl;

import com.spzx.admin.mapper.ProductSpecMapper;
import com.spzx.admin.service.ProductSpecService;
import com.spzx.model.entity.product.ProductSpec;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSpecServiceImpl implements ProductSpecService {

    @Autowired
    private ProductSpecMapper productSpecMapper ;

    @Override
    public PageInfo<ProductSpec> selectByPage(Integer page, Integer limit) {
        PageHelper.startPage(page , limit) ;
        List<ProductSpec> productSpecList = productSpecMapper.findByPage();
        return new PageInfo<>(productSpecList);
    }

    @Override
    public void insert(ProductSpec productSpec) {
        productSpecMapper.save(productSpec) ;
    }

    @Override
    public void update(ProductSpec productSpec) {
        productSpecMapper.updateById(productSpec);
    }

    @Override
    public void delete(Long id) {
        productSpecMapper.deleteById(id);
    }

    @Override
    public List<ProductSpec> selectAll() {
        return productSpecMapper.findAll();
    }

}