package com.qin.web.sys_user.entity;

import lombok.Data;

/**
 * @author 秦家乐
 * @date 2022/3/26 14:41
 */
@Data
public class UpdatePasswordParm {
    private Long userId;
    private String oldPassword;
    private String password;
}
