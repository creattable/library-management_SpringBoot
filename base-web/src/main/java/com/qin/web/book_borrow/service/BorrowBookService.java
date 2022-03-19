package com.qin.web.book_borrow.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qin.web.book_borrow.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 秦家乐
 * @date 2022/3/19 17:47
 */
public interface BorrowBookService extends IService<BorrowBook> {
    
    //借书
    void borrow(BorrowParm parm);
    
    //还书列表
    IPage<ReturnBook> getBorrowList(ListParm parm);
    
    //还书(正常)
    void returnBook(List<ReturnParm> list);
    
    
}
