package com.qin.web.sys_category.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qin.annotation.Auth;
import com.qin.utils.ResultUtils;
import com.qin.utils.ResultVo;
import com.qin.web.sys_category.entity.CategoryEcharts;
import com.qin.web.sys_category.entity.ListCateParm;
import com.qin.web.sys_category.entity.SysCategory;
import com.qin.web.sys_category.service.SysCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @Auth
    @PostMapping
    public ResultVo add(@RequestBody SysCategory sysCategory) {
        boolean save = sysCategoryService.save(sysCategory);
        if (save) {
            return ResultUtils.success("新增成功！");
        }
        return ResultUtils.error("新增失败");
    }
    
    
    //编辑
    @Auth
    @PutMapping
    public ResultVo edit(@RequestBody SysCategory sysCategory) {
        boolean save = sysCategoryService.updateById(sysCategory);
        if (save) {
            return ResultUtils.success("编辑成功！");
        }
        return ResultUtils.error("编辑失败");
    }
    
    //删除
    @Auth
    @DeleteMapping("{categoryId}")
    public ResultVo delete(@PathVariable("categoryId") Long categoryId) {
        
        boolean remove = sysCategoryService.removeById(categoryId);
        if (remove) {
            return ResultUtils.success("删除成功！");
        }
        return ResultUtils.error("删除失败");
    }
    
    //查询
    @Auth
    @GetMapping("/list")
    public ResultVo getList(ListCateParm parm) {
        IPage<SysCategory> list = sysCategoryService.getList(parm);
        return ResultUtils.success("查询成功", list);
        
    }
    
    
    //图书列表的分类，让其他(图书列表)可以完成图书分类中的查询
    @Auth
    @GetMapping("/cateList")
    public ResultVo getCateList() {
        List<SysCategory> list = sysCategoryService.list();
        return ResultUtils.success("查询成功", list);
    }
    
    
    //图书分类统计
    @Auth
    @GetMapping("/categoryCount")
    public ResultVo categoryCount() {
        CategoryEcharts categoryVo = sysCategoryService.getCategoryVo();
        return ResultUtils.success("查询成功", categoryVo);
    }
    
    
}
