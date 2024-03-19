package com.spzx.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spzx.common.utils.AuthContextUtil;
import com.spzx.feign.product.ProductFeignClient;
import com.spzx.model.entity.common.ProductSku;
import com.spzx.model.entity.webapp.User;
import com.spzx.model.entity.webapp.UserCollect;
import com.spzx.model.vo.webapp.ProductSkuVO;
import com.spzx.user.mapper.UserCollectMapper;
import com.spzx.user.service.UserCollectService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    private UserCollectMapper userCollectMapper;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Override
    public void insertCollect(Long skuId) {
        if(!selectCollectBySkuId(skuId)) {
            User user = AuthContextUtil.getUser();
            UserCollect userCollect = new UserCollect();
            userCollect.setUserId(user.getId());
            userCollect.setCreateTime(new Date());
            userCollect.setUpdateTime(new Date());
            userCollect.setSkuId(skuId);
            userCollect.setIsDeleted(0);
            userCollectMapper.insertCollect(userCollect);
        }
    }

    @Transactional
    @Override
    public void deleteCollect(Long skuId) {
        if(selectCollectBySkuId(skuId)) {
            User user = AuthContextUtil.getUser();
            userCollectMapper.deleteCollect(user.getId(), skuId);
        }
    }

    @Override
    public PageInfo<UserCollect> selectCollect(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        User user = AuthContextUtil.getUser();
        List<UserCollect> userCollects = userCollectMapper.selectCollect(user.getId());

        List<ProductSkuVO> productSkus = new ArrayList<>();
        for (UserCollect userCollect : userCollects) {
            ProductSkuVO productSkuVO = new ProductSkuVO();
            ProductSku productSku = productFeignClient.getBySkuId(userCollect.getSkuId()).getData();
            BeanUtils.copyProperties(productSku, productSkuVO);
            productSkuVO.setSukId(userCollect.getSkuId());
            productSkus.add(productSkuVO);
        }

        PageInfo<UserCollect> pageInfo = new PageInfo(productSkus);
        return pageInfo;
    }

    @Override
    public Boolean selectCollectBySkuId(Long skuId) {
        User user = AuthContextUtil.getUser();
        UserCollect userCollect = userCollectMapper.selectCollectBySkuId(user.getId(), skuId);
        return null != userCollect;
    }
}
