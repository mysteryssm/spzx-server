package com.spzx.user.service;

import com.github.pagehelper.PageInfo;
import com.spzx.model.entity.webapp.UserCollect;
import com.spzx.model.vo.webapp.UserInfoVo;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description: UserCollectService
 * @author: yck
 * @create: 2024-03-19
 */

public interface UserCollectService {

    void insertCollect(Long skuId);

    PageInfo<UserCollect> selectCollect(Integer page, Integer limit);

    @Transactional
    void deleteCollect(Long skuId);
}
