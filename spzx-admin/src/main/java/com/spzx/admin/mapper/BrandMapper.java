package com.spzx.admin.mapper;

import com.spzx.model.entity.common.Brand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: BrandMapper
 * @author: yck
 * @create: 2024-02-26
 */

@Mapper
public interface BrandMapper {

    void insert(Brand brand);

    void update(Brand brand);

    void delete(Long brandId);

    List<Brand> select();

    List<Brand> selectAll();
}
