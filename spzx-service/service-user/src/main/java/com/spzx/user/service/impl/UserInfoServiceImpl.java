package com.spzx.user.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.spzx.common.service.exception.GlobalException;
import com.spzx.feign.product.ProductFeignClient;
import com.spzx.model.entity.common.ProductSku;
import com.spzx.model.entity.webapp.UserBrowseHistory;
import com.spzx.model.entity.webapp.UserCollect;
import com.spzx.model.entity.webapp.User;
import com.spzx.model.globalConstant.RedisKeyEnum;
import com.spzx.model.globalConstant.ResultCodeEnum;
import com.spzx.model.vo.webapp.UserInfoVo;
import com.spzx.model.vo.webapp.ProductSkuVO;
import com.spzx.user.mapper.UserBrowseHistoryMapper;
import com.spzx.user.mapper.UserCollectMapper;
import com.spzx.user.service.UserInfoService;
import com.spzx.common.utils.AuthContextUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// 业务接口实现
@Service
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Autowired
	private UserCollectMapper userCollectMapper;

	@Autowired
	private UserBrowseHistoryMapper userBrowseHistoryMapper;

	@Autowired
	private ProductFeignClient productFeignClient;

	@Override
	public UserInfoVo getCurrentUserInfo(String token) {
		String userInfoJSON = redisTemplate.opsForValue().get(RedisKeyEnum.USER_LOGIN_TOKEN.getKeyPrefix() + token);

		// 校验 token 是否有效，即用户是否登录
		if(StrUtil.isEmpty(userInfoJSON)) {
			throw new GlobalException(ResultCodeEnum.USER_LOGIN_AUTH);
		}

		User user = JSON.parseObject(userInfoJSON , User.class);
		UserInfoVo userInfoVo = new UserInfoVo();

		// 将用户信息转化为 响应结果实体类
		BeanUtils.copyProperties(user, userInfoVo);
		return userInfoVo;
	}

	@Override
	public void saveUserCollect(Long id) {
		User user = AuthContextUtil.getUser();
		UserCollect userCollect = new UserCollect();
		userCollect.setUserId(user.getId());
		userCollect.setCreateTime(new Date());
		userCollect.setUpdateTime(new Date());
		userCollect.setSkuId(id);
		userCollect.setIsDeleted(0);
		userCollectMapper.saveUserCollect(userCollect);
	}

	@Override
	public PageInfo<UserCollect> findUserBrowseHistoryPage(Integer page, Integer limit) {
		PageHelper.startPage(page , limit) ;
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
		PageInfo<UserCollect> pageInfo = new PageInfo(productSkus);
		return pageInfo;
	}

	@Override
	public PageInfo<UserBrowseHistory> findUserCollectPage(Integer page, Integer limit) {
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
		PageInfo<UserBrowseHistory> pageInfo = new PageInfo(productSkus);
		return pageInfo;
	}

	//取消收藏
	@Transactional
	@Override
	public void updatecancelCollect(Long skuId) {
		User user = AuthContextUtil.getUser();
		userBrowseHistoryMapper.updatecancelCollect(skuId , user.getId());

	}

	//添加收藏
	@Override
	@Transactional
	public void savecollect(Long skuId) {
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

	//查询商品是否已经收藏
	@Override
	public Boolean findUserisCollect(Long skuId) {
		User user = AuthContextUtil.getUser();
		if (user != null) {
			UserBrowseHistory userBrowseHistory = userBrowseHistoryMapper.selectusercollect(skuId, user.getId());
			if (userBrowseHistory == null) {
				return false;
			} else {
				return true;
			}
		} else {
			throw new GlobalException(ResultCodeEnum.USER_REGISTER_DATA_ERROR);
		}
	}

	@Override
	public UserBrowseHistory getMostFrequentSkuId() {
		UserBrowseHistory userBrowseHistory = userBrowseHistoryMapper.getMostFrequentSkuId();
		return userBrowseHistory;
	}

}