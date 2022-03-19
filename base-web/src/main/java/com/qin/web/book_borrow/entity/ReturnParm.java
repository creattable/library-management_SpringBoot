package com.qin.web.book_borrow.entity;

import lombok.Data;

/**
 * @author 秦家乐
 * @date 2022/3/19 22:26
 */
@Data
public class ReturnParm {
    
    private Long borrowId;
    //图书id
    private Long bookId;
}