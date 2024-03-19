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

    void deleteBrowseHistory(Long skuId);

    PageInfo<UserBrowseHistory> selectBrowseHistory(Integer page, Integer limit);
}
