package com.spzx.order.service;

import com.spzx.model.dto.webapp.OrderDto;
import com.spzx.model.entity.webapp.OrderInfo;
import com.spzx.model.vo.webapp.TradeVo;
import com.github.pagehelper.PageInfo;

public interface OrderInfoService {
    TradeVo generateOrder();

    Long submitOrder(OrderDto orderDto);

    TradeVo buy(Long skuId);

    PageInfo<OrderInfo> select(Integer page, Integer limit, Integer orderStatus);

    OrderInfo selectByOrderId(Long orderId);

    OrderInfo selectByOrderNo(String orderNo);

    void updateOrderStatus(String orderNo, Integer orderStatus);

}