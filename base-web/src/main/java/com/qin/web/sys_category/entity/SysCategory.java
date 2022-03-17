package com.qin.web.sys_category.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 秦家乐
 * @date 2022/3/17 12:21
 */
@Data
@TableName("sys_category")
public class SysCategory {
    @TableId(type = IdType.AUTO)
    private Long categoryId;
    private String categoryName;
    private Long orderNum;
    


}
