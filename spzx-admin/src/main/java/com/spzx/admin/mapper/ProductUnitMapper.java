package com.spzx.admin.mapper;

import com.spzx.model.entity.base.ProductUnit;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ljl
 * @create 2023-10-29-17:09
 */
@Mapper
public interface ProductUnitMapper {
    public abstract List<ProductUnit> findAll();
}