package com.spzx.admin.task;

import cn.hutool.core.date.DateUtil;
import com.spzx.admin.mapper.OrderInfoMapper;
import com.spzx.admin.mapper.OrderStatisticsMapper;
import com.spzx.model.entity.order.OrderStatistics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ljl
 * @create 2023-10-30-18:50
 */

@Component
@Slf4j
public class OrderStatisticsTask {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrderStatisticsMapper orderStatisticsMapper;

    @Scheduled(cron = "0 0 2 * * ?")
    public void orderTotalAmountStatistics() {
        // 获取前一天的日期
        String createTime = DateUtil.offsetDay(new Date(), -1).
                toString(new SimpleDateFormat("yyyy-MM-dd"));
        OrderStatistics orderStatistics = orderInfoMapper.selectOrderStatistics(createTime);
        if(orderStatistics != null) {
            orderStatisticsMapper.insert(orderStatistics);
        }
    }

}
