package com.spzx.common.utils;

import com.spzx.model.entity.admin.Menu;
import com.spzx.model.vo.system.SysMenuVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yck
 * @create 2023-10-26-20:10
 */

public class MenuUtil {

    /**
     * 使用递归方法建菜单
     * @param menuList
     * @return
     */
    public static List<Menu> buildTree(List<Menu> menuList) {
        List<Menu> menuTree = new ArrayList<>();
        for (Menu menu : menuList) {
            //递归入口条件是 Parent_id = 0
            if (menu.getParentId().longValue() == 0) {
                //根据第一层去找下层数据，使用递归完成(第一个参数是当前的第一层菜单，第二个参数是所有菜单的集合)
                menuTree.add(findChildren(menu, menuList));
            }
        }
        return menuTree;
    }

    /**
     * 递归查找子节点
     * @param menuList
     * @return
     */
    public static Menu findChildren(Menu menu, List<Menu> menuList) {
        menu.setChildren(new ArrayList<Menu>());  //对 SysMenu 的 children 进行初始化，即清空
        for (Menu it : menuList) {
            if(menu.getId().longValue() == it.getParentId().longValue()) {
                menu.getChildren().add(findChildren(it, menuList));
            }
        }
        return menu;
    }

    public static List<SysMenuVo> menuList2MenuVoList(List<Menu> list) {
        List<SysMenuVo> menuVolist = new ArrayList<>();
        for(Menu menu : list) {
            menuVolist.add(sysMenu2sysMenuVo(menu));
        }
        return menuVolist;
    }

    public static SysMenuVo sysMenu2sysMenuVo(Menu menu) {
        SysMenuVo sysMenuVo = new SysMenuVo();
        sysMenuVo.setTitle(menu.getTitle());
        sysMenuVo.setName(menu.getComponent());
        if(menu.getChildren() != null || menu.getChildren().size() != 0) {
            sysMenuVo.setChildren(menuList2MenuVoList(menu.getChildren()));
        }
        return sysMenuVo;
    }

}