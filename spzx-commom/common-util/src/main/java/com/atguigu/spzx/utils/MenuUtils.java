package com.atguigu.spzx.utils;

import com.atguigu.spzx.model.entity.system.SysMenu;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yck
 * @create 2023-10-26-20:10
 */

public class MenuUtils {

    /**
     * 使用递归方法建菜单
     * @param sysMenuList
     * @return
     */
    public static List<SysMenu> buildTree(List<SysMenu> sysMenuList) {
        List<SysMenu> sysMenuTree = new ArrayList<>();
        for (SysMenu sysMenu : sysMenuList) {
            //递归入口条件是 Parent_id = 0
            if (sysMenu.getParentId().longValue() == 0) {
                //根据第一层去找下层数据，使用递归完成(第一个参数是当前的第一层菜单，第二个参数是所有菜单的集合)
                sysMenuTree.add(findChildren(sysMenu,sysMenuList));
            }
        }
        return sysMenuTree;
    }

    /**
     * 递归查找子节点
     * @param sysMenuList
     * @return
     */
    public static SysMenu findChildren(SysMenu sysMenu, List<SysMenu> sysMenuList) {
        sysMenu.setChildren(new ArrayList<SysMenu>());  //对 SysMenu 的 children 进行初始化，即清空
        for (SysMenu it : sysMenuList) {
            if(sysMenu.getId().longValue() == it.getParentId().longValue()) {
                sysMenu.getChildren().add(findChildren(it, sysMenuList));
            }
        }
        return sysMenu;
    }
}