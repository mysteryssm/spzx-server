package com.spzx.admin.mapper;

import com.spzx.model.dto.admin.OrderStatisticsDto;
import com.spzx.model.entity.admin.OrderStatistics;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderStatisticsMapper {

    void insert(OrderStatistics orderStatistics);
    List<OrderStatistics> selectList(OrderStatisticsDto orderStatisticsDto);


}