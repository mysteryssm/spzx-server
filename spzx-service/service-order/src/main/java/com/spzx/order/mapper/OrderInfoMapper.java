package com.spzx.order.mapper;

import com.spzx.model.entity.webapp.OrderInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderInfoMapper {

    void insert(OrderInfo orderInfo);

    void update(OrderInfo orderInfo);

    OrderInfo selectByOrderId(Long orderId);

    List<OrderInfo> select(Long userId, Integer orderStatus);

    OrderInfo selectByOrderNo(String orderNo);
}