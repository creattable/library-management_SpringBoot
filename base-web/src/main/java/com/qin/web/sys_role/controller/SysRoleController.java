package com.qin.web.sys_role.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qin.utils.ResultUtils;
import com.qin.utils.ResultVo;
import com.qin.web.sys_role.entity.*;
import com.qin.web.sys_role.service.SysRoleService;
import com.qin.web.sys_role_menu.service.RoleMenuService;
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
    @Autowired
    private RoleMenuService roleMenuService;
    
    
    //新增
    @PostMapping
    public ResultVo addRole(@RequestBody SysRole role) {
        
        //加个判断，有时候新增的是系统用户
        if (role.getRoleType().equals("2")) {
            //在角色中，读者只能有一个，防止在角色管理中创建多个角色
            QueryWrapper<SysRole> query = new QueryWrapper<>();
            query.lambda().eq(SysRole::getRoleType, "2");
            SysRole one = SysRoleService.getOne(query);
            if (one != null) {
                return ResultUtils.error("读者角色只能创建一个");
            }
        }
        
        
        role.setCreateTime(new Date());
        boolean save = SysRoleService.save(role);
        if (save) {
            return ResultUtils.success("新增角色成功！");
        }
        return ResultUtils.error("新增角色失败！");
    }
    
    
    //编辑
    @PutMapping
    public ResultVo editRole(@RequestBody SysRole role) {
        
        //在角色中，读者只能有一个，防止在角色管理中创建多个角色
        if (role.getRoleType().equals("2")) {
            QueryWrapper<SysRole> query = new QueryWrapper<>();
            query.lambda().eq(SysRole::getRoleType, "2");
            SysRole one = SysRoleService.getOne(query);
            //读者结果存在，或者传来的id和查出来的是一样(这说明要修改的是同一个对象)才向下走
            if (one != null && role.getRoleId() != one.getRoleId()) {
                return ResultUtils.error("读者角色只能创建一个");
            }
        }
        
        role.setUpdateTime(new Date());
        boolean save = SysRoleService.updateById(role);
        if (save) {
            return ResultUtils.success("编辑角色成功！");
        }
        return ResultUtils.error("编辑角色失败！");
    }
    
    //删除
    @DeleteMapping("/{roleId}")
    public ResultVo deleteRole(@PathVariable("roleId") long roleId) {
        boolean remove = SysRoleService.removeById(roleId);
        if (remove) {
            return ResultUtils.success("删除角色成功！");
        }
        return ResultUtils.error("删除角色失败！");
    }
    
    
    //列表
    @GetMapping("/list")
    public ResultVo getList(RoleParm parm) {
        
        IPage<SysRole> list = SysRoleService.list(parm);
        
        return ResultUtils.success("查询角色成功！", list);
        
    }
    
    
    //查询角色权限树的回显
    @GetMapping("/getAssingShow")
    public ResultVo getAssingShow(AssignParm parm) {
        AssignVo show = SysRoleService.getAssignShow(parm);
        return ResultUtils.success("查询成功", show);
    }
    
    
    //角色分配权限保存
    @PostMapping("/assignSave")
    public ResultVo assignSave(@RequestBody SaveAssign parm) {
        roleMenuService.assignSave(parm.getRoleId(), parm.getList());
        return ResultUtils.success("分配成功!");
    }
    
    
}
