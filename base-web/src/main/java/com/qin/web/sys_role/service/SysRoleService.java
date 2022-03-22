package com.qin.web.sys_role.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qin.web.sys_role.entity.AssignParm;
import com.qin.web.sys_role.entity.AssignVo;
import com.qin.web.sys_role.entity.RoleParm;
import com.qin.web.sys_role.entity.SysRole;

/**
 * @author 秦家乐
 * @date 2022/3/14 20:22
 */
public interface SysRoleService extends IService<SysRole> {
    IPage<SysRole> list(RoleParm parm);
    //角色权限的回显
    AssignVo getAssignShow(AssignParm parm);
    
}
