package com.qin.web.sys_menu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qin.web.sys_menu.entity.SysMenu;
import org.apache.ibatis.annotations.Param;

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
    
    //根据用户id查询权限
    List<SysMenu> getMenuByUserId(Long userId);
    
    //根据角色id查询权限
    List<SysMenu> getMenuByRoleId(Long roleId);
    
    //根据读者id查询权限，这两个查询几乎是一样的
    List<SysMenu> getReaderMenuByUserId(Long readerId);
    
}
