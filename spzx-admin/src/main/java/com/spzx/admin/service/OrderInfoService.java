package com.spzx.admin.service;

import com.spzx.model.dto.admin.OrderStatisticsDto;
import com.spzx.model.vo.admin.OrderStatisticsVo;

/**
 * @author ljl
 * @create 2023-10-30-22:56
 */
public interface OrderInfoService {
    OrderStatisticsVo getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto);
}
