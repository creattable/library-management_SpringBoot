package com.qin.web.sys_reader.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qin.exception.BusinessException;
import com.qin.web.sys_reader.entity.ReaderParm;
import com.qin.web.sys_reader.entity.SysReader;
import com.qin.web.sys_reader.mapper.SysReaderMapper;
import com.qin.web.sys_reader.service.SysReaderService;
import com.qin.web.sys_reader_role.entity.ReaderRole;
import com.qin.web.sys_reader_role.service.ReaderRoleService;
import com.qin.web.sys_role.entity.SysRole;
import com.qin.web.sys_role.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Reader;

/**
 * @author 秦家乐
 * @date 2022/3/16 20:16
 */

@Service
public class SysReaderServiceImpl extends ServiceImpl<SysReaderMapper, SysReader> implements SysReaderService {
    
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private ReaderRoleService readerRoleService;
    
    
    @Override
    public IPage<SysReader> getList(ReaderParm parm) {
        //构造查询条件
        QueryWrapper<SysReader> query = new QueryWrapper<>();
        if (!StringUtils.isEmpty(parm.getIdCard())) {
            query.lambda().like(SysReader::getIdCard, parm.getIdCard());
        }
        if (!StringUtils.isEmpty(parm.getLearnNum())) {
            query.lambda().like(SysReader::getLearnNum, parm.getLearnNum());
        }
        if (!StringUtils.isEmpty(parm.getPhone())) {
            query.lambda().like(SysReader::getPhone, parm.getPhone());
        }
        if (!StringUtils.isEmpty(parm.getUsername())) {
            query.lambda().like(SysReader::getUsername, parm.getUsername());
        }
        
        //构造分页对象
        IPage<SysReader> page = new Page<>();
        page.setCurrent(parm.getCurrentPage());
        page.setSize(parm.getPageSize());
        
        return this.baseMapper.selectPage(page, query);
        
        
    }
    
    @Override
    @Transactional
    public void saveReader(SysReader sysReader) {
        //新增读者用户之前，需要先检查读者角色是否存在
        QueryWrapper<SysRole> query = new QueryWrapper<>();
        query.lambda().eq(SysRole::getRoleType, "2");
        SysRole one = sysRoleService.getOne(query);
        if (one == null) {
            throw new BusinessException(500, "请先新建读者角色，再建读者");
        }
        
        //新增读者
        this.baseMapper.insert(sysReader);
        //设置读者角色
        ReaderRole readerRole = new ReaderRole();
        readerRole.setReader_id(sysReader.getReaderId());
        readerRole.setRole_id(one.getRoleId());
        readerRoleService.save(readerRole);
        
        
    }
    
    @Override
    @Transactional
    public void editReader(SysReader sysReader) {
        //编辑读者用户之前，需要先检查读者角色是否存在
        QueryWrapper<SysRole> query = new QueryWrapper<>();
        query.lambda().eq(SysRole::getRoleType, "2");
        SysRole one = sysRoleService.getOne(query);
        if (one == null) {
            throw new BusinessException(500, "请先新建读者角色，再建读者");
        }
        
        //编辑读者
        this.baseMapper.updateById(sysReader);
        
        //设置读者，先删除，后插入
        QueryWrapper<ReaderRole> query1 = new QueryWrapper<>();
        query1.lambda().eq(ReaderRole::getReader_id, sysReader.getReaderId());
        readerRoleService.remove(query1);
        
        ReaderRole reader1 = new ReaderRole();
        reader1.setReader_id(sysReader.getReaderId());
        reader1.setRole_id(one.getRoleId());
        readerRoleService.save(reader1);
        
        
    }
    
    @Override
    public SysReader loadByUsername(String username) {
        QueryWrapper<SysReader> query = new QueryWrapper();
        query.lambda().eq(SysReader::getUsername,username);
        return this.baseMapper.selectOne(query);
    }
}
