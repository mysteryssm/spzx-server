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
@RequestMapping("/api/order/cart/auth")
public class CartController {

    @Autowired
    private CartService cartService;

    @Operation(summary = "添加购物车")
    @GetMapping("/addToCart/{skuId}/{skuNum}")
    public Result insert(@PathVariable("skuId") String skuId, @PathVariable("skuNum") Integer skuNum) {
        cartService.insert(Long.valueOf(skuId), skuNum);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "购物车商品删除")
    @DeleteMapping("/deleteCart/{skuId}")
    public Result delete(@PathVariable("skuId") Long skuId) {
        cartService.delete(skuId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "购物车清空")
    @GetMapping("/clearCart")
    public Result deleteAll(){
        cartService.deleteAll();
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "购物车商品选中状态更新")
    @GetMapping("/checkCart/{skuId}/{isChecked}")
    public Result check(@PathVariable(value = "skuId") Long skuId,
                        @PathVariable(value = "isChecked") Integer isChecked) {
        cartService.check(skuId, isChecked);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary="购物车商品选中状态全部更新")
    @GetMapping("/allCheckCart/{isChecked}")
    public Result checkAll(@PathVariable(value = "isChecked") Integer isChecked){
        cartService.checkAll(isChecked);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "查询购物车")
    @GetMapping("/cartList")
    public Result<List<CartInfo>> select() {
        List<CartInfo> cartInfoList = cartService.select();
        return Result.build(cartInfoList, ResultCodeEnum.SUCCESS);
    }

    /**
     * 远程调用订单结算时候使用，获取购物车选中的商品列表
     * @return
     */
    @Operation(summary = "选中的购物车")
    @GetMapping(value = "/getAllChecked")
    public List<CartInfo> selectChecked() {
        List<CartInfo> cartInfoList = cartService.selectChecked();
        return cartInfoList;
    }

    /**
     *  下单时远程调用，删除订单中的购物车商品
     * @return
     */
    @Operation(summary = "下单回调购物车商品删除")
    @GetMapping(value = "/deleteChecked")
    public Result deleteChecked() {
        cartService.deleteChecked();
        return Result.build(null , ResultCodeEnum.SUCCESS);
    }
}