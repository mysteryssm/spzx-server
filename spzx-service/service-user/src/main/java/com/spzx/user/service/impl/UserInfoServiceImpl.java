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

@Service
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

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
}