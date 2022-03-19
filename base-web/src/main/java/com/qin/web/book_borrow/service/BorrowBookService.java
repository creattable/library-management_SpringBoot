package com.qin.web.book_borrow.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qin.web.book_borrow.entity.BorrowBook;
import com.qin.web.book_borrow.entity.BorrowParm;

/**
 * @author 秦家乐
 * @date 2022/3/19 17:47
 */
public interface BorrowBookService extends IService<BorrowBook> {
    
    //借书
    void borrow(BorrowParm parm);
    
}
