package com.spzx.order.controller;

import com.spzx.model.dto.webapp.OrderDto;
import com.spzx.model.entity.webapp.OrderInfo;
import com.spzx.model.vo.common.Result;
import com.spzx.model.globalConstant.ResultCodeEnum;
import com.spzx.model.vo.webapp.TradeVo;
import com.spzx.order.service.OrderInfoService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "订单管理")
@RestController
@RequestMapping(value="/api/order/orderInfo/auth")
@SuppressWarnings({"unchecked", "rawtypes"})
public class OrderController {
   
   @Autowired
   private OrderInfoService orderInfoService;

   @Operation(summary = "从购物车下单")
   @GetMapping("/trade")
   public Result<TradeVo> generateOrder() {
      TradeVo tradeVo = orderInfoService.generateOrder();
      return Result.build(tradeVo, ResultCodeEnum.SUCCESS);
   }

   @Operation(summary = "订单提交")
   @PostMapping("/submitOrder")
   public Result<Long> submitOrder(@RequestBody OrderDto orderDto) {
      Long orderId = orderInfoService.submitOrder(orderDto);
      return Result.build(orderId, ResultCodeEnum.SUCCESS);
   }

   @Operation(summary = "商品界面下单购买")
   @GetMapping("/buy/{skuId}")
   public Result<TradeVo> buy(@PathVariable(value = "skuId") String skuId) {
      TradeVo tradeVo;
      tradeVo = orderInfoService.buy(Long.valueOf(skuId));
      return Result.build(tradeVo, ResultCodeEnum.SUCCESS);
   }

   @Operation(summary = "获取订单分页列表")
   @GetMapping("/{page}/{limit}")
   public Result<PageInfo<OrderInfo>> select(@PathVariable(value = "page") Integer page,
                                             @PathVariable(value = "limit") Integer limit,
                                             @RequestParam(value = "orderStatus", required = false) Integer orderStatus) {
      PageInfo<OrderInfo> pageInfo = orderInfoService.select(page, limit, orderStatus);
      return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
   }

   //远程调用：根据订单编号获取订单信息
   @Operation(summary = "根据订单编号获取订单信息")
   @GetMapping("/getOrderInfoByOrderNo/{orderNo}")
   public Result<OrderInfo> selectByOrderNo(@PathVariable(value = "orderNo") String orderNo) {
      OrderInfo orderInfo = orderInfoService.selectByOrderNo(orderNo) ;
      return Result.build(orderInfo, ResultCodeEnum.SUCCESS);
   }

   @Operation(summary = "订单获取")
   @GetMapping("/{orderId}")
   public Result<OrderInfo> selectByOrderId(@PathVariable(value = "orderId") Long orderId) {
      OrderInfo orderInfo = orderInfoService.selectByOrderId(orderId);
      return Result.build(orderInfo, ResultCodeEnum.SUCCESS);
   }

   //远程调用：更新订单状态
   @Operation(summary = "更新订单状态")
   @GetMapping("/updateOrderStatusPayed/{orderNo}/{orderStatus}")
   public Result updateOrderStatus(@PathVariable(value = "orderNo") String orderNo,
                                   @PathVariable(value = "orderStatus") Integer orderStatus) {
      orderInfoService.updateOrderStatus(orderNo , orderStatus);
      return Result.build(null, ResultCodeEnum.SUCCESS);
   }

}