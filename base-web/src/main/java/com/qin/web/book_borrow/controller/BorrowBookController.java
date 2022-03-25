package com.qin.web.book_borrow.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qin.jwt.JwtUtils;
import com.qin.utils.ResultUtils;
import com.qin.utils.ResultVo;
import com.qin.web.book_borrow.entity.*;
import com.qin.web.book_borrow.service.BorrowBookService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    
    @Autowired
    private JwtUtils jwtUtils;
    
    //借阅审核(非管理借书需要审核)
    @PostMapping("/applyBook")
    public ResultVo applyBook(@RequestBody BorrowBook borrowBook) {
        borrowBook.setBorrowStatus("1");
        borrowBook.setApplyStatus("1");
        boolean b = borrowBookService.updateById(borrowBook);
        if (b) {
            return ResultUtils.success("审核成功");
        }
        return ResultUtils.error("审核失败");
        
        
    }
    
    
    //借书
    @PostMapping
    public ResultVo borrow(@RequestBody BorrowParm parm, HttpServletRequest request) {
        //先获取下token
        String token = request.getHeader("token");
        Claims claims = jwtUtils.getClaimsFromToken(token);
        String userType = (String) claims.get("userType");
        
        borrowBookService.borrow(parm, userType);
        return ResultUtils.success("借书成功");
    }
    
    
    //还书列表
    @GetMapping("/getBorrowList")
    public ResultVo getBorrowList(ListParm parm) {
        IPage<ReturnBook> borrowList = borrowBookService.getBorrowList(parm);
        return ResultUtils.success("查询成功", borrowList);
    }
    
    //还书(可批量)
    @PostMapping("/returnBooks")
    public ResultVo returnBooks(@RequestBody List<ReturnParm> parm) {
        borrowBookService.returnBook(parm);
        return ResultUtils.success("还书成功");
    }
    
    //异常还书
    @PostMapping("/exceptionBooks")
    public ResultVo exceptionBooks(@RequestBody ExceptionParm parm) {
        borrowBookService.exceptionBook(parm);
        return ResultUtils.success("还书成功");
    }
    
    //借阅查看
    @GetMapping("/getLookBorrowList")
    public ResultVo getLookBorrowList(LookParm parm, HttpServletRequest request) {
        //获取token
        String token = request.getHeader("token");
        Claims claims = jwtUtils.getClaimsFromToken(token);
        String userType = (String) claims.get("userType");
        IPage<LookBorrow> lookBorrowList = null;
        //0的时候是读者，1是管理
        if (userType.equals("0")) {
            lookBorrowList = borrowBookService.getReaderLookBorrowList(parm);
            return ResultUtils.success("查询成功", lookBorrowList);
            
        } else if (userType.equals("1")) {
            lookBorrowList = borrowBookService.getLookBorrowList(parm);
            return ResultUtils.success("查询成功", lookBorrowList);
            
        } else {
            return ResultUtils.error("用户类型错误");
        }
        
    }
    
    
}
