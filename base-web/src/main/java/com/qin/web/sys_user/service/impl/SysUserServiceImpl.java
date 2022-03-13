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
import org.springframework.stereotype.Service;

/**
 * @author 秦家乐
 * @date 2022/3/13 21:53
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    
    @Override
    public IPage<SysUser> list(PageParm parm) {
        //构造分页对象
        IPage<SysUser> page = new Page<>();
        page.setSize(parm.getPageSize());
        page.setCurrent(page.getCurrent());
        
        //构造查询条件
        QueryWrapper<SysUser> query =new QueryWrapper<>();
        if(!StringUtils.isEmpty(parm.getNickName())){
            query.lambda().like(SysUser::getNickName,parm.getNickName());
        }
        if(!StringUtils.isEmpty(parm.getPhone())){
            query.lambda().like(SysUser::getPhone,parm.getPhone());
        }
        
        //mybatis-plus自带的分页查询
        return this.baseMapper.selectPage(page,query);
    }
}
