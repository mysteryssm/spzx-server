package com.spzx.admin.mapper;

import com.spzx.model.dto.system.MenuDto;
import com.spzx.model.entity.admin.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: sysMenuMapper
 * @author: yck
 * @create: 2024-02-21
 */

@Mapper
public interface MenuMapper {

    List<Menu> findAllNodes();

    void save(MenuDto menuDto);

    void update(MenuDto menuDto);

    void delete(Long id);

    int countChildren(Long id);

    List<Menu> findMenusByUserId(Long userId);

    void updateParentMenuIsHalf(Long parentId);

    Long findParentMenuId(Long parentId);
}
