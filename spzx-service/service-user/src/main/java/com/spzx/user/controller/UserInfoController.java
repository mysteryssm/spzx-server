package com.spzx.user.controller;

import com.spzx.model.entity.webapp.UserBrowseHistory;
import com.spzx.model.entity.webapp.UserCollect;
import com.spzx.model.vo.common.Result;
import com.spzx.model.globalConstant.ResultCodeEnum;
import com.spzx.model.vo.webapp.UserInfoVo;
import com.spzx.user.service.UserInfoService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户信息接口")
@RestController
@RequestMapping("/api/user/userInfo")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Operation(summary = "当前登录用户信息获取")
    @GetMapping(value = "/auth/getCurrentUserInfo")
    public Result<UserInfoVo> getCurrentUserInfo(HttpServletRequest request) {
        String token = request.getHeader("token");
        UserInfoVo userInfoVo = userInfoService.getCurrentUserInfo(token);

//        User user = AuthContextUtil.getUser();
//        UserInfoVo userInfoVo = new UserInfoVo();
//        BeanUtil.copyProperties(user, userInfoVo);

        return Result.build(userInfoVo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "新增商品浏览信息")
    @GetMapping(value = "/isCollect/{id}")
    public Result<UserInfoVo> saveUserCollect(@PathVariable("id") String id) {
        if ("undefined".equals(id)){
            return Result.build(false , ResultCodeEnum.SUCCESS) ;
        }else {
            //保存商品浏览信息
            userInfoService.saveUserCollect(Long.valueOf(id));
            //查询是否收藏
            Boolean bool = userInfoService.findUserisCollect(Long.valueOf(id));

            return Result.build(bool , ResultCodeEnum.SUCCESS) ;
        }
    }

    @Operation(summary = "商品浏览信息分页展示")
    @GetMapping(value = "/auth/findUserBrowseHistoryPage/{page}/{limit}")
    public Result<UserInfoVo> findUserBrowseHistoryPage(@PathVariable(name = "page") Integer page,
                                                        @PathVariable(name = "limit") Integer limit) {
        PageInfo<UserCollect> pageInfo = userInfoService.findUserBrowseHistoryPage(page, limit);
        return Result.build(pageInfo , ResultCodeEnum.SUCCESS) ;
    }

    @Operation(summary = "收藏商品分页展示")
    @GetMapping(value = "/auth/findUserCollectPage/{page}/{limit}")
    public Result<UserInfoVo> findUserCollectPage(@PathVariable(name = "page") Integer page,
                                                  @PathVariable(name = "limit") Integer limit) {
        PageInfo<UserBrowseHistory> pageInfo = userInfoService.findUserCollectPage(page, limit);
        return Result.build(pageInfo , ResultCodeEnum.SUCCESS) ;
    }

    @Operation(summary = "取消收藏")
    @GetMapping("/auth/cancelCollect/{skuId}")
    public Result updatecancelCollect(@PathVariable(name = "skuId") String skuId) {
        // 参数校验，确保skuId不为空
        if (skuId == null || skuId.isEmpty() || skuId.equals("undefined")) {
            // 获取当前浏览量最多的商品并取消收藏
            userInfoService.updatecancelCollect(userInfoService.getMostFrequentSkuId().getSkuId());
        } else {
            long id = skuId.equals("undefined") ? userInfoService.getMostFrequentSkuId().getSkuId() : Long.valueOf(skuId);
            userInfoService.updatecancelCollect(id);
        }
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


    @Operation(summary = "添加收藏")
    @GetMapping(value = "/auth/collect/{skuId}")
    public Result savecollect(@PathVariable(name = "skuId") String skuId) {
        // 参数校验，确保skuId不为空
        if (skuId == null || skuId.isEmpty() || skuId.equals("undefined")) {
            // 获取当前浏览量最多的商品
            UserBrowseHistory userBrowseHistory = userInfoService.getMostFrequentSkuId();
            userInfoService.savecollect(userBrowseHistory.getSkuId());
        } else {
            // 使用三元运算符简化代码逻辑
            long id = skuId.equals("undefined") ? userInfoService.getMostFrequentSkuId().getSkuId() : Long.valueOf(skuId);
            userInfoService.savecollect(id);
        }
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


    /**
     * @Description: 远程调用：获取浏览量最多的商品
     */
    @Operation(summary = "获取浏览量最多的商品")
    @GetMapping("/auth/BrowseHistory")
    public UserBrowseHistory getByBrowseHistory() {
        UserBrowseHistory userBrowseHistory = userInfoService.getMostFrequentSkuId();
        return userBrowseHistory;
    }

}