package com.spzx.user.service;

import com.github.pagehelper.PageInfo;
import com.spzx.model.entity.webapp.UserBrowseHistory;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description: UserBrowseHistoryService
 * @author: yck
 * @create: 2024-03-19
 */

public interface UserBrowseHistoryService {

    @Transactional
    void insertBrowseHistory(Long skuId);

    PageInfo<UserBrowseHistory> selectBrowseBySkuId(Integer page, Integer limit);

    PageInfo<UserBrowseHistory> selectBrowse(Integer page, Integer limit);
}
