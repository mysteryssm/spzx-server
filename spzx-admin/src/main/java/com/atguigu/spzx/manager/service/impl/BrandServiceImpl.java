package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.BrandMapper;
import com.atguigu.spzx.manager.service.BrandService;
import com.atguigu.spzx.model.entity.product.Brand;
import com.github.pagehelper.Page;
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
    public void add(Brand brand) {
        brandMapper.add(brand);
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
    public PageInfo<Brand> query(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);  //设置分页参数
        List<Brand> list = brandMapper.query(); //获取数据列表
        PageInfo pageInfo = new PageInfo(list); //将列表转换为 PageInfo 类型的数据
        return pageInfo;
    }

    @Override
    public List<Brand> queryAll() {
        List<Brand> list = brandMapper.queryAll();
        return list;
    }


}
