package com.qin.web.book_borrow.controller;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qin.annotation.Auth;
import com.qin.jwt.JwtUtils;
import com.qin.utils.ResultUtils;
import com.qin.utils.ResultVo;
import com.qin.web.book_borrow.entity.*;
import com.qin.web.book_borrow.service.BorrowBookService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.util.Date;
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
    @Auth
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
    @Auth
    @PostMapping
    public ResultVo borrow(@RequestBody BorrowParm parm, HttpServletRequest request) {
        
        //先获取下token
        String token = request.getHeader("token");
        
        //token过期
        if (StringUtils.isEmpty(token)) {
            return ResultUtils.error("token过期", 600);
        }
        
        Claims claims = jwtUtils.getClaimsFromToken(token);
        String userType = (String) claims.get("userType");
        
        borrowBookService.borrow(parm, userType);
        return ResultUtils.success("借书成功");
    }
    
    
    //还书列表
    @Auth
    @GetMapping("/getBorrowList")
    public ResultVo getBorrowList(ListParm parm) {
        IPage<ReturnBook> borrowList = borrowBookService.getBorrowList(parm);
        return ResultUtils.success("查询成功", borrowList);
    }
    
    //还书(可批量)
    @Auth
    @PostMapping("/returnBooks")
    public ResultVo returnBooks(@RequestBody List<ReturnParm> parm) {
        borrowBookService.returnBook(parm);
        return ResultUtils.success("还书成功");
    }
    
    //异常还书
    @Auth
    @PostMapping("/exceptionBooks")
    public ResultVo exceptionBooks(@RequestBody ExceptionParm parm) {
        borrowBookService.exceptionBook(parm);
        return ResultUtils.success("还书成功");
    }
    
    //借阅查看
    @Auth
    @GetMapping("/getLookBorrowList")
    public ResultVo getLookBorrowList(LookParm parm, HttpServletRequest request) {
        //获取token
        String token = request.getHeader("token");
        
        //token过期
        if (StringUtils.isEmpty(token)) {
            return ResultUtils.error("token过期", 600);
        }
        
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
    
    
    //借书续期
    @Auth
    @PostMapping("/addTime")
    public ResultVo addTime(@RequestBody BorrowParm parm) {
        BorrowBook borrowBook = new BorrowBook();
        borrowBook.setBorrowId(parm.getBorrowId());
        borrowBook.setReturnTime(parm.getReturnTime());
        boolean b = borrowBookService.updateById(borrowBook);
        if (b) {
            return ResultUtils.success("续期成功");
        }
        return ResultUtils.error("续期失败");
        
    }
    
    //借书待审核总数
    //如果是管理则查所有的，读者只能查自己的
    @Auth
    @GetMapping("/getBorrowApplyCount")
    public ResultVo getBorrowApplyCount(String userType, Long userId) {
        if (userType.equals("0")) {
            QueryWrapper<BorrowBook> query = new QueryWrapper<>();
            query.lambda().eq(BorrowBook::getApplyStatus, "0")
                    .eq(BorrowBook::getReaderId, userId);
            int count = borrowBookService.count(query);
            return ResultUtils.success("查询成功", count);
        } else if (userType.equals("1")) {
            QueryWrapper<BorrowBook> query = new QueryWrapper<>();
            query.lambda().eq(BorrowBook::getApplyStatus, "0");
            int count = borrowBookService.count(query);
            return ResultUtils.success("查询成功", count);
        } else {
            return ResultUtils.success("用户类型错误", 0);
        }
        
        
    }
    
    //到期待还总数
    //如果是管理则查所有的，读者只能查自己的
    @Auth
    @GetMapping("/getBorrowReturnCount")
    public ResultVo getBorrowReturnCount(String userType, Long userId) {
        System.out.println("*************************************");
        System.out.println("userType:"+userType);
        System.out.println("userId:"+userId);
        if (userType.equals("0")) { //读者
            QueryWrapper<BorrowBook> query = new QueryWrapper<>();
            query.lambda().eq(BorrowBook::getBorrowStatus, "1")
                    .lt(BorrowBook::getReturnTime, new Date())
                    .eq(BorrowBook::getReaderId, userId);
            int count = borrowBookService.count(query);
            return ResultUtils.success("查询成功", count);
        } else if (userType.equals("1")) { //系统管理员
            QueryWrapper<BorrowBook> query = new QueryWrapper<>();
            
            query.lambda().eq(BorrowBook::getBorrowStatus, "1")
                    .lt(BorrowBook::getReturnTime, new Date());
            int count = borrowBookService.count(query);
            System.out.println(count+"-------------------------------------------");
            return ResultUtils.success("查询成功", count);
        } else {
            return ResultUtils.error("查询失败");
        }
    
    }
    
    
}
