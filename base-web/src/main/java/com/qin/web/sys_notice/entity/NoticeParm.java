package com.qin.web.sys_notice.entity;

import lombok.Data;

/**
 * @author 秦家乐
 * @date 2022/3/21 20:14
 */
@Data
public class NoticeParm {
    private Long currentPage;
    private Long pageSize;
    private String noticeTitle;
}
