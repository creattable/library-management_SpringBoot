package com.qin.web.book_borrow.entity;

import lombok.Data;

/**
 * @author 秦家乐
 * @date 2022/3/19 21:15
 */
@Data
public class ListParm {
    private Long currentPage;
    private Long pageSize;
    private String username;
    private String borrowStatus;
}