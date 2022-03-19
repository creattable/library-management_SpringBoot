package com.qin.web.book_borrow.controller;

import com.qin.utils.ResultUtils;
import com.qin.utils.ResultVo;
import com.qin.web.book_borrow.entity.BorrowParm;
import com.qin.web.book_borrow.service.BorrowBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 秦家乐
 * @date 2022/3/19 17:49
 */
@RestController
@RequestMapping("/api/borrow")
public class BorrowBookController {
    
    @Autowired
    private BorrowBookService borrowBookService;
    
    @PostMapping
    public ResultVo borrow(@RequestBody BorrowParm parm){
        borrowBookService.borrow(parm);
        return ResultUtils.success("借书成功");
    }
    
    
}
