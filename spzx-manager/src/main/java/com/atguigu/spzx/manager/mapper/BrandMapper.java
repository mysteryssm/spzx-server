package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.product.Brand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: BrandMapper
 * @author: yck
 * @create: 2024-02-26
 */

@Mapper
public interface BrandMapper {

    void add(Brand brand);

    void update(Brand brand);

    void delete(Long brandId);

    List<Brand> query();

    List<Brand> queryAll();
}
