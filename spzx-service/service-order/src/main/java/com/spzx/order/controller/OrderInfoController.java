package com.spzx.order.controller;

import com.spzx.model.dto.webapp.OrderDto;
import com.spzx.model.entity.webapp.OrderInfo;
import com.spzx.model.vo.common.Result;
import com.spzx.model.globalConstant.ResultCodeEnum;
import com.spzx.model.vo.webapp.TradeVo;
import com.spzx.order.service.OrderInfoService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "订单管理")
@RestController
@RequestMapping(value="/api/order/orderInfo/auth")
@SuppressWarnings({"unchecked", "rawtypes"})
public class OrderInfoController {
   
   @Autowired
   private OrderInfoService orderInfoService;

   @Operation(summary = "确认下单")
   @GetMapping("/trade")
   public Result<TradeVo> trade() {
      TradeVo tradeVo = orderInfoService.getTrade();
      return Result.build(tradeVo, ResultCodeEnum.SUCCESS);
   }

   @Operation(summary = "提交订单")
   @PostMapping("/submitOrder")
   public Result<Long> submitOrder(@Parameter(name = "orderInfoDto", description = "请求参数实体类", required = true) @RequestBody OrderDto orderDto) {
      Long orderId = orderInfoService.submitOrder(orderDto);
      return Result.build(orderId, ResultCodeEnum.SUCCESS);
   }

   @Operation(summary = "获取订单信息")
   @GetMapping("/{orderId}")
   public Result<OrderInfo> getOrderInfo(@Parameter(name = "orderId", description = "订单id", required = true) @PathVariable Long orderId) {
      OrderInfo orderInfo = orderInfoService.getOrderInfo(orderId);
      return Result.build(orderInfo, ResultCodeEnum.SUCCESS);
   }

   @Operation(summary = "立即购买")
   @GetMapping("/buy/{skuId}")
   public Result<TradeVo> buy(@PathVariable(value = "skuId") String skuId) {
      TradeVo tradeVo;
      tradeVo = orderInfoService.buy(Long.valueOf(skuId));
      return Result.build(tradeVo, ResultCodeEnum.SUCCESS);
   }

   @Operation(summary = "获取订单分页列表")
   @GetMapping("/{page}/{limit}")
   public Result<PageInfo<OrderInfo>> list(
           @Parameter(name = "page", description = "当前页码", required = true)
           @PathVariable Integer page,

           @Parameter(name = "limit", description = "每页记录数", required = true)
           @PathVariable Integer limit,

           @Parameter(name = "orderStatus", description = "订单状态", required = false)
           @RequestParam(required = false, defaultValue = "") Integer orderStatus) {
      PageInfo<OrderInfo> pageInfo = orderInfoService.findUserPage(page, limit, orderStatus);
      return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
   }

   //远程调用：根据订单编号获取订单信息
   @Operation(summary = "获取订单信息")
   @GetMapping("/getOrderInfoByOrderNo/{orderNo}")
   public Result<OrderInfo> getOrderInfoByOrderNo(@Parameter(name = "orderId", description = "订单id", required = true) @PathVariable String orderNo) {
      OrderInfo orderInfo = orderInfoService.getByOrderNo(orderNo) ;
      return Result.build(orderInfo, ResultCodeEnum.SUCCESS);
   }

   //远程调用：更新订单状态
   @Operation(summary = "获取订单分页列表")
   @GetMapping("/updateOrderStatusPayed/{orderNo}/{orderStatus}")
   public Result updateOrderStatus(@PathVariable(value = "orderNo") String orderNo , @PathVariable(value = "orderStatus") Integer orderStatus) {
      orderInfoService.updateOrderStatus(orderNo , orderStatus);
      return Result.build(null , ResultCodeEnum.SUCCESS) ;
   }

}