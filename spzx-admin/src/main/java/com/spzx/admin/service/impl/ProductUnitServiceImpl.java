package com.spzx.admin.service.impl;

import com.spzx.admin.mapper.ProductUnitMapper;
import com.spzx.admin.service.ProductUnitService;
import com.spzx.model.entity.base.ProductUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ljl
 * @create 2023-10-29-17:08
 */
@Service
public class ProductUnitServiceImpl implements ProductUnitService {
    @Autowired
    private ProductUnitMapper productUnitMapper ;

    @Override
    public List<ProductUnit> selectAll() {
        return productUnitMapper.findAll() ;
    }
}
