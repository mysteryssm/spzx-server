package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.system.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: sysMenuMapper
 * @author: yck
 * @create: 2024-02-21
 */

@Mapper
public interface SysMenuMapper {

    List<SysMenu> findAllNodes();
}
