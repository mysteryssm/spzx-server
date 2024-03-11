package com.spzx.admin.service.impl;

import com.spzx.admin.mapper.BrandMapper;
import com.spzx.admin.service.BrandService;
import com.spzx.model.entity.product.Brand;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: BrandServiceImpl
 * @author: yck
 * @create: 2024-02-26
 */

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public void insert(Brand brand) {
        brandMapper.insert(brand);
    }

    @Override
    public void delete(Long brandId) {
        brandMapper.delete(brandId);
    }

    @Override
    public void update(Brand brand) {
        brandMapper.update(brand);
    }

    @Override
    public PageInfo<Brand> selectByPage(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);  //设置分页参数
        List<Brand> list = brandMapper.select(); //获取数据列表
        PageInfo pageInfo = new PageInfo(list); //将列表转换为 PageInfo 类型的数据
        return pageInfo;
    }

    @Override
    public List<Brand> selectAll() {
        List<Brand> list = brandMapper.selectAll();
        return list;
    }


}
