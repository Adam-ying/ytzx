package com.yingtao.ytzx.manager.controller;

import com.yingtao.ytzx.manager.service.SysUserService;
import com.yingtao.ytzx.manager.service.ValidateCodeService;
import com.yingtao.ytzx.model.dto.system.LoginDto;
import com.yingtao.ytzx.model.entity.system.SysUser;
import com.yingtao.ytzx.model.vo.common.Result;
import com.yingtao.ytzx.model.vo.common.ResultCodeEnum;
import com.yingtao.ytzx.model.vo.system.LoginVo;
import com.yingtao.ytzx.model.vo.system.SysMenuVo;
import com.yingtao.ytzx.model.vo.system.ValidateCodeVo;
import com.yingtao.ytzx.utils.AuthContextUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Adam
 * @create 2024-04-13 19:18
 */
@Tag(name="用户接口")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ValidateCodeService validateCodeService;


    @Operation(summary = "登录接口")
    @PostMapping("/login")
    public Result login(@RequestBody LoginDto loginDto){
        LoginVo loginVo = sysUserService.login(loginDto);
        return Result.build(loginVo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "生成验证码")
    @GetMapping("/generateValidateCode")
    public Result generateValidateCode(){
        ValidateCodeVo validateCodeVo = validateCodeService.generateValidateCode();
        return Result.build(validateCodeVo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "获取用户信息")
    @GetMapping("/getUserInfo")
    public Result getUserInfo(@RequestHeader(name = "token") String token){
        return Result.build(AuthContextUtil.get(), ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "退出功能")
    @GetMapping("/logout")
    public Result logout(@RequestHeader(name = "token")String token){
        sysUserService.logout(token);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "动态菜单")
    @GetMapping("/menus")
    public Result menus(){
        List<SysMenuVo> sysMenuVoList = sysUserService.findUserMenuList();
        return Result.build(sysMenuVoList, ResultCodeEnum.SUCCESS);
    }

}
