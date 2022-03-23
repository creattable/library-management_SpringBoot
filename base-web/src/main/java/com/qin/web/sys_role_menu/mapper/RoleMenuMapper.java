package com.qin.web.sys_role_menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qin.web.sys_role_menu.entityt.RoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 秦家乐
 * @date 2022/3/23 15:50
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    //保存角色的权限
    void assignSave(@Param("roleId") Long roleId, @Param("menuList") List<Long> menuList);
}
