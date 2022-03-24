package com.qin.web.sys_reader.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
    @PutMapping
    public ResultVo editReader(@RequestBody SysReader reader) {
        sysReaderService.editReader(reader);
        return ResultUtils.success("编辑成功！");
    }
    
    //读者审核
    @PutMapping("/applyReader")
    public ResultVo applyReader(@RequestBody SysReader reader) {
        reader.setCheckStatus("1");
        sysReaderService.updateById(reader);
        return ResultUtils.success("审核成功！");
    }
    
    //删除
    @DeleteMapping("/{readerId}")
    public ResultVo deleteReader(@PathVariable("readerId") Long readerId) {
        boolean remove = sysReaderService.removeById(readerId);
        if (remove) {
            return ResultUtils.success("删除成功！");
        }
        return ResultUtils.error("删除失败！");
    }
    
    //列表查询
    @GetMapping("/list")
    public ResultVo getList(ReaderParm parm) {
        IPage<SysReader> list = sysReaderService.getList(parm);
        return ResultUtils.success("查询成功！", list);
    }
    
    //根据学号查询信息
    @GetMapping("/getByUserName")
    public ResultVo getByUserName(SysReader reader) {
        QueryWrapper<SysReader> query = new QueryWrapper();
        query.lambda().eq(SysReader::getUsername, reader.getUsername());
        SysReader one = sysReaderService.getOne(query);
        return ResultUtils.success("查询成功", one);
        
    }
    
    
}
