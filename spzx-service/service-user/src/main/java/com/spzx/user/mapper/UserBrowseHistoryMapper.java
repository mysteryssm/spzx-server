package com.spzx.user.mapper;

import com.spzx.model.entity.webapp.UserBrowseHistory;
import com.spzx.model.entity.webapp.UserCollect;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author ljl
 * @create 2023-11-12-13:48
 */
@Mapper
public interface UserBrowseHistoryMapper {

    void insertBrowseHistory(Long skuId, Long userId);

    void deleteBrowseHistory(Long skuId, Long userId);

    void updateBrowseHistory(Long skuId, Long userId);

    UserBrowseHistory selectBrowseHistoryBySkuId(Long skuId, Long userId);

    List<UserBrowseHistory> selectBrowseHistory(Long userId);
}
