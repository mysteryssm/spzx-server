package com.spzx.user.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spzx.common.service.exception.GlobalException;
import com.spzx.common.utils.AuthContextUtil;
import com.spzx.feign.product.ProductFeignClient;
import com.spzx.model.entity.common.ProductSku;
import com.spzx.model.entity.webapp.User;
import com.spzx.model.entity.webapp.UserBrowseHistory;
import com.spzx.model.entity.webapp.UserCollect;
import com.spzx.model.globalConstant.RedisKeyEnum;
import com.spzx.model.globalConstant.ResultCodeEnum;
import com.spzx.model.vo.webapp.ProductSkuVO;
import com.spzx.model.vo.webapp.UserInfoVo;
import com.spzx.user.mapper.UserBrowseHistoryMapper;
import com.spzx.user.mapper.UserCollectMapper;
import com.spzx.user.service.UserCollectService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description: UserCollectServiceImpl
 * @author: yck
 * @create: 2024-03-19
 */

@Service
public class UserCollectServiceImpl implements UserCollectService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserCollectMapper userCollectMapper;

    @Autowired
    private UserBrowseHistoryMapper userBrowseHistoryMapper;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Override
    public void insertCollect(Long skuId) {
        User user = AuthContextUtil.getUser();
        UserCollect userCollect = new UserCollect();

        userCollect.setUserId(user.getId());
        userCollect.setCreateTime(new Date());
        userCollect.setUpdateTime(new Date());
        userCollect.setSkuId(skuId);
        userCollect.setIsDeleted(0);

        userCollectMapper.insertCollect(userCollect);
    }

    @Override
    public PageInfo<UserCollect> selectCollect(Integer page, Integer limit) {
        PageHelper.startPage(page , limit) ;
        User user = AuthContextUtil.getUser();
        //根据条件查询所有数据
        List<UserBrowseHistory> userBrowseHistories = userCollectMapper.findUserCollectPage(user.getId()) ;
        //查询商品的suk信息
        List<ProductSkuVO> productSkus = new ArrayList<>();
        for (UserBrowseHistory userBrowseHistory : userBrowseHistories) {
            ProductSkuVO productSkuVO = new ProductSkuVO();
            ProductSku productSku = productFeignClient.getBySkuId(userBrowseHistory.getSkuId()).getData();
            BeanUtils.copyProperties(productSku, productSkuVO);
            productSkuVO.setSukId(userBrowseHistory.getSkuId());
            productSkus.add(productSkuVO);
        }
        PageInfo<UserCollect> pageInfo = new PageInfo(productSkus);
        return pageInfo;
    }

    @Transactional
    @Override
    public void deleteCollect(Long skuId) {
        User user = AuthContextUtil.getUser();
        userBrowseHistoryMapper.updatecancelCollect(skuId , user.getId());
    }
}
