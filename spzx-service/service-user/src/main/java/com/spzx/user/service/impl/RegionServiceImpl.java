package com.spzx.user.service.impl;

import com.spzx.model.entity.base.Region;
import com.spzx.user.mapper.UserRegionMapper;
import com.spzx.user.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ljl
 * @create 2023-11-04-20:55
 */

@Service
public class RegionServiceImpl implements RegionService {

    @Autowired
    private UserRegionMapper userRegionMapper;
    @Override
    public List<Region> selectRegionByParentCode(Integer code) {
        List<Region> regions = userRegionMapper.selectByParentCode(code);
        return regions;
    }

}
