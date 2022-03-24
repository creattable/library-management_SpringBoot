package com.qin.web.sys_login.controller;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qin.jwt.JwtUtils;
import com.qin.utils.ResultUtils;
import com.qin.utils.ResultVo;
import com.qin.web.sys_login.entity.LoginParm;
import com.qin.web.sys_login.entity.LoginResult;
import com.qin.web.sys_login.entity.UserInfo;
import com.qin.web.sys_menu.entity.SysMenu;
import com.qin.web.sys_menu.service.SysMenuService;
import com.qin.web.sys_reader.entity.SysReader;
import com.qin.web.sys_reader.service.SysReaderService;
import com.qin.web.sys_user.entity.SysUser;
import com.qin.web.sys_user.service.SysUserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private SysMenuService sysMenuService;
    
    
    //用户登录
    @PostMapping("/login")
    public ResultVo login(@RequestBody LoginParm loginParm) {
        //先判断下账户，密码，登录类型是否有空的
        if (StringUtils.isEmpty(loginParm.getUsername()) || StringUtils.isEmpty(loginParm.getPassword()) || StringUtils.isEmpty(loginParm.getUserType())) {
            return ResultUtils.error("用户名/密码/类型不能为空");
        }
        
        //加密后的密码
        String s = DigestUtils.md5DigestAsHex(loginParm.getPassword().getBytes());
        System.out.println(s+"--------------------------------");
        
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
    
    //获取用户权限字段
    @GetMapping("/getInfo")
    public ResultVo getInfo(Long userid, HttpServletRequest request){
        
        //从请求的头部获取token
        String token=request.getHeader("token");
        //获取从token解析用户的类型
        Claims claims = jwtUtils.getClaimsFromToken(token);
        Object userType = claims.get("userType");
        //定义用户信息类(还是接收用的)
        UserInfo userInfo=new UserInfo();
        
        //0表示读者，1是管理
        if (userType.equals("0")){
            //根据id查询读者信息
            SysReader reader = sysReaderService.getById(userid);
            userInfo.setIntroduction(reader.getLearnNum());
            userInfo.setName(reader.getLearnNum());
            userInfo.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
            //权限字段的查询
            List<SysMenu> menuList = sysMenuService.getReaderMenuByUserId(userid);
            //只需要权限字段，因此需要过滤不需要的
            List<String> collect = menuList.stream().filter(item -> item != null && item.getCode() != null).map(item -> item.getCode()).collect(Collectors.toList());
            //转成数组然后返回
            String[] strings = collect.toArray(new String[collect.size()]);
            userInfo.setRoles(strings);
            return ResultUtils.success("查询成功",userInfo);
    
    
        }else if (userType.equals("1")){
            SysUser user = sysUserService.getById(userid);
            userInfo.setIntroduction(user.getNickName());
            userInfo.setName(user.getNickName());
            userInfo.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
            //权限字段的查询
            List<SysMenu> menuList = sysMenuService.getMenuByUserId(userid);
            //只需要权限字段，因此需要过滤不需要的
            List<String> collect = menuList.stream().filter(item -> item != null && item.getCode() != null).map(item -> item.getCode()).collect(Collectors.toList());
            //转成数组然后返回
            String[] strings = collect.toArray(new String[collect.size()]);
            userInfo.setRoles(strings);
            return ResultUtils.success("查询成功",userInfo);
        
        }else {
            return ResultUtils.error("用户类型不存在",userInfo);
        }
    
    
    }
    
}