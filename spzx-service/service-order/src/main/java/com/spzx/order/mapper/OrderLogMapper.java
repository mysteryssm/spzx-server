package com.spzx.order.mapper;

import com.spzx.model.entity.webapp.OrderLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderLogMapper {
    void save(OrderLog orderLog);
}