package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.entity.system.SysMenu;

import java.util.List;

/**
 * @description: SysMenuService
 * @author: yck
 * @create: 2024-02-21
 */

public interface SysMenuService {

    List<SysMenu> findAllNodes();
}
