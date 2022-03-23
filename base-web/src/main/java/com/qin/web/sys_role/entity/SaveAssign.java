package com.qin.web.sys_role.entity;

import lombok.Data;

import java.util.List;

/**
 * @author 秦家乐
 * @date 2022/3/23 15:57
 */
@Data
public class SaveAssign {
    private Long roleId;
    private List<Long> list;
}
