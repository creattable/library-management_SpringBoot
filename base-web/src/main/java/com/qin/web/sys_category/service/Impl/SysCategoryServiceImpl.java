package com.qin.web.sys_category.service.Impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qin.web.sys_category.entity.ListCateParm;
import com.qin.web.sys_category.entity.SysCategory;
import com.qin.web.sys_category.mapper.SysCategoryMapper;
import com.qin.web.sys_category.service.SysCategoryService;
import org.springframework.stereotype.Service;

/**
 * @author 秦家乐
 * @date 2022/3/17 12:25
 */
@Service
public class SysCategoryServiceImpl extends ServiceImpl<SysCategoryMapper, SysCategory> implements SysCategoryService {
    
    
    @Override
    public IPage<SysCategory> getList(ListCateParm parm) {
        //构造分页对象
        //查询的时候，比如要出啊日奴当前页和每页的限制，并且用page来进行new
        IPage<SysCategory> page = new Page<>();
        page.setSize(parm.getPageSize());
        page.setCurrent(parm.getCurrentPage());
        
        //构造查询条件
        QueryWrapper<SysCategory> query = new QueryWrapper<>();
        if (!StringUtils.isEmpty(parm.getCategoryName())) {
            //like第一个传入的是字段，第二个传入的是值
            query.lambda().like(SysCategory::getCategoryName, parm.getCategoryName());
        }
        
        //查询的时候，第一个传入的是分页对象，第二个传入的是查询条件
        return this.baseMapper.selectPage(page,query);
    }
}
