package com.qin.web.sys_category.entity;

import lombok.Data;

/**
 * @author 秦家乐
 * @date 2022/3/17 12:31
 */
@Data
public class ListCateParm {
    private Long currentPage;
    private Long pageSize;
    private String categoryName;


}
