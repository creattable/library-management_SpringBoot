package com.qin.web.sys_books.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qin.web.sys_books.entity.ListParm;
import com.qin.web.sys_books.entity.SysBooks;

/**
 * @author 秦家乐
 * @date 2022/3/17 20:47
 */
public interface SysBooksService extends IService<SysBooks> {
    IPage<SysBooks> getList(ListParm parm);
    
}
