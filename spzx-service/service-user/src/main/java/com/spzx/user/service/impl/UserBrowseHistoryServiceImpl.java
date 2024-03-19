package com.spzx.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spzx.common.utils.AuthContextUtil;
import com.spzx.feign.product.ProductFeignClient;
import com.spzx.model.entity.common.ProductSku;
import com.spzx.model.entity.webapp.User;
import com.spzx.model.entity.webapp.UserBrowseHistory;
import com.spzx.model.entity.webapp.UserCollect;
import com.spzx.model.vo.webapp.ProductSkuVO;
import com.spzx.user.mapper.UserBrowseHistoryMapper;
import com.spzx.user.service.UserBrowseHistoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    private UserBrowseHistoryMapper userBrowseHistoryMapper;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Override
    @Transactional
    public void insertBrowseHistory(Long skuId) {
        User user = AuthContextUtil.getUser();
        if (user != null) { // 该方法不需要用户登录，但只有用户才可添加浏览记录，所以需要校验
            UserBrowseHistory userBrowseHistory = userBrowseHistoryMapper.selectBrowseHistoryBySkuId(skuId, user.getId());
            if (null == userBrowseHistory) {
                userBrowseHistoryMapper.insertBrowseHistory(skuId, user.getId());
            } else {
                userBrowseHistoryMapper.updateBrowseHistory(skuId, user.getId());
            }
        }
    }

    @Override
    public void deleteBrowseHistory(Long skuId) {
        User user = AuthContextUtil.getUser();
        UserBrowseHistory userBrowseHistory = userBrowseHistoryMapper.selectBrowseHistoryBySkuId(skuId, user.getId());
        if(null != userBrowseHistory) {
            userBrowseHistoryMapper.deleteBrowseHistory(skuId, user.getId());
        }
    }

    @Override
    public PageInfo<UserBrowseHistory> selectBrowseHistory(Integer page, Integer limit) {
        User user = AuthContextUtil.getUser();
        PageHelper.startPage(page, limit);
        List<UserBrowseHistory> userBrowseHistories = userBrowseHistoryMapper.selectBrowseHistory(user.getId());

        List<ProductSkuVO> productSkus = new ArrayList<>();

        for (UserBrowseHistory UserBrowseHistory : userBrowseHistories) {
            ProductSkuVO productSkuVO = new ProductSkuVO();
            ProductSku productSku = productFeignClient.getBySkuId(UserBrowseHistory.getSkuId()).getData();
            BeanUtils.copyProperties(productSku, productSkuVO);
            productSkuVO.setSukId(UserBrowseHistory.getSkuId());
            productSkus.add(productSkuVO);
        }

        PageInfo<UserBrowseHistory> pageInfo = new PageInfo(productSkus);
        return pageInfo;
    }
}
