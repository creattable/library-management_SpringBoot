package com.qin.web.sys_menu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qin.annotation.Auth;
import com.qin.utils.ResultUtils;
import com.qin.utils.ResultVo;
import com.qin.web.sys_menu.entity.SysMenu;
import com.qin.web.sys_menu.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author 秦家乐
 * @date 2022/3/15 9:20
 */

@RestController
@RequestMapping("/api/menu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;
    
    //新增
    @Auth
    @PostMapping
    public ResultVo addMenu(@RequestBody SysMenu menu){
        menu.setCreateTime(new Date());
        boolean save = sysMenuService.save(menu);
        if(save){
            return ResultUtils.success("新增成功");
        }
        return ResultUtils.error("新增失败");
    }
    
    
    //编辑
    @Auth
    @PutMapping
    public ResultVo editMenu(@RequestBody SysMenu menu){
        menu.setUpdateTime(new Date());
        boolean save = sysMenuService.updateById(menu);
        if(save){
            return ResultUtils.success("编辑成功");
        }
        return ResultUtils.error("编辑失败");
    }

    
    //删除
    @Auth
    @DeleteMapping("/{menuId}")
    public ResultVo deleteMenu(@PathVariable("menuId") Long menuId){
        //先判断是否有下级
        QueryWrapper<SysMenu> query =new QueryWrapper<>();
        //如果查询到表中有下级
        query.lambda().eq(SysMenu::getParentId,menuId);
        List<SysMenu> list = sysMenuService.list(query);
        if(list.size()>0){
            return ResultUtils.error("该菜单存在下级，无法删除");
        }
        boolean remove = sysMenuService.removeById(menuId);
        if(remove){
            return ResultUtils.success("删除成功");
        }
        return ResultUtils.error("删除失败");
    }
    
    
    //查询菜单列表，它的这个是一个树状结构
    @Auth
    @GetMapping("/list")
    public ResultVo getList(){
        List<SysMenu> menuList = sysMenuService.menuList();
        return ResultUtils.success("查询成功",menuList);
    }
    
    //上级菜单列表
    @Auth
    @GetMapping("/parent")
    public ResultVo getParentList(){
        List<SysMenu> menuList = sysMenuService.parentList();
        return ResultUtils.success("查询成功",menuList);
    }
    

}
