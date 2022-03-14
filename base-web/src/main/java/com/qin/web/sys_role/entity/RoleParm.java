package com.qin.web.sys_role.entity;

import lombok.Data;

/**
 * @author 秦家乐
 * @date 2022/3/14 20:32
 */
@Data
public class RoleParm {
    private Long currentPage;
    private Long pageSize;
    private String roleName;
}
