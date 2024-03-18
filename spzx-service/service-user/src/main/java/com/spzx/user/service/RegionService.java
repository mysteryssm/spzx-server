package com.spzx.user.service;

import com.spzx.model.entity.base.Region;

import java.util.List;

/**
 * @author ljl
 * @create 2023-11-04-20:55
 */
public interface RegionService {

    /**
     * @Description: 收货地址省市区显示
     * @param code
     */
    List<Region> selectRegionByParentCode(Integer code);
}
