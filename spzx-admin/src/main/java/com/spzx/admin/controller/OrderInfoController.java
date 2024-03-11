package com.spzx.admin.controller;

import com.spzx.admin.service.OrderInfoService;
import com.spzx.model.dto.order.OrderStatisticsDto;
import com.spzx.model.globalEnum.ResultCodeEnum;
import com.spzx.model.vo.common.Result;
import com.spzx.model.vo.order.OrderStatisticsVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ljl
 * @create 2023-10-30-18:54
 */
@Tag(name = "订单管理接口")
@RestController
@RequestMapping(value="/admin/order")
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService ;

    @Operation(summary = "订单查询")
    @GetMapping("/select/all")
    public Result<OrderStatisticsVo> getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto) {
        OrderStatisticsVo orderStatisticsVo = orderInfoService.getOrderStatisticsData(orderStatisticsDto);
        return Result.build(orderStatisticsVo , ResultCodeEnum.SUCCESS);
    }
}
