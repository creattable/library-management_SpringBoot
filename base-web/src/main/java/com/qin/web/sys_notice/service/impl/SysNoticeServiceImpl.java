package com.qin.web.sys_notice.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qin.web.sys_notice.entity.NoticeParm;
import com.qin.web.sys_notice.entity.SysNotice;
import com.qin.web.sys_notice.mapper.SysNoticeMapper;
import com.qin.web.sys_notice.service.SysNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 秦家乐
 * @date 2022/3/21 20:08
 */
@Service
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeMapper, SysNotice> implements SysNoticeService {
    
    
    @Override
    public IPage<SysNotice> getList(NoticeParm parm) {
        
        //构造分页对象
        Page<SysNotice> page = new Page<>();
        page.setCurrent(parm.getCurrentPage());
        page.setSize(parm.getPageSize());
        //构造查询条件
        QueryWrapper<SysNotice> query = new QueryWrapper<>();
        if (!StringUtils.isEmpty(parm.getNoticeTitle())) {
            query.lambda().like(SysNotice::getNoticeTitle, parm.getNoticeTitle());
        }
        query.lambda().orderByDesc(SysNotice::getCreateTime);
        
        
        return this.baseMapper.selectPage(page, query);
    }
}
