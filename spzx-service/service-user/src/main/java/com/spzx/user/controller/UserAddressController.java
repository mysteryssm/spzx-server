package com.spzx.user.controller;

import com.spzx.model.entity.base.Region;
import com.spzx.model.entity.webapp.UserAddress;
import com.spzx.model.vo.common.Result;
import com.spzx.model.globalConstant.ResultCodeEnum;
import com.spzx.user.service.UserAddressService;
import com.spzx.user.service.RegionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "用户地址接口")
@RestController
@RequestMapping(value="/api/user")
public class UserAddressController {
   
   @Autowired
   private UserAddressService userAddressService;

   @Autowired
   private RegionService regionService;

   @Operation(summary = "用户收货地址查询")
   @GetMapping(value = "/userAddress/auth/findUserAddressList")
   public Result<List<UserAddress>> select() {
      List<UserAddress> list = userAddressService.select();
      return Result.build(list, ResultCodeEnum.SUCCESS);
   }

   @Operation(summary = "省市区查询")
   @GetMapping (value = "/region/findByParentCode/{code}")
   public Result selectRegionByParentCode(@PathVariable("code") Integer code) {
      List<Region> regionList = regionService.selectRegionByParentCode(code);
      return Result.build(regionList, ResultCodeEnum.SUCCESS);
   }

   @Operation(summary = "用户收货地址修改")
   @PutMapping(value = "/userAddress/auth/updateById")
   public Result update(@RequestBody UserAddress userAddress) {
      userAddressService.update(userAddress);
      return Result.build(null, ResultCodeEnum.SUCCESS);
   }

   @Operation(summary = "用户收货地址新增")
   @PostMapping(value = "/userAddress/auth/save")
   public Result insert(@RequestBody UserAddress userAddress) {
      userAddressService.insert(userAddress);
      return Result.build(null, ResultCodeEnum.SUCCESS);
   }

   @Operation(summary = "用户删除收货地址")
   @DeleteMapping(value = "/userAddress/auth/removeById/{id}")
   public Result delete(@PathVariable(name = "id") Long id) {
      userAddressService.delete(id);
      return Result.build(null, ResultCodeEnum.SUCCESS);
   }

   /**
    * 远程调用获取地址信息
    * @param addressId
    * @return
    */
   @Operation(summary = "获取地址信息")
   @GetMapping(value = "/userAddress/getUserAddress/{id}")
   public UserAddress selectByAddressId(@PathVariable(name = "id") Long addressId) {
      return userAddressService.selectByAddressId(addressId);
   }
}