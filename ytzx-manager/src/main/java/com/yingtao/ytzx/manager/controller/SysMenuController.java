package com.yingtao.ytzx.manager.controller;

import com.yingtao.ytzx.manager.service.SysMenuService;
import com.yingtao.ytzx.model.entity.system.SysMenu;
import com.yingtao.ytzx.model.vo.common.Result;
import com.yingtao.ytzx.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Adam
 * @create 2024-04-16 20:34
 */
@RestController
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @Operation(summary = "查询菜单")
    @GetMapping("/findNodes")
    public Result findNodes(){
        List<SysMenu> sysMenuList = sysMenuService.findNodes();
        return Result.build(sysMenuList, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "添加菜单")
    @PostMapping("/save")
    public Result save(@RequestBody SysMenu sysMenu){
        sysMenuService.save(sysMenu);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "修改菜单")
    @PutMapping("/updateById")
    public Result updateById(@RequestBody SysMenu sysMenu){
        sysMenuService.updateById(sysMenu);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "删除菜单")
    @DeleteMapping("/removeById/{id}")
    public Result removeById(@PathVariable Long id){
        sysMenuService.removeById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
