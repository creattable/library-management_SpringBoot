package com.qin.web.sys_category.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qin.web.sys_category.entity.CategoryEcharts;
import com.qin.web.sys_category.entity.CategoryVo;
import com.qin.web.sys_category.entity.ListCateParm;
import com.qin.web.sys_category.entity.SysCategory;

import java.util.List;

/**
 * @author 秦家乐
 * @date 2022/3/17 12:24
 */
public interface SysCategoryService extends IService<SysCategory> {
    
    IPage<SysCategory> getList(ListCateParm parm);
    
    //分类统计
    CategoryEcharts getCategoryVo();
    
}
