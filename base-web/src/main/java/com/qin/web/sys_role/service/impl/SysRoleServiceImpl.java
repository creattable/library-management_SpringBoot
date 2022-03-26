package com.qin.web.sys_role.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qin.web.sys_menu.entity.MakeTree;
import com.qin.web.sys_menu.entity.SysMenu;
import com.qin.web.sys_menu.service.SysMenuService;
import com.qin.web.sys_role.entity.AssignParm;
import com.qin.web.sys_role.entity.AssignVo;
import com.qin.web.sys_role.entity.RoleParm;
import com.qin.web.sys_role.entity.SysRole;
import com.qin.web.sys_role.mapper.SysRoleMapper;
import com.qin.web.sys_role.service.SysRoleService;
import com.qin.web.sys_user.entity.SysUser;
import com.qin.web.sys_user.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author 秦家乐
 * @date 2022/3/14 20:23
 */

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysMenuService sysMenuService;
    
    
    @Override
    public IPage<SysRole> list(RoleParm parm) {
        //构造分页的对象
        IPage<SysRole> page = new Page<>();
        page.setSize(page.getSize());
        page.setCurrent(page.getCurrent());
        
        //查询条件
        QueryWrapper<SysRole> query = new QueryWrapper<>();
        //如果不为空才做查询
        if (!StringUtils.isEmpty(parm.getRoleName())) {
            query.lambda().like(SysRole::getRoleName, parm.getRoleName());
        }
        
        
        return this.baseMapper.selectPage(page, query);
    }
    
    
    /*
    *
    * 这块查询的逻辑
    * 查询出数据->判断是否是超级管理
    * 是的话查询所有的菜单然后排序即可
    * 不是的话，查询当前用户拥有的菜单
    *
    * 查询之后组装树，组装之后查询角色原来的菜单
    * 然后遍历后形成ids
    *
    * 再组装数据和设置菜单后把数据回显给前端
    *
    *
    * */
    
    @Override
    public AssignVo getAssignShow(AssignParm parm) {
        System.out.println("******************************************");
        System.out.println(parm);
        //查询当前用户的信息
        SysUser user = sysUserService.getById(parm.getUserId());
        //菜单数据
        List<SysMenu> list = null;
        if(user.getIsAdmin().equals("1")){ //如果是超级管理员，拥有所有的权限
            QueryWrapper<SysMenu> query = new QueryWrapper<>();
            query.lambda().orderByAsc(SysMenu::getOrderNum);
            list = sysMenuService.list(query);
        }else{
            list = sysMenuService.getMenuByUserId(user.getUserId());
        }
        //组装树
        List<SysMenu> menuList = MakeTree.makeMenuTree(list, 0L);
        //查询角色原来的菜单
        List<SysMenu> roleList = sysMenuService.getMenuByRoleId(parm.getRoleId());
        List<Long> ids = new ArrayList<>();
        Optional.ofNullable(roleList).orElse(new ArrayList<>()).stream().filter(item -> item != null).forEach(item ->{
            ids.add(item.getMenuId());
        });
        //组装数据
        AssignVo vo = new AssignVo();
        vo.setMenuList(menuList);
        vo.setCheckList(ids.toArray());
        return vo;
    }
}
