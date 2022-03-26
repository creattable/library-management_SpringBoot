package com.qin.web.sys_category.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qin.web.sys_category.entity.CategoryVo;
import com.qin.web.sys_category.entity.SysCategory;

import java.util.List;

/**
 * @author 秦家乐
 * @date 2022/3/17 12:23
 */
public interface SysCategoryMapper extends BaseMapper<SysCategory> {
    
    //分类统计
    List<CategoryVo> getCategoryVo();
    
}
