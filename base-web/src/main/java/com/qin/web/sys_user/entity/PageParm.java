package com.qin.web.sys_user.entity;

import lombok.Data;

/**
 * @author 秦家乐
 * @date 2022/3/13 22:17
 */
@Data
public class PageParm {
    private Long currentPage;
    private Long pageSize;
    private String phone;
    private String nickName;
}