package com.qin.web.sys_category.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 秦家乐
 * @date 2022/3/26 12:54
 */
@Data
public class CategoryEcharts {
    private List<String> names = new ArrayList<>();
    private List<Integer> counts = new ArrayList<>();
}
