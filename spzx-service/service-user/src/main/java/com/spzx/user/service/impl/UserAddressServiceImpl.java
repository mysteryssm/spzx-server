package com.spzx.user.service.impl;

import com.spzx.model.entity.webapp.UserAddress;
import com.spzx.user.mapper.UserAddressMapper;
import com.spzx.user.mapper.UserRegionMapper;
import com.spzx.user.service.UserAddressService;
import com.spzx.common.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAddressServiceImpl implements UserAddressService {

   @Autowired
   private UserAddressMapper userAddressMapper;

   @Autowired
   private UserRegionMapper userRegionMapper;

   //用户收获地址查询
   @Override
   public List<UserAddress> select() {
      Long userId = AuthContextUtil.getUser().getId();
      return userAddressMapper.findByUserId(userId);
   }

   //用户收货地址修改
   @Override
   public void update(UserAddress userAddress) {
      String provinceCode = userRegionMapper.selectByCode(userAddress.getProvinceCode());
      String cityCode = userRegionMapper.selectByCode(userAddress.getCityCode());
      String districtCode = userRegionMapper.selectByCode(userAddress.getDistrictCode());

      StringBuilder fullAddressBuilder = new StringBuilder();
      fullAddressBuilder.append(provinceCode)
              .append(cityCode)
              .append(districtCode);

      String fullAddress = fullAddressBuilder.toString();
      userAddress.setFullAddress(fullAddress);
      userAddressMapper.updateByid(userAddress);
   }

   //用户收货地址新增
   @Override
   public void insert(UserAddress userAddress) {
      //获取用户userID
      Long userId = AuthContextUtil.getUser().getId();
      userAddress.setUserId(userId);
      //拼接全部地址
      String provinceCode = userRegionMapper.selectByCode(userAddress.getProvinceCode());
      String cityCode = userRegionMapper.selectByCode(userAddress.getCityCode());
      String districtCode = userRegionMapper.selectByCode(userAddress.getDistrictCode());

      StringBuilder fullAddressBuilder = new StringBuilder();
      fullAddressBuilder.append(provinceCode)
              .append(cityCode)
              .append(districtCode);

      String fullAddress = fullAddressBuilder.toString();
      userAddress.setFullAddress(fullAddress);
      userAddressMapper.save(userAddress);
   }

   //用户收货地址刪除
   @Override
   public void delete(Long id) {
      userAddressMapper.removeById(id);
   }

   //获取地址信息
   @Override
   public UserAddress selectByAddressId(Long id) {
      return userAddressMapper.getById(id);
   }
}