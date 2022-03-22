package com.qin.web.sys_role.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qin.utils.ResultUtils;
import com.qin.utils.ResultVo;
import com.qin.web.sys_role.entity.AssignParm;
import com.qin.web.sys_role.entity.AssignVo;
import com.qin.web.sys_role.entity.RoleParm;
import com.qin.web.sys_role.entity.SysRole;
import com.qin.web.sys_role.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author 秦家乐
 * @date 2022/3/14 20:25
 */

@RestController
@RequestMapping("/api/role")
public class SysRoleController {
    
    @Autowired
    private SysRoleService SysRoleService;
    
    
    //新增
    @PostMapping
    public ResultVo addRole(@RequestBody SysRole role){
        role.setCreateTime(new Date());
        boolean save = SysRoleService.save(role);
        if(save){
            return ResultUtils.success("新增角色成功！");
        }
        return ResultUtils.error("新增角色失败！");
    }
    
    
    
    //编辑
    @PutMapping
    public ResultVo editRole(@RequestBody SysRole role){
        role.setUpdateTime(new Date());
        boolean save = SysRoleService.updateById(role);
        if(save){
            return ResultUtils.success("编辑角色成功！");
        }
        return ResultUtils.error("编辑角色失败！");
    }
    
    //删除
    @DeleteMapping("/{roleId}")
    public ResultVo deleteRole(@PathVariable("roleId") long roleId){
        boolean remove = SysRoleService.removeById(roleId);
        if(remove){
            return ResultUtils.success("删除角色成功！");
        }
        return ResultUtils.error("删除角色失败！");
    }
    
    
    //列表
    @GetMapping("/list")
    public ResultVo getList(RoleParm parm){

        IPage<SysRole> list = SysRoleService.list(parm);
    
        return ResultUtils.success("查询角色成功！",list);
    
    }
    
    
    //查询角色权限树的回显
    @GetMapping("/getAssingShow")
    public ResultVo getAssingShow(AssignParm parm) {
        AssignVo show = SysRoleService.getAssignShow(parm);
        return ResultUtils.success("查询成功", show);
    }
    

}
