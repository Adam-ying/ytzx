package com.yingtao.ytzx.manager.controller;

import com.github.pagehelper.PageInfo;
import com.yingtao.ytzx.common.log.annotation.Log;
import com.yingtao.ytzx.manager.service.SysRoleService;
import com.yingtao.ytzx.model.dto.system.AssginRoleDto;
import com.yingtao.ytzx.model.dto.system.SysRoleDto;
import com.yingtao.ytzx.model.entity.system.SysRole;
import com.yingtao.ytzx.model.vo.common.Result;
import com.yingtao.ytzx.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

/**
 * @author Adam
 * @create 2024-04-14 20:44
 */
@RestController
@RequestMapping(value = "/admin/system/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @Operation(summary = "查询角色")
    @PostMapping("/findByPage/{pageNum}/{pageSize}")
    public Result findByPage(@PathVariable Integer pageNum,
                             @PathVariable Integer pageSize,
                             @RequestBody SysRoleDto sysRoleDto){
        PageInfo<SysRole> pageInfo = sysRoleService.findByPage(pageNum, pageSize, sysRoleDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "增加角色")
    @Log(title = "角色添加", businessType = 0)
    @PostMapping("/saveSysRole")
    public Result saveSysRole(@RequestBody SysRole sysRole){
        sysRoleService.saveSysRole(sysRole);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "修改角色")
    @PutMapping( "/updateSysRole")
    public Result updateSysRole(@RequestBody SysRole sysRole){
        sysRoleService.updateSysRole(sysRole);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "删除角色")
    @DeleteMapping("/deleteById/{roleId}")
    public Result deleteById(@PathVariable Long roleId){
        sysRoleService.deleteById(roleId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "分配角色：查询所有角色与已分配角色")
    @GetMapping("/findAllRoles/{userId}")
    public Result findAllRoles(@PathVariable Long userId){
        Map<String, Object> resultMap = sysRoleService.findAllRoles(userId);
        return Result.build(resultMap, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "分配角色：提交保存分配角色")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginRoleDto assginRoleDto){
        sysRoleService.doAssign(assginRoleDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

}
