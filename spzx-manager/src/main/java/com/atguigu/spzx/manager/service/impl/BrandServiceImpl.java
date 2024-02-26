package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: BrandServiceImpl
 * @author: yck
 * @create: 2024-02-26
 */

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

}
