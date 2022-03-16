package com.qin.web.sys_reader.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qin.web.sys_reader.entity.ReaderParm;
import com.qin.web.sys_reader.entity.SysReader;
import com.qin.web.sys_reader.mapper.SysReaderMapper;
import com.qin.web.sys_reader.service.SysReaderService;
import org.springframework.stereotype.Service;

/**
 * @author 秦家乐
 * @date 2022/3/16 20:16
 */

@Service
public class SysReaderServiceImpl extends ServiceImpl<SysReaderMapper, SysReader> implements SysReaderService {
    
    
    @Override
    public IPage<SysReader> getList(ReaderParm parm) {
        //构造查询条件
        QueryWrapper<SysReader> query=new QueryWrapper<>();
        if(!StringUtils.isEmpty(parm.getIdCard())){
            query.lambda().like(SysReader::getIdCard,parm.getIdCard());
        }
        if(!StringUtils.isEmpty(parm.getLearnNum())){
            query.lambda().like(SysReader::getLearnNum,parm.getLearnNum());
        }
        if(!StringUtils.isEmpty(parm.getPhone())){
            query.lambda().like(SysReader::getPhone,parm.getPhone());
        }
        if(!StringUtils.isEmpty(parm.getUsername())){
            query.lambda().like(SysReader::getUsername,parm.getUsername());
        }
        
        //构造分页对象
        IPage<SysReader> page=new Page<>();
        page.setCurrent(parm.getCurrentPage());
        page.setSize(parm.getPageSize());
        
        return  this.baseMapper.selectPage(page,query);
        
        
    }
}
