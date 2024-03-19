package com.spzx.user.controller;

import com.github.pagehelper.PageInfo;
import com.spzx.model.entity.webapp.UserCollect;
import com.spzx.model.globalConstant.ResultCodeEnum;
import com.spzx.model.vo.common.Result;
import com.spzx.model.vo.webapp.UserInfoVo;
import com.spzx.user.service.UserCollectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: UserCollectController
 * @author: yck
 * @create: 2024-03-19
 */

@Tag(name = "用户商品收藏接口")
@RestController
@RequestMapping("/api/user/userInfo")
public class UserCollectController {

    @Autowired
    private UserCollectService userCollectService;

    @Operation(summary = "商品收藏")
    @GetMapping(value = "/auth/collect/{skuId}")
    public Result insertCollect(@PathVariable(name = "skuId") String skuId) {
        if (null ==  skuId || skuId.isEmpty() || "undefined".equals(skuId)) {   // 参数校验，确保 skuId 不为空
            return Result.build(null, ResultCodeEnum.PRODUCT_EXIST_ERROR);
        } else {
            userCollectService.insertCollect(Long.valueOf(skuId));
            return Result.build(null, ResultCodeEnum.SUCCESS);
        }
    }

    @Operation(summary = "商品取消收藏")
    @GetMapping("/auth/cancelCollect/{skuId}")
    public Result deleteCollect(@PathVariable(name = "skuId") String skuId) {
        if (null ==  skuId || skuId.isEmpty() || "undefined".equals(skuId)) {
            return Result.build(null, ResultCodeEnum.PRODUCT_EXIST_ERROR);
        } else {
            userCollectService.deleteCollect(Long.valueOf(skuId));
            return Result.build(null, ResultCodeEnum.SUCCESS);
        }
    }

    @Operation(summary = "商品收藏分页查询")
    @GetMapping(value = "/auth/findUserCollectPage/{page}/{limit}")
    public Result<UserInfoVo> selectCollect(@PathVariable(name = "page") Integer page,
                                            @PathVariable(name = "limit") Integer limit) {
        PageInfo<UserCollect> pageInfo = userCollectService.selectCollect(page, limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "商品是否收藏查询")
    @GetMapping(value = "/auth/select/collect/skuId")
    public Result<Boolean> selectCollectBySkuId(@PathVariable(value = "productId") Long productId) {
        Boolean isCollect = userCollectService.selectCollectBySkuId(productId);
        return Result.build(isCollect, ResultCodeEnum.SUCCESS);
    }
}
