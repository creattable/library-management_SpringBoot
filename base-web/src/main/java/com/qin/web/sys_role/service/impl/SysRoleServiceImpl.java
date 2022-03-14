package com.qin.web.sys_role.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qin.web.sys_role.entity.RoleParm;
import com.qin.web.sys_role.entity.SysRole;
import com.qin.web.sys_role.mapper.SysRoleMapper;
import com.qin.web.sys_role.service.SysRoleService;
import org.springframework.stereotype.Service;

/**
 * @author 秦家乐
 * @date 2022/3/14 20:23
 */

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    
    
    @Override
    public IPage<SysRole> list(RoleParm parm) {
        //构造分页的对象
        IPage<SysRole> page= new Page<>();
        page.setSize(page.getSize());
        page.setCurrent(page.getCurrent());
        
        //查询条件
        QueryWrapper<SysRole> query = new QueryWrapper<>();
        //如果不为空才做查询
        if(!StringUtils.isEmpty(parm.getRoleName())){
            query.lambda().like(SysRole::getRoleName,parm.getRoleName());
        }
        
        
        return this.baseMapper.selectPage(page,query);
    }
}
