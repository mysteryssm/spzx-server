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

    void insertCollect(Long skuId);

    @Transactional
    void deleteCollect(Long skuId);

    PageInfo<UserCollect> selectCollect(Integer page, Integer limit);

    Boolean selectCollectBySkuId(Long productId);
}
