package com.qin.web.book_borrow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qin.web.book_borrow.entity.BorrowBook;
import com.qin.web.book_borrow.entity.ListParm;
import com.qin.web.book_borrow.entity.ReturnBook;
import org.apache.ibatis.annotations.Param;

/**
 * @author 秦家乐
 * @date 2022/3/19 17:45
 */
public interface BorrowBookMapper extends BaseMapper<BorrowBook> {
    IPage<ReturnBook> getBorrowList(Page<ReturnBook> page, @Param("parm")ListParm parm);
}
