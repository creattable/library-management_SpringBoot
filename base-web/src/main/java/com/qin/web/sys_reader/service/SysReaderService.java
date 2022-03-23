package com.qin.web.sys_reader.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qin.web.sys_reader.entity.ReaderParm;
import com.qin.web.sys_reader.entity.SysReader;

/**
 * @author 秦家乐
 * @date 2022/3/16 20:15
 */
public interface SysReaderService extends IService<SysReader> {
    
    IPage<SysReader> getList(ReaderParm parm);
    
    //新增读者
    void saveReader(SysReader sysReader);
    
    //编辑读者
    void editReader(SysReader sysReader);
    
}
