package com.qin.web.sys_login.entity;

import lombok.Data;

/**
 * @author 秦家乐
 * @date 2022/3/23 21:13
 */
@Data
public class LoginResult {
    private Long userId;
    private String token;
    //token过期时间
    private Long expireTime;
}