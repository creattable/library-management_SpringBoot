package com.qin.web.sys_reader_role.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 秦家乐
 * @date 2022/3/23 20:04
 */
@Data
@TableName("sys_reader_role")
public class ReaderRole {
    @TableId(type = IdType.AUTO)
    private Long readerRoleId;
    private Long reader_id;
    private Long role_id;
    
    
    
}
