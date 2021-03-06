package com.qin.web.sys_books.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qin.web.sys_books.entity.BookVo;
import com.qin.web.sys_books.entity.ListParm;
import com.qin.web.sys_books.entity.SysBooks;
import com.qin.web.sys_books.mapper.SysBooksMapper;
import com.qin.web.sys_books.service.SysBooksService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 秦家乐
 * @date 2022/3/17 20:48
 */
@Service
public class SysBooksServiceImpl extends ServiceImpl<SysBooksMapper, SysBooks> implements SysBooksService {
    
    
    @Override
    public IPage<SysBooks> getList(ListParm parm) {
        //构造分页对象
        Page<SysBooks> page=new Page<>();
        page.setCurrent(parm.getCurrentPage());
        page.setSize(parm.getPageSize());
        

        return this.baseMapper.getList(page,parm);
    }
    
    
    //有点麻烦，具体的写在mappper里面吧
    @Override
    public int subBook(Long bookId) {
        return this.baseMapper.subBook(bookId);
    }
    
    @Override
    public int addBook(Long bookId) {
        return  this.baseMapper.addBook(bookId);
    }
    
    @Override
    public List<BookVo> getHotBook() {
        return this.baseMapper.getHotBook();
    }
}
