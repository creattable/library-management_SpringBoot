package com.qin.web.book_borrow.entity;

import lombok.Data;

/**
 * @author 秦家乐
 * @date 2022/3/20 17:17
 */
@Data
public class ExceptionParm {
    
    private Long borrowId;
    //图书id
    private Long bookId;
    //判断是异常还是丢失，丢失的话不能加库存
    //0是异常，1是丢失
    private String type;
    //异常还书备注
    private String exceptionText;

}
