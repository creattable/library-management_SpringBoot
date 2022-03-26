package com.qin.web.sys_user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qin.annotation.Auth;
import com.qin.jwt.JwtUtils;
import com.qin.utils.ResultUtils;
import com.qin.utils.ResultVo;
import com.qin.web.sys_reader.entity.SysReader;
import com.qin.web.sys_reader.service.SysReaderService;
import com.qin.web.sys_role.entity.SysRole;
import com.qin.web.sys_role.service.SysRoleService;
import com.qin.web.sys_user.entity.PageParm;
import com.qin.web.sys_user.entity.SysUser;
import com.qin.web.sys_user.entity.UpdatePasswordParm;
import com.qin.web.sys_user.service.SysUserService;
import com.qin.web.sys_user_role.entity.UserRole;
import com.qin.web.sys_user_role.service.UserRoleService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author 秦家乐
 * @date 2022/3/13 21:57
 */

@RestController
@RequestMapping("/api/user")
public class SysUserController {
    
    
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private SysReaderService sysReaderService;
    
    
    //新增用户
    @Auth
    @PostMapping
    public ResultVo addUser(@RequestBody SysUser user) {
        //判断账户是否被占用
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        query.lambda().eq(SysUser::getUsername, user.getUsername());
        SysUser one = sysUserService.getOne(query);
        if (one != null) {
            return ResultUtils.error("账户被占用过了!");
        }
        
        //密码加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        
        //设置是否为管理
        user.setIsAdmin("0");
        //设置创建时间
        user.setCreateTime(new Date());
        //入库
        sysUserService.addUser(user);
        
        return ResultUtils.success("编辑用户成功!");
    }
    
    
    //编辑用户
    @Auth
    @PutMapping
    public ResultVo editUser(@RequestBody SysUser user) {
        //判断账户是否被占用
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        query.lambda().eq(SysUser::getUsername, user.getUsername());
        SysUser one = sysUserService.getOne(query);
        if (one != null && one.getUserId() != user.getUserId()) {
            return ResultUtils.error("账户被占用过了!");
        }
        
        //密码加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        
        //更新时间
        user.setUpdateTime(new Date());
        
        //入库
        sysUserService.editUser(user);
        return ResultUtils.success("编辑用户成功!");
        
        
    }
    
    
    //删除
    @Auth
    @DeleteMapping("/{userId}")
    public ResultVo delete(@PathVariable("userId") Long userId) {
        
        boolean remove = sysUserService.removeById(userId);
        if (remove) {
            return ResultUtils.success("删除用户成功!");
        }
        return ResultUtils.error("删除用户失败！");
    }
    
    
    //列表查询,因为是分页查询，所有用自定义类来处理
    @Auth
    @GetMapping("/list")
    public ResultVo getList(PageParm parm) {
        IPage<SysUser> list = sysUserService.list(parm);
        
        //密码处理一下
        list.getRecords().stream().forEach(item -> {
            item.setPassword("");
        });
        
        return ResultUtils.success("查询成功", list);
        
    }
    
    
    //查询角色列表
    @Auth
    @GetMapping("/getRoleList")
    public ResultVo getRoleList() {
        List<SysRole> list = sysRoleService.list();
        return ResultUtils.success("查询成功", list);
    }
    
    //根据用户id查询角色
    @Auth
    @GetMapping("/getRoleId")
    public ResultVo getRoleId(Long userId) {
        QueryWrapper<UserRole> query = new QueryWrapper<>();
        query.lambda().eq(UserRole::getUserId, userId);
        UserRole one = userRoleService.getOne(query);
        return ResultUtils.success("查询成功", one);
    }
    
    
    //修改密码
    @Auth
    @PostMapping("/updatePassword")
    public ResultVo updatePassword(@RequestBody UpdatePasswordParm parm, HttpServletRequest request){
        //获取token
        String token=request.getHeader("token");
    
        Claims claims = jwtUtils.getClaimsFromToken(token);
        Object userType = claims.get("userType");
        //获取原密码
        String old=DigestUtils.md5DigestAsHex(parm.getOldPassword().getBytes());
        
        //0是读者，1是管理
        if(userType.equals("0")){
            SysReader reader = sysReaderService.getById(parm.getUserId());
            //密码对比
            if(!old.equals(reader.getPassword())){
                return ResultUtils.error("原密码错误");
            }
            SysReader sysReader=new SysReader();
            sysReader.setPassword(DigestUtils.md5DigestAsHex(parm.getPassword().getBytes()));
            sysReader.setReaderId(parm.getUserId());
            boolean b = sysReaderService.updateById(sysReader);
            if(b){
                return ResultUtils.success("修改成功");
            }
            return ResultUtils.error("修改失败");
            
        }else if(userType.equals("1")){
            SysUser user = sysUserService.getById(parm.getUserId());
            if(user.getPassword().equals("old")){
                return ResultUtils.error("原密码错误");
            }
            SysUser sysUser=new SysUser();
            sysUser.setPassword(DigestUtils.md5DigestAsHex(parm.getPassword().getBytes()));
            sysUser.setUserId(parm.getUserId());
            boolean b = sysUserService.updateById(sysUser);
            if(b){
                return ResultUtils.success("修改成功");
            }
            return ResultUtils.error("修改失败");
    
        }else {
            return ResultUtils.error("类型错误");
        }
    
    
    }
    
    
    
}
