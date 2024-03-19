package com.spzx.user.controller;

import com.github.pagehelper.PageInfo;
import com.spzx.model.entity.webapp.UserBrowseHistory;
import com.spzx.model.globalConstant.ResultCodeEnum;
import com.spzx.model.vo.common.Result;
import com.spzx.model.vo.webapp.UserInfoVo;
import com.spzx.user.service.UserBrowseHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description: UserBrowseHistoryController
 * @author: yck
 * @create: 2024-03-19
 */

@Tag(name = "用户浏览记录接口")
@RestController
@RequestMapping(value = "/api/user/userInfo")
public class UserBrowseHistoryController {

    @Autowired
    private UserBrowseHistoryService userBrowseHistoryService;

    @Operation(summary = "商品浏览信息添加")
    @GetMapping(value = "/isCollect/{id}")
    public Result insertBrowseHistory(@PathVariable("id") String id) {
        if (null ==  id || id.isEmpty() || "undefined".equals(id)){
            return Result.build(null, ResultCodeEnum.PRODUCT_EXIST_ERROR);
        }else {
            userBrowseHistoryService.insertBrowseHistory(Long.valueOf(id));   //保存商品浏览信息
            return Result.build(null, ResultCodeEnum.SUCCESS);
        }
    }

    @Operation(summary = "商品浏览信息删除")
    @DeleteMapping(value = "/auth/browse_history/delete/{id}")
    public Result<Boolean> deleteBrowseHistory(@PathVariable("id") String id) {
        if (null ==  id || id.isEmpty() || "undefined".equals(id)){
            return Result.build(null, ResultCodeEnum.PRODUCT_EXIST_ERROR);
        }else {
            userBrowseHistoryService.deleteBrowseHistory(Long.valueOf(id));
            return Result.build(null, ResultCodeEnum.SUCCESS);
        }
    }

    @Operation(summary = "商品浏览信息分页展示")
    @GetMapping(value = "/auth/findUserBrowseHistoryPage/{page}/{limit}")
    public Result<UserInfoVo> selectBrowse(@PathVariable(name = "page") Integer page,
                                           @PathVariable(name = "limit") Integer limit) {
        PageInfo<UserBrowseHistory> pageInfo = userBrowseHistoryService.selectBrowseHistory(page, limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }
}
