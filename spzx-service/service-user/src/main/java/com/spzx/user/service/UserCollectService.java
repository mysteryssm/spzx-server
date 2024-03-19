package com.spzx.user.service;

import com.github.pagehelper.PageInfo;
import com.spzx.model.entity.webapp.UserCollect;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description: UserCollectService
 * @author: yck
 * @create: 2024-03-19
 */

public interface UserCollectService {

    void insert(Long skuId);

    @Transactional
    void delete(Long skuId);

    PageInfo<UserCollect> select(Integer page, Integer limit);

    Boolean selectBySkuId(Long productId);
}
