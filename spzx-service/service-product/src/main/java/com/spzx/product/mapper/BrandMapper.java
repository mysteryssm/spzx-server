package com.spzx.product.mapper;

import com.spzx.model.entity.common.Brand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

// com.spzx.product.mapper;
@Mapper
public interface BrandMapper {

    List<Brand> selectAll();

}