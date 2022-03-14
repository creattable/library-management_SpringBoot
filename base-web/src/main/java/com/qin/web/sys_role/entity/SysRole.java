package com.qin.web.sys_role.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author 秦家乐
 * @date 2022/3/14 20:19
 */

@Data
@TableName("sys_role")
public class SysRole {
    @TableId(type = IdType.AUTO)
    private long roleId;
    private String roleName;
    private String roleType;
    private String remake;
    private Date createdTime;
    private Date updatedTime;
    
    
}
