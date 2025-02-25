package com.spzx.admin.mapper;

import com.spzx.model.entity.admin.OrderStatistics;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ljl
 * @create 2023-10-30-18:51
 */
@Mapper
public interface OrderInfoMapper {

    // 查询指定日期产生的订单数据
    OrderStatistics selectOrderStatistics(String creatTime);

}
