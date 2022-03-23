package com.qin.web.sys_menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qin.web.sys_menu.entity.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 秦家乐
 * @date 2022/3/15 9:16
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    
    //根据用户id查询权限
    List<SysMenu> getMenuByUserId(@Param("userId") Long userId);
    
    //根据读者id查询权限，这两个查询几乎是一样的
    List<SysMenu> getReaderMenuByUserId(@Param("readerId") Long readerId);
    
    //根据角色id查询权限
    List<SysMenu> getMenuByRoleId(@Param("roleId") Long roleId);
    
}
