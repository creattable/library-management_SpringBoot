package com.qin.web.sys_login.entity;

import lombok.Data;

/**
 * @author 秦家乐
 * @date 2022/3/24 18:24
 */
@Data
public class UserInfo {
    
    private String name;
    private String avatar;
    private String introduction;
    private Object[] roles;
    
}
