package com.qin.web.book_borrow.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qin.utils.ResultUtils;
import com.qin.utils.ResultVo;
import com.qin.web.book_borrow.entity.*;
import com.qin.web.book_borrow.service.BorrowBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 秦家乐
 * @date 2022/3/19 17:49
 */
@RestController
@RequestMapping("/api/borrow")
public class BorrowBookController {
    
    @Autowired
    private BorrowBookService borrowBookService;
    
    
    //借书
    @PostMapping
    public ResultVo borrow(@RequestBody BorrowParm parm){
        borrowBookService.borrow(parm);
        return ResultUtils.success("借书成功");
    }
    
    
    //还书列表
    @GetMapping("/getBorrowList")
    public ResultVo getBorrowList(ListParm parm){
        IPage<ReturnBook> borrowList = borrowBookService.getBorrowList(parm);
        return ResultUtils.success("查询成功",borrowList);
    }
    
    //还书(可批量)
    @PostMapping("/returnBooks")
    public ResultVo returnBooks(@RequestBody List<ReturnParm> parm){
        borrowBookService.returnBook(parm);
        return ResultUtils.success("还书成功");
    }
    
    //异常还书
    @PostMapping("/exceptionBooks")
    public ResultVo exceptionBooks(@RequestBody ExceptionParm parm){
        borrowBookService.exceptionBook(parm);
        return ResultUtils.success("还书成功");
    }
    
    //借阅查看
    @GetMapping("/getLookBorrowList")
    public ResultVo getLookBorrowList(LookParm parm){
        IPage<LookBorrow> lookBorrowList = borrowBookService.getLookBorrowList(parm);
        return ResultUtils.success("查询成功",lookBorrowList);
    }
    
    
}
