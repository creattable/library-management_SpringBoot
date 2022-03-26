package com.qin.annotation;

import java.lang.annotation.*;

/**
 * @author 秦家乐
 * @date 2022/3/26 13:37
 */

//作用范围
@Target(ElementType.METHOD)
//是否生成文档注释
@Documented
//什么时候执行
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {
}