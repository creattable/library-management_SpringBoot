package com.qin.web.sys_user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qin.web.sys_user.entity.PageParm;
import com.qin.web.sys_user.entity.SysUser;

/**
 * @author 秦家乐
 * @date 2022/3/13 21:52
 */
public interface SysUserService extends IService<SysUser> {
    
    IPage<SysUser> list(PageParm parm);
    
    void addUser(SysUser user);
    void editUser(SysUser user);

}
