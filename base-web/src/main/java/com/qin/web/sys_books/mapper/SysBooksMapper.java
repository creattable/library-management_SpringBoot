package com.qin.web.sys_books.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qin.web.sys_books.entity.ListParm;
import com.qin.web.sys_books.entity.SysBooks;
import org.apache.ibatis.annotations.Param;

/**
 * @author 秦家乐
 * @date 2022/3/17 20:46
 */
public interface SysBooksMapper extends BaseMapper<SysBooks> {
    
    IPage<SysBooks> getList(Page<SysBooks> page,@Param("parm") ListParm parm);
    
    //有点麻烦，具体的写在mappper里面吧
    int subBook(@Param("bookId") Long bookId);
    int addBook(@Param("bookId") Long bookId);
    
}
