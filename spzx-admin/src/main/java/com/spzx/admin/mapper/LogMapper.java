package com.spzx.admin.mapper;

import com.atguigu.spzx.model.entity.log.LogEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogMapper {

    //保存日志数据
    void insert(LogEntity logEntity);
}
