package com.spzx.admin.service.impl;

import cn.hutool.core.date.DateUtil;
import com.spzx.admin.mapper.OrderStatisticsMapper;
import com.spzx.admin.service.OrderInfoService;
import com.spzx.model.dto.admin.OrderStatisticsDto;
import com.spzx.model.entity.admin.OrderStatistics;
import com.spzx.model.vo.admin.OrderStatisticsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

// com.spzx.admin.service.impl;
@Service
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    private OrderStatisticsMapper orderStatisticsMapper ;

    @Override
    public OrderStatisticsVo getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto) {

        List<OrderStatistics> orderStatisticsList = orderStatisticsMapper.selectList(orderStatisticsDto);    //查询统计结果数据

        //统计日期列表
        List<String> dateList = orderStatisticsList.stream()    // 使用 stream() 将 list 转化为 stream
                .map(orderStatistics -> DateUtil.format(orderStatistics.getOrderDate(), "yyyy-MM-dd"))  //使用 map() 进行元素映射
                .collect(Collectors.toList()); // 使用 collect() 进行归约操作，将 stream 转化为 list

        //统计金额列表
        List<BigDecimal> amountList = orderStatisticsList.stream()
                .map(OrderStatistics::getTotalAmount)
                .collect(Collectors.toList());

        // 创建OrderStatisticsVo对象封装响应结果数据
        OrderStatisticsVo orderStatisticsVo = new OrderStatisticsVo();
        orderStatisticsVo.setDateList(dateList);
        orderStatisticsVo.setAmountList(amountList);

        // 返回数据
        return orderStatisticsVo;
    }
}