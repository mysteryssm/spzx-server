package com.spzx.user.mapper;

import com.spzx.model.entity.base.Region;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ljl
 * @create 2023-11-04-21:04
 */
@Mapper
public interface UserRegionMapper {

    List<Region> selectByParentCode(Integer code);

    String select(String Code);
}
