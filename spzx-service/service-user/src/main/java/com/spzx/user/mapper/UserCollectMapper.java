package com.spzx.user.mapper;

import com.spzx.model.entity.webapp.UserCollect;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ljl
 * @create 2023-11-11-21:23
 */

@Mapper
public interface UserCollectMapper {
    void insertCollect(UserCollect userCollect);

    void deleteCollect(Long userId, Long skuId);

    UserCollect selectCollectBySkuId(Long userId, Long skuId);

    List<UserCollect> selectCollect(Long userId);
}
