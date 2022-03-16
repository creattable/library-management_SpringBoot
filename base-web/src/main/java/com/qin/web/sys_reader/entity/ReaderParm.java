package com.qin.web.sys_reader.entity;

import lombok.Data;

/**
 * @author 秦家乐
 * @date 2022/3/16 20:25
 */
@Data
public class ReaderParm {
    //这两个参数的话，分页是必须要有的
    private Long currentPage;
    private Long pageSize;
    private String username;
    private String idCard;
    private String phone;
    private String learnNum;
    
    

}
