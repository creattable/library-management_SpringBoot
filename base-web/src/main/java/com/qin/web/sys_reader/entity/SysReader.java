package com.qin.web.sys_reader.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 秦家乐
 * @date 2022/3/16 20:09
 */


/*
* 用学号登录,username改变成学号，和learnNum做一个对换
*
* */

@Data
@TableName("sys_reader")
public class SysReader {
    @TableId(type = IdType.AUTO)
    private Long readerId;
    private String learnNum;  //姓名
    private String username;  //学号
    private String idCard;
    private String sex;
    private String phone;
    private String password;
    private String type;
    //管理员是否已审核
    private String checkStatus;
    //账户是否停用
    private String userStatus;
    private String className;
    
    

}
