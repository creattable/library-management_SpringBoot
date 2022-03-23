package com.qin.web.sys_login.controller;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qin.jwt.JwtUtils;
import com.qin.utils.ResultUtils;
import com.qin.utils.ResultVo;
import com.qin.web.sys_login.entity.LoginParm;
import com.qin.web.sys_login.entity.LoginResult;
import com.qin.web.sys_reader.entity.SysReader;
import com.qin.web.sys_reader.service.SysReaderService;
import com.qin.web.sys_user.entity.SysUser;
import com.qin.web.sys_user.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 秦家乐
 * @date 2022/3/23 21:14
 */

@RestController
@RequestMapping("/api/system")
public class LoginController {
    
    
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysReaderService sysReaderService;
    @Autowired
    private JwtUtils jwtUtils;
    
    
    //用户登录
    @PostMapping("/login")
    public ResultVo login(@RequestBody LoginParm loginParm) {
        //先判断下账户，密码，登录类型是否有空的
        if (StringUtils.isEmpty(loginParm.getUsername()) || StringUtils.isEmpty(loginParm.getPassword()) || StringUtils.isEmpty(loginParm.getUserType())) {
            return ResultUtils.error("用户名/密码/类型不能为空");
        }
        
        //加密后的密码
        String s = DigestUtils.md5DigestAsHex(loginParm.getPassword().getBytes());
        
        //判断是读者还是管理
        //传来的参数中0是读者，1是管理
        if (loginParm.getUserType().equals("0")) {
            
            
            //根据读者的用户名和密码查询
            //密码的话，因为涉及到md5加密，所有加密后和表里的进行对比
            QueryWrapper<SysReader> query = new QueryWrapper<>();
            query.lambda().eq(SysReader::getUsername, loginParm.getUsername())
                    .eq(SysReader::getPassword, DigestUtils.md5DigestAsHex(loginParm.getPassword().getBytes()));
            SysReader one = sysReaderService.getOne(query);
            if (one == null) {
                return ResultUtils.error("用户名或密码错误");
            }
            
            //返回数据给前端
            LoginResult result = new LoginResult();
            //字段中显示的是ReaderId但在表中实际是user_id字段
            //这里用jwt工具类，传入的是用户名和类型
            result.setToken(jwtUtils.generateToken(one.getUsername(), loginParm.getUserType()));
            result.setUserId(one.getReaderId());
            return ResultUtils.success("登录成功", result);
            
            
        } else if (loginParm.getUserType().equals("1")) {
            //根据用户的用户名和密码查询
            QueryWrapper<SysUser> query = new QueryWrapper<>();
            query.lambda().eq(SysUser::getUsername, loginParm.getUsername())
                    .eq(SysUser::getPassword, DigestUtils.md5DigestAsHex(loginParm.getPassword().getBytes()));
            SysUser one = sysUserService.getOne(query);
            if (one == null) {
                return ResultUtils.error("用户名或密码错误");
            }
            //返回数据给前端
            LoginResult result = new LoginResult();
            //字段中显示的是ReaderId但在表中实际是user_id字段
            result.setToken(jwtUtils.generateToken(one.getUsername(), loginParm.getUserType()));
            result.setUserId(one.getUserId());
            return ResultUtils.success("登录成功", result);
        } else {
            return ResultUtils.error("用户类型不存在");
        }
        
    }
    
}
