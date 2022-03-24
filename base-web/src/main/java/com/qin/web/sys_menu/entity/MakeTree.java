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
    
    
    /**
     * 生成路由数据格式
     */
    
    //组装出来的数据格式类型
    /*{
        path: '/permission',
                component: Layout,
            redirect: '/permission/index', //重定向地址，在面包屑中点击会重定向去的地址
            hidden: true, // 不在侧边栏显示
            alwaysShow: true, //一直显示根路由
            meta: { roles: ['admin','editor'] }, //你可以在根路由设置权限，这样它下面所有的子路由都继承了这个权限
        children: [{
        path: 'index',
                component: ()=>import('permission/index'),
                name: 'permission',
                meta: {
            title: 'permission',
                    icon: 'lock', //图标
                    roles: ['admin','editor'], //或者你可以给每一个子路由设置自己的权限
            noCache: true // 不会被 <keep-alive> 缓存
        }
    }]
    }*/
    public static List<RouterVO> makeRouter(List<SysMenu> menuList, Long pid){
        //接受生产的路由数据
        List<RouterVO> list = new ArrayList<>();
        
        //组装数据
        //优先判断是否为空，如果为空直接返回一个空的list(new ArrayList<>())
        //如果不为空则stream过滤一波,
        //找到之后循环遍历每一项，组装成上面的案例类型
        Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream()
                .filter(item ->item != null && item.getParentId() == pid)
                .forEach(item ->{
                    RouterVO router = new RouterVO();
                    router.setName(item.getName());
                    router.setPath(item.getPath());
                    //判断是否是一级菜单
                    if(item.getParentId() == 0L){
                        router.setComponent("Layout");
                        router.setAlwaysShow(true);
                    }else{
                        router.setComponent(item.getUrl());
                        router.setAlwaysShow(false);
                    }
                    //设置meta
                    router.setMeta(router.new Meta(
                            item.getTitle(),
                            item.getIcon(),
                            item.getCode().split(",")
                    ));
                    //设置children
                    List<RouterVO> children = makeRouter(menuList, item.getMenuId());
                    router.setChildren(children);
                    if(router.getChildren().size() > 0){
                        router.setAlwaysShow(true);
                    }
                    list.add(router);
                });
        return list;
    }
    
    
}
