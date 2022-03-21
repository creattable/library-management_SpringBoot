package com.qin.web.sys_notice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qin.web.sys_notice.entity.NoticeParm;
import com.qin.web.sys_notice.entity.SysNotice;

/**
 * @author 秦家乐
 * @date 2022/3/21 20:07
 */
public interface SysNoticeService extends IService<SysNotice> {
    
    IPage<SysNotice> getList(NoticeParm parm);
    
}
