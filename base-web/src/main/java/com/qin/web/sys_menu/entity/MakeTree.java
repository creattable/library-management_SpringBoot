package com.qin.web.sys_menu.entity;

import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author 秦家乐
 * @date 2022/3/15 9:35
 */
public class MakeTree {
    
    public static List<SysMenu> makeMenuTree(List<SysMenu> menuList,Long pid){
        //先用ofNullable判断是否为空，如果为空啧默认创建个orElse，
        // 如果不为空，则走stream，通过filter过滤
        //通过过滤 item!=null 并且item的parentId等于传进来的pid
        //过滤了之后再进行forEach处理
        //把menu复制给dom
        
        
        
        /*
        * 首先把所有的菜单查询出来
        * 之后会传递进来菜单的id，
        * 之后传递来的菜单id进行遍历，（对进来的menuList）
        *
        * pid进行判断是什么类型的，菜单还是什么
        *
        * */
        
        
        
        //接收参数
        List<SysMenu> list=new ArrayList<>();
        
        Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream()
                .filter(item -> item != null & item.getParentId() == pid)
                .forEach(dom -> {
                    SysMenu menu = new SysMenu();
                    BeanUtils.copyProperties(dom,menu);
                    
                    //查询该项的下级菜单,通过递归调用，自己一层一层调用自己
                    List<SysMenu> menuList1 = makeMenuTree(menuList, dom.getMenuId());
                    menu.setChildren(menuList1);
                    list.add(menu);
    
                });
        
        return list;
        
        
    }
    
}
