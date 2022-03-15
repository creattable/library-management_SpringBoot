package com.qin.web.sys_menu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 秦家乐
 * @date 2022/3/15 9:05
 */
@Data
@TableName("sys_menu")
public class SysMenu {
    
    @TableId(type = IdType.AUTO)
    private Long menuId;
    //上级的ID，因为归属于系统管理，只有绑定了上级ID才能找到属于那一个菜单下面的
    private Long parentId;
    //菜单名称
    private String title;
    //权限字段
    private String code;
    //路由name
    private String name;
    //路由path
    private String path;
    //组件路径
    private String url;
    //类型(0 目录 1菜单，2按钮)
    /*
    * 例如系统管理，读者管理就是目录
    * 点进去之后的用户管理，角色管理就是菜单
    *
    * */
    private String type;
    //菜单图标
    private String icon;
    //上级菜单名称
    private String parentName;
    //序号
    private Long orderNum;
    private Date createTime;
    private Date updateTime;
    
    //该字段不属于数据库表，不需要映射，只需要在查询的时候用到，日常需要排除
    @TableField(exist = false)
    private List<SysMenu> children=new ArrayList<>();
    //不属于数据库，给前端查询列表做美观优化用的
    @TableField(exist = false)
    private Boolean open;
    
    
}
