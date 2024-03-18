package com.spzx.product.service.impl;

import com.spzx.model.entity.common.Brand;
import com.spzx.product.mapper.BrandMapper;
import com.spzx.product.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    
	@Autowired
	private BrandMapper brandMapper;

    // value = "brandList"，定义缓存名称是"brandList"
    // unless="#result.size() == 0"，当方法返回值的size等于0时，不进行缓存
	@Cacheable(value = "brandList", unless="#result.size() == 0")
    @Override
    public List<Brand> selectAll() {
        return brandMapper.selectAll();
    }

}