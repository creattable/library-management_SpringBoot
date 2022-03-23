package com.qin.web.sys_role_menu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qin.web.sys_role_menu.entityt.RoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 秦家乐
 * @date 2022/3/23 15:51
 */
public interface RoleMenuService extends IService<RoleMenu> {
    //保存角色的权限
    void assignSave(Long roleId,List<Long> menuList);
}
