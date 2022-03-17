package com.qin.web.sys_category.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qin.utils.ResultUtils;
import com.qin.utils.ResultVo;
import com.qin.web.sys_category.entity.ListCateParm;
import com.qin.web.sys_category.entity.SysCategory;
import com.qin.web.sys_category.service.SysCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 秦家乐
 * @date 2022/3/17 12:26
 */
@RestController
@RequestMapping("/api/category")
public class SysCategoryController {
    
    @Autowired
    private SysCategoryService sysCategoryService;
    
    //新增
    @PostMapping
    public ResultVo add(@RequestBody SysCategory sysCategory){
        boolean save = sysCategoryService.save(sysCategory);
        if(save){
            return ResultUtils.success("新增成功！");
        }
        return ResultUtils.error("新增失败");
    }
    
    
    //编辑
    @PutMapping
    public ResultVo edit(@RequestBody SysCategory sysCategory){
        boolean save = sysCategoryService.updateById(sysCategory);
        if(save){
            return ResultUtils.success("编辑成功！");
        }
        return ResultUtils.error("编辑失败");
    }
    
    //删除
    @DeleteMapping("{categoryId}")
    public ResultVo delete(@PathVariable("categoryId") Long categoryId){
    
        boolean remove = sysCategoryService.removeById(categoryId);
        if(remove){
            return ResultUtils.success("删除成功！");
        }
        return ResultUtils.error("删除失败");
    }
    
    //查询
    @GetMapping("/list")
    public ResultVo getList(ListCateParm parm){
        IPage<SysCategory> list = sysCategoryService.getList(parm);
        return ResultUtils.success("查询成功",list);
    
    }
    
    
    
    
}
