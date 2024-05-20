package com.yingtao.ytzx.manager.controller;

import com.github.pagehelper.PageInfo;
import com.yingtao.ytzx.manager.service.SysUserService;
import com.yingtao.ytzx.model.dto.system.SysUserDto;
import com.yingtao.ytzx.model.entity.system.SysRole;
import com.yingtao.ytzx.model.entity.system.SysUser;
import com.yingtao.ytzx.model.vo.common.Result;
import com.yingtao.ytzx.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Adam
 * @create 2024-04-15 19:37
 */
@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;


    @Operation(summary = "查询用户")
    @GetMapping("/findByPage/{pageNum}/{pageSize}")
    public Result findByPage(@PathVariable Integer pageNum,
                             @PathVariable Integer pageSize,
                             @RequestBody SysUserDto sysUserDto){
        PageInfo<SysRole> pageInfo = sysUserService.findByPage(pageNum, pageSize, sysUserDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "增加用户")
    @PostMapping("/saveSysUser")
    public Result saveSysUser(@RequestBody SysUser sysUser){
        sysUserService.saveSysUser(sysUser);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "修改用户")
    @PutMapping("/updateSysUser")
    public Result updateSysUser(@RequestBody SysUser sysUser){
        sysUserService.updateSysUser(sysUser);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/deleteById/{userId}")
    public Result deleteById(@PathVariable Long userId){
        sysUserService.deleteById(userId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

}
