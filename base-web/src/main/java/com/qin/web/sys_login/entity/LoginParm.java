package com.qin.web.sys_login.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 秦家乐
 * @date 2022/3/23 21:10
 */

/*
* Serializable:
* 作用:
*   1，存储对象在存储介质中，以便在下次使用的时候，可以很快捷的重建一个副本。
*   2，便于数据传输，尤其是在远程调用的时候！也可以用作跨版本之类的使用~
*
* */

@Data
public class LoginParm implements Serializable {
    private String username;
    private String password;
    private String userType;
}
