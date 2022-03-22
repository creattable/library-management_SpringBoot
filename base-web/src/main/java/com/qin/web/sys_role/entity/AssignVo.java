package com.qin.web.sys_role.entity;

import com.qin.web.sys_menu.entity.SysMenu;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 秦家乐
 * @date 2022/3/22 19:31
 */
@Data
public class AssignVo {

    //当前用户拥有的菜单
    private List<SysMenu> menuList=new ArrayList<>();
    //被分配的角色拥有的菜单id
    private Object[] checkList;
    


}
