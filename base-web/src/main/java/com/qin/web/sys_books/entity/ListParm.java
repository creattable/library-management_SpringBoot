package com.qin.web.sys_books.entity;

import lombok.Data;

/**
 * @author 秦家乐
 * @date 2022/3/17 20:56
 */
@Data
public class ListParm {
    private Long currentPage;
    private Long pageSize;
    //图书分类Id
    private String categoryId;
    private String bookName;
    private String bookPlaceNum;
    private String bookAuther;
}
