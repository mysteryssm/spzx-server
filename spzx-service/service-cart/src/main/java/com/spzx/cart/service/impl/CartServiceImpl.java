package com.spzx.cart.service.impl;

import com.alibaba.fastjson.JSON;
import com.spzx.cart.service.CartService;
import com.spzx.feign.product.ProductFeignClient;
import com.spzx.model.entity.webapp.CartInfo;
import com.spzx.model.entity.common.ProductSku;
import com.spzx.common.utils.AuthContextUtil;
import com.spzx.model.globalConstant.RedisKeyEnum;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author ljl
 * @create 2023-11-04-1:21
 */

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private RedisTemplate<String , String> redisTemplate;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Override
    public void insert(Long skuId, Integer skuNum) {

        Long userId = AuthContextUtil.getUser().getId();    //从 ThreadLocal 中获取用户信息，得到用户 id

        String cartKey = RedisKeyEnum.USER_CART.getKeyPrefix() + userId;    // 获取该用户购物车对应的 key

        // 从redis中获取购物车数据，key field value
        Object cartInfoObj = redisTemplate.opsForHash().get(cartKey, String.valueOf(skuId));
        CartInfo cartInfo;

        if(null != cartInfoObj) {   // 若购物车中已经存在该商品，更新商品数量
            cartInfo = JSON.parseObject(cartInfoObj.toString(), CartInfo.class);
            cartInfo.setSkuNum(cartInfo.getSkuNum() + skuNum);  // 更新购物车中商品数量
            cartInfo.setIsChecked(1);   // 将该商品置为选中状态
            cartInfo.setUpdateTime(new Date()); // 更新购物车修改时间
        }else { // 不存在该商品则添加商品
            cartInfo = new CartInfo();
            ProductSku productSku = productFeignClient.getBySkuId(skuId).getData();   // 根据 skuId 获取商品信息
            cartInfo.setCartPrice(productSku.getSalePrice());
            cartInfo.setSkuNum(skuNum);
            cartInfo.setSkuId(skuId);
            cartInfo.setUserId(userId);
            cartInfo.setImgUrl(productSku.getThumbImg());
            cartInfo.setSkuName(productSku.getSkuName());
            cartInfo.setIsChecked(1);
            cartInfo.setCreateTime(new Date());
            cartInfo.setUpdateTime(new Date());
        }

        // 将商品数据存储到购物车中
        redisTemplate.opsForHash().put(cartKey, String.valueOf(skuId), JSON.toJSONString(cartInfo));
    }

    @Override
    public void delete(Long skuId) {

        Long userId = AuthContextUtil.getUser().getId();    //从 ThreadLocal 中获取用户信息，得到用户 id
        String cartKey = RedisKeyEnum.USER_CART.getKeyPrefix() + userId;     // 获取该用户购物车对应的 key

        redisTemplate.opsForHash().delete(cartKey ,String.valueOf(skuId));

    }

    @Override
    public void deleteAll() {

        Long userId = AuthContextUtil.getUser().getId();
        String cartKey = RedisKeyEnum.USER_CART.getKeyPrefix() + userId;

        redisTemplate.delete(cartKey);  // 通过 cartKey 清空对应的所有 hash 键值对
    }

    @Override
    public void check(Long skuId, Integer isChecked) {

        Long userId = AuthContextUtil.getUser().getId();
        String cartKey = RedisKeyEnum.USER_CART.getKeyPrefix() + userId;

        // 判断是否包含该用户对应的 key 且购物车中是否包含 filed
        if(redisTemplate.opsForHash().hasKey(cartKey, String.valueOf(skuId))) {
            String cartInfoJSON = redisTemplate.opsForHash().get(cartKey, String.valueOf(skuId)).toString();
            CartInfo cartInfo = JSON.parseObject(cartInfoJSON, CartInfo.class);
            if(!Objects.equals(cartInfo.getIsChecked(), isChecked)) {
                cartInfo.setIsChecked(isChecked);
                redisTemplate.opsForHash().put(cartKey, String.valueOf(skuId) , JSON.toJSONString(cartInfo));
            }
        }
    }

    public void checkAll(Integer isChecked) {

        Long userId = AuthContextUtil.getUser().getId();
        String cartKey = RedisKeyEnum.USER_CART.getKeyPrefix() + userId;

        // 获取所有的购物项数据
        List<Object> objectList = redisTemplate.opsForHash().values(cartKey);
        if(!CollectionUtils.isEmpty(objectList)) {
            objectList.stream()
                    .map(cartInfoJSON -> {
                        CartInfo cartInfo = JSON.parseObject(cartInfoJSON.toString(), CartInfo.class);
                        cartInfo.setIsChecked(isChecked);
                        return cartInfo;
                    })
                    .forEach(cartInfo -> redisTemplate.opsForHash().put(cartKey, String.valueOf(cartInfo.getSkuId()) , JSON.toJSONString(cartInfo)));
        }
    }

    @Override
    public List<CartInfo> select() {

        Long userId = AuthContextUtil.getUser().getId();
        String cartKey = RedisKeyEnum.USER_CART.getKeyPrefix() + userId;

        List<Object> cartInfoList = redisTemplate.opsForHash().values(cartKey);
        if (!CollectionUtils.isEmpty(cartInfoList)) {
            List<CartInfo> infoList = cartInfoList.stream()
                    .map(cartInfoJSON -> JSON.parseObject(cartInfoJSON.toString(), CartInfo.class))
                    .sorted((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime()))
                    .collect(Collectors.toList());
            return infoList;
        }

        return new ArrayList<>() ;
    }

    @Override
    public List<CartInfo> selectChecked() {

        Long userId = AuthContextUtil.getUser().getId();
        String cartKey = RedisKeyEnum.USER_CART.getKeyPrefix() + userId;
        List<Object> objectList = redisTemplate.opsForHash().values(cartKey);   // 获取所有的购物项数据
        if(!CollectionUtils.isEmpty(objectList)) {
            List<CartInfo> cartInfoList = objectList.stream()
                    .map(cartInfoJSON -> JSON.parseObject(cartInfoJSON.toString(), CartInfo.class))
                    .filter(cartInfo -> cartInfo.getIsChecked() == 1)
                    .collect(Collectors.toList());
            return cartInfoList;
        }
        return new ArrayList<>();
    }

    @Override
    public void deleteChecked() {
        Long userId = AuthContextUtil.getUser().getId();
        String cartKey = RedisKeyEnum.USER_CART.getKeyPrefix() + userId;
        List<Object> objectList = redisTemplate.opsForHash().values(cartKey);       // 删除选中的购物项数据
        if(!CollectionUtils.isEmpty(objectList)) {
            objectList.stream()
                    .map(cartInfoJSON -> JSON.parseObject(cartInfoJSON.toString(), CartInfo.class))
                    .filter(cartInfo -> cartInfo.getIsChecked() == 1)
                    .forEach(cartInfo -> redisTemplate.opsForHash().delete(cartKey , String.valueOf(cartInfo.getSkuId())));
        }
    }
}
