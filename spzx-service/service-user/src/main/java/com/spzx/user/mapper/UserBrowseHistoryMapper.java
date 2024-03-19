package com.spzx.user.mapper;

import com.spzx.model.entity.webapp.UserBrowseHistory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ljl
 * @create 2023-11-12-13:48
 */
@Mapper
public interface UserBrowseHistoryMapper {

    void insert(Long userId, Long skuId);

    void delete(Long userId, Long skuId);

    void update(Long userId, Long skuId);

    UserBrowseHistory selectBySkuId(Long userId, Long skuId);

    List<UserBrowseHistory> select(Long userId);
}
