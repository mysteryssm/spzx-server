package com.spzx.order.mapper;

import com.spzx.model.entity.webapp.OrderItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderItemMapper {

    void insert(OrderItem orderItem);

    List<OrderItem> selectByOrderId(Long id);
}