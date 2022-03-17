package com.qin.web.sys_category.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qin.web.sys_category.entity.ListCateParm;
import com.qin.web.sys_category.entity.SysCategory;

/**
 * @author 秦家乐
 * @date 2022/3/17 12:24
 */
public interface SysCategoryService extends IService<SysCategory> {
    
    IPage<SysCategory> getList(ListCateParm parm);
    
}
