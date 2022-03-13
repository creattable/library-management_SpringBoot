package com.qin.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 秦家乐
 * @date 2022/3/13 17:29
 */


/*
* @Data：lombok中自动生成get和set方法
* @AllArgsConstructor:自从生成带参的构造方法
*
* */

@Data
@AllArgsConstructor
public class ResultVo<T> {
    private String msg;
    private int code;
    private T data;
}
