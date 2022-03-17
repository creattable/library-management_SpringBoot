package com.qin.web.sys_books.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 秦家乐
 * @date 2022/3/17 20:42
 */
@Data
@TableName("sys_books")
public class SysBooks {
    @TableId(type = IdType.AUTO)
    private Long bookId;
    private Long categoryId;
    
    @TableField(exist = false)
    private String categoryName;
    
    private String bookName;
    private String bookCode;
    private String bookPlaceNum;
    private String bookAuther;
    private String bookProduct;
    private BigDecimal bookPrice;
    private Integer bookStore;
    
}
