package com.spzx.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spzx.common.service.exception.GlobalException;
import com.spzx.common.utils.AuthContextUtil;
import com.spzx.feign.product.ProductFeignClient;
import com.spzx.model.entity.common.ProductSku;
import com.spzx.model.entity.webapp.User;
import com.spzx.model.entity.webapp.UserBrowseHistory;
import com.spzx.model.entity.webapp.UserCollect;
import com.spzx.model.globalConstant.ResultCodeEnum;
import com.spzx.model.vo.webapp.ProductSkuVO;
import com.spzx.user.mapper.UserBrowseHistoryMapper;
import com.spzx.user.mapper.UserCollectMapper;
import com.spzx.user.service.UserBrowseHistoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: UserBrowseHistoryServiceImpl
 * @author: yck
 * @create: 2024-03-19
 */

@Service
public class UserBrowseHistoryServiceImpl implements UserBrowseHistoryService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserCollectMapper userCollectMapper;

    @Autowired
    private UserBrowseHistoryMapper userBrowseHistoryMapper;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Override
    @Transactional
    public void insertBrowseHistory(Long skuId) {
        User user = AuthContextUtil.getUser();
        if (user != null) {
            UserBrowseHistory userBrowseHistory = userBrowseHistoryMapper.selectcollect(skuId, user.getId());
            if (userBrowseHistory == null) {
                userBrowseHistoryMapper.savecollect(skuId, user.getId());
            } else {
                userBrowseHistoryMapper.updatecollect(skuId, user.getId());
            }
        } else {
            throw new GlobalException(ResultCodeEnum.USER_REGISTER_DATA_ERROR);
        }
    }

    @Override
    public PageInfo<UserBrowseHistory> selectBrowseBySkuId(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        User user = AuthContextUtil.getUser();
        //根据条件查询所有数据
        List<UserCollect> userCollects = userCollectMapper.findUserBrowseHistoryPage(user.getId()) ;
        //查询商品的suk信息
        List<ProductSkuVO> productSkus = new ArrayList<>();

        for (UserCollect userCollect : userCollects) {
            ProductSkuVO productSkuVO = new ProductSkuVO();
            ProductSku productSku = productFeignClient.getBySkuId(userCollect.getSkuId()).getData();
            BeanUtils.copyProperties(productSku, productSkuVO);
            productSkuVO.setSukId(userCollect.getSkuId());
            productSkus.add(productSkuVO);
        }
        PageInfo<UserBrowseHistory> pageInfo = new PageInfo(productSkus);
        return pageInfo;
    }

    @Override
    public PageInfo<UserBrowseHistory> selectBrowse(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        User user = AuthContextUtil.getUser();
        //根据条件查询所有数据
        List<UserCollect> userCollects = userCollectMapper.findUserBrowseHistoryPage(user.getId()) ;
        //查询商品的suk信息
        List<ProductSkuVO> productSkus = new ArrayList<>();

        for (UserCollect userCollect : userCollects) {
            ProductSkuVO productSkuVO = new ProductSkuVO();
            ProductSku productSku = productFeignClient.getBySkuId(userCollect.getSkuId()).getData();
            BeanUtils.copyProperties(productSku, productSkuVO);
            productSkuVO.setSukId(userCollect.getSkuId());
            productSkus.add(productSkuVO);
        }
        PageInfo<UserBrowseHistory> pageInfo = new PageInfo(productSkus);
        return pageInfo;
    }
}
