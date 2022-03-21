package com.qin.web.book_borrow.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author 秦家乐
 * @date 2022/3/21 8:04
 */


/*
*
* 返回借阅查询
* 返回的时候用的，
*
* */

@Data
public class LookBorrow {
    private Long borrowId;
    private Long bookId;
    private String bookName;
    private String bookPlaceNum;
    private String username;
    private String learnNum;
    private String className;
    private String phone;
    private String borrowStatus;
    //JsonFormat:把数据库中的时间格式设置成pattern参数的格式
    //虽然数据库也是这种格式，但是在传送的时候可能会变成其他格式类型
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date borrowTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date returnTime;
    private String applyStatus;
    private String returnStatus;
    private String excepionText;
    private String applyText;
    private String timeStatus;
}
