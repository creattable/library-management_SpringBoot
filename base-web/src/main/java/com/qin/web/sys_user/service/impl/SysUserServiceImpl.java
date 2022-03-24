package com.qin.web.sys_user.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qin.web.sys_user.entity.PageParm;
import com.qin.web.sys_user.entity.SysUser;
import com.qin.web.sys_user.mapper.SysUserMapper;
import com.qin.web.sys_user.service.SysUserService;
import com.qin.web.sys_user_role.entity.UserRole;
import com.qin.web.sys_user_role.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 秦家乐
 * @date 2022/3/13 21:53
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    
    @Autowired()
    private UserRoleService userRoleService;
    
    @Override
    public IPage<SysUser> list(PageParm parm) {
        //构造分页对象
        IPage<SysUser> page = new Page<>();
        page.setSize(parm.getPageSize());
        page.setCurrent(page.getCurrent());
        
        //构造查询条件
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        if (!StringUtils.isEmpty(parm.getNickName())) {
            query.lambda().like(SysUser::getNickName, parm.getNickName());
        }
        if (!StringUtils.isEmpty(parm.getPhone())) {
            query.lambda().like(SysUser::getPhone, parm.getPhone());
        }
        
        //mybatis-plus自带的分页查询
        return this.baseMapper.selectPage(page, query);
    }
    
    @Override
    @Transactional
    public void addUser(SysUser user) {
        //新增用户
        int insert = this.baseMapper.insert(user);
        //分配角色
        if(insert > 0){
            UserRole userRole = new UserRole();
            userRole.setRoleId(user.getRoleId());
            userRole.setUserId(user.getUserId());
            userRoleService.save(userRole);
        }
    }
    
    @Override
    @Transactional
    public void editUser(SysUser user) {
        int i = this.baseMapper.updateById(user);
        if(i > 0){
            //先删除，再插入
            QueryWrapper<UserRole> query = new QueryWrapper<>();
            query.lambda().eq(UserRole::getUserId,user.getUserId());
            userRoleService.remove(query);
            UserRole userRole = new UserRole();
            userRole.setUserId(user.getUserId());
            userRole.setRoleId(user.getRoleId());
            userRoleService.save(userRole);
        }
    }
    
    @Override
    public SysUser loadByUsername(String username) {
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        query.lambda().eq(SysUser::getUsername,username);
        return this.baseMapper.selectOne(query);
    }
}
