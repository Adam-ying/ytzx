package com.yingtao.ytzx.manager.controller;

import com.yingtao.ytzx.manager.service.SysRoleMenuService;
import com.yingtao.ytzx.model.dto.system.AssginMenuDto;
import com.yingtao.ytzx.model.vo.common.Result;
import com.yingtao.ytzx.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Adam
 * @create 2024-04-17 19:36
 */
@RestController
@RequestMapping(value = "/admin/system/sysRoleMenu")
public class SysRoleMenuController {

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Operation(summary = "查询菜单")
    @GetMapping("/findSysRoleMenuByRoleId/{roleId}")
    public Result findSysRoleMenuByRoleId(@PathVariable Long roleId){
        Map<String, Object> sysRoleMenuList = sysRoleMenuService.findSysRoleMenuByRoleId(roleId);
        return Result.build(sysRoleMenuList, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "保存菜单")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginMenuDto assginMenuDto){
        sysRoleMenuService.doAssign(assginMenuDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
