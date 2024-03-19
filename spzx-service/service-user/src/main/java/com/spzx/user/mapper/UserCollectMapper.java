package com.spzx.user.mapper;

import com.spzx.model.entity.webapp.UserBrowseHistory;
import com.spzx.model.entity.webapp.UserCollect;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ljl
 * @create 2023-11-11-21:23
 */

@Mapper
public interface UserCollectMapper {
    void insertBrowse(UserCollect userCollect);

    List<UserCollect> findUserBrowseHistoryPage(Long id);

    List<UserBrowseHistory> findUserCollectPage(Long id);
}
