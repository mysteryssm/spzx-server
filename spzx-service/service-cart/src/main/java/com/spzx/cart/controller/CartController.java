package com.spzx.cart.controller;

import com.spzx.cart.service.CartService;
import com.spzx.feign.user.UserFeignClient;
import com.spzx.model.entity.webapp.CartInfo;
import com.spzx.model.vo.common.Result;
import com.spzx.model.globalConstant.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "购物车接口")
@RestController
@RequestMapping("api/order/cart/auth")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserFeignClient userFeignClient;

    @Operation(summary = "添加购物车")
    @GetMapping("/addToCart/{skuId}/{skuNum}")
    public Result addToCart(@PathVariable("skuId") String skuId, @PathVariable("skuNum") Integer skuNum) {
        Long id = "undefined".equals(skuId) ? userFeignClient.getByBrowseHistory().getSkuId() : Long.valueOf(skuId);
        cartService.insert(id, skuNum);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "购物车商品删除")
    @DeleteMapping("/deleteCart/{skuId}")
    public Result delete(@PathVariable("skuId") Long skuId) {
        cartService.deleteCart(skuId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "购物车清空")
    @GetMapping("/clearCart")
    public Result deleteAll(){
        cartService.deleteAll();
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "更新购物车商品选中状态")
    @GetMapping("/checkCart/{skuId}/{isChecked}")
    public Result checkCart(@PathVariable(value = "skuId") Long skuId, @PathVariable(value = "isChecked") Integer isChecked) {
        cartService.checkCart(skuId, isChecked);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary="更新购物车商品全部选中状态")
    @GetMapping("/allCheckCart/{isChecked}")
    public Result allCheckCart(@PathVariable(value = "isChecked") Integer isChecked){
        cartService.allCheckCart(isChecked);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //远程调用使用，删除生成订单的购物车商品
    @GetMapping(value = "/deleteChecked")
    public Result deleteChecked() {
        cartService.deleteChecked();
        return Result.build(null , ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "查询购物车")
    @GetMapping("/cartList")
    public Result<List<CartInfo>> cartList() {
        List<CartInfo> cartInfoList = cartService.getCartList();
        return Result.build(cartInfoList, ResultCodeEnum.SUCCESS);
    }

    //远程调用订单结算时候使用，获取购物车选中商品列表
    @Operation(summary="选中的购物车")
    @GetMapping(value = "/getAllCkecked")
    public List<CartInfo> getAllChecked() {
        List<CartInfo> cartInfoList = cartService.getAllCkecked();
        return cartInfoList;
    }
}