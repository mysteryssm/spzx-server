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

   @Override
   public void insert(UserAddress userAddress) {
      Long userId = AuthContextUtil.getUser().getId();
      userAddress.setUserId(userId);

      String provinceCode = userRegionMapper.select(userAddress.getProvinceCode());
      String cityCode = userRegionMapper.select(userAddress.getCityCode());
      String districtCode = userRegionMapper.select(userAddress.getDistrictCode());

      StringBuilder fullAddressBuilder = new StringBuilder();
      fullAddressBuilder.append(provinceCode)
              .append(cityCode)
              .append(districtCode);

      String fullAddress = fullAddressBuilder.toString();
      userAddress.setFullAddress(fullAddress);
      userAddressMapper.insert(userAddress);
   }

   @Override
   public void update(UserAddress userAddress) {
      String provinceCode = userRegionMapper.select(userAddress.getProvinceCode());
      String cityCode = userRegionMapper.select(userAddress.getCityCode());
      String districtCode = userRegionMapper.select(userAddress.getDistrictCode());

      StringBuilder fullAddressBuilder = new StringBuilder();
      fullAddressBuilder.append(provinceCode)
              .append(cityCode)
              .append(districtCode);

      String fullAddress = fullAddressBuilder.toString();
      userAddress.setFullAddress(fullAddress);
      userAddressMapper.update(userAddress);
   }

   @Override
   public void delete(Long id) {
      userAddressMapper.delete(id);
   }

   @Override
   public List<UserAddress> select() {
      Long userId = AuthContextUtil.getUser().getId();
      return userAddressMapper.select(userId);
   }

   @Override
   public UserAddress selectByAddressId(Long addressId) {
      return userAddressMapper.selectByAddressId(addressId);
   }
}