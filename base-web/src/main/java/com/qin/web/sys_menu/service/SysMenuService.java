package com.qin.web.sys_menu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qin.web.sys_menu.entity.SysMenu;

import java.util.List;

/**
 * @author 秦家乐
 * @date 2022/3/15 9:18
 */
public interface SysMenuService extends IService<SysMenu> {
    //菜单的列表
    List<SysMenu> menuList();
    
    //上级菜单的列表
    List<SysMenu> parentList();
    
    
    
}
