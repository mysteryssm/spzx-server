package com.spzx.user.service;

import com.spzx.model.dto.webapp.UserLoginDto;
import com.spzx.model.dto.webapp.UserRegisterDto;
import com.spzx.model.entity.webapp.UserBrowseHistory;
import com.spzx.model.entity.webapp.UserCollect;
import com.spzx.model.vo.webapp.UserInfoVo;
import com.github.pagehelper.PageInfo;

// 业务接口
public interface UserInfoService {

    UserInfoVo getCurrentUserInfo(String token);

    /**
     * @Description: 新增浏览商品
     * @param id
     */
    void saveUserCollect(Long id);

    //浏览商品分页展示
    PageInfo<UserCollect> findUserBrowseHistoryPage(Integer page, Integer limit);

    //收藏商品分页展示
    PageInfo<UserBrowseHistory> findUserCollectPage(Integer page, Integer limit);

    //取消收藏
    void updatecancelCollect(Long skuId);

    //商品收藏
    void savecollect(Long skuId);

    //查看用户是否收藏
    Boolean findUserisCollect(Long skuId);

//    远程调用：获取浏览量最多的商品
    UserBrowseHistory getMostFrequentSkuId();
}