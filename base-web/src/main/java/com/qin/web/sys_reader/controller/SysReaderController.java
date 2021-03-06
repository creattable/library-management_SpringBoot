package com.qin.web.sys_reader.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qin.annotation.Auth;
import com.qin.utils.ResultUtils;
import com.qin.utils.ResultVo;
import com.qin.web.sys_reader.entity.ReaderParm;
import com.qin.web.sys_reader.entity.SysReader;
import com.qin.web.sys_reader.service.SysReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author 秦家乐
 * @date 2022/3/16 20:17
 */
@RestController
@RequestMapping("/api/reader")
public class SysReaderController {
    
    @Autowired
    private SysReaderService sysReaderService;
    
    
    //读者注册
    @PostMapping("/register")
    public ResultVo register(@RequestBody SysReader reader) {
        //查询学号是否已经被占用
        QueryWrapper<SysReader> query = new QueryWrapper<>();
        query.lambda().eq(SysReader::getUsername, reader.getUsername());
        SysReader one = sysReaderService.getOne(query);
        if (one != null) {
            return ResultUtils.error("学号已经被占用");
        }
        
        reader.setPassword(DigestUtils.md5DigestAsHex(reader.getPassword().getBytes()));
        
        //如果是管理自己新增的，则默认为已经审核了,同时默认状态是启用，
        //0是未审核，需要管理审核
        reader.setCheckStatus("0");
        reader.setUserStatus("1");
        sysReaderService.saveReader(reader);
        return ResultUtils.success("注册成功！");
        
    }
    
    //新增
    @Auth
    @PostMapping
    public ResultVo addReader(@RequestBody SysReader reader) {
        //查询学号是否已经被占用
        QueryWrapper<SysReader> query = new QueryWrapper<>();
        query.lambda().eq(SysReader::getUsername, reader.getUsername());
        SysReader one = sysReaderService.getOne(query);
        if (one != null) {
            return ResultUtils.error("学号已经被占用");
        }
        
        reader.setPassword(DigestUtils.md5DigestAsHex(reader.getPassword().getBytes()));
        
        //如果是管理自己新增的，则默认为已经审核了,同时默认状态是启用
        reader.setCheckStatus("1");
        reader.setUserStatus("1");
        sysReaderService.saveReader(reader);
        return ResultUtils.success("新增成功！");
        
    }
    
    //编辑
    @Auth
    @PutMapping
    public ResultVo editReader(@RequestBody SysReader reader) {
        sysReaderService.editReader(reader);
        return ResultUtils.success("编辑成功！");
    }
    
    //读者审核
    @Auth
    @PutMapping("/applyReader")
    public ResultVo applyReader(@RequestBody SysReader reader) {
        reader.setCheckStatus("1");
        sysReaderService.updateById(reader);
        return ResultUtils.success("审核成功！");
    }
    
    //删除
    @Auth
    @DeleteMapping("/{readerId}")
    public ResultVo deleteReader(@PathVariable("readerId") Long readerId) {
        boolean remove = sysReaderService.removeById(readerId);
        if (remove) {
            return ResultUtils.success("删除成功！");
        }
        return ResultUtils.error("删除失败！");
    }
    
    //列表查询
    @Auth
    @GetMapping("/list")
    public ResultVo getList(ReaderParm parm) {
        IPage<SysReader> list = sysReaderService.getList(parm);
        return ResultUtils.success("查询成功！", list);
    }
    
    //根据学号查询信息
    @Auth
    @GetMapping("/getByUserName")
    public ResultVo getByUserName(SysReader reader) {
        QueryWrapper<SysReader> query = new QueryWrapper();
        query.lambda().eq(SysReader::getUsername, reader.getUsername());
        SysReader one = sysReaderService.getOne(query);
        return ResultUtils.success("查询成功", one);
        
    }
    
    //读者总数
    @Auth
    @GetMapping("/getReaderCount")
    public ResultVo getReaderCount() {
        int count = sysReaderService.count();
        return ResultUtils.success("查询成功", count);
    }
    
    //读者待审核总数
    @Auth
    @GetMapping("/getApplyReaderCount")
    public ResultVo getApplyReaderCount() {
        QueryWrapper<SysReader> query = new QueryWrapper<>();
        query.lambda().eq(SysReader::getCheckStatus, "0");
        int count = sysReaderService.count(query);
        return ResultUtils.success("查询成功", count);
    }
    
    //重置密码
    @PostMapping("/resetPassword")
    public ResultVo resetPassword(@RequestBody SysReader sysReader) {
        
        String password = "666666";
        sysReader.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        boolean b = sysReaderService.updateById(sysReader);
        if (b) {
            return ResultUtils.success("重置成功");
        }
        return ResultUtils.error("重置密码失败");

    }
    
    
}
