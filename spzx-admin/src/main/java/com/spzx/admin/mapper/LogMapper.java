package com.spzx.admin.mapper;

import com.spzx.model.entity.admin.LogEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogMapper {

    //保存日志数据
    void insert(LogEntity logEntity);
}
