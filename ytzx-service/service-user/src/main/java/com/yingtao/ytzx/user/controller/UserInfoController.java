package com.yingtao.ytzx.user.controller;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageInfo;
import com.yingtao.ytzx.model.dto.h5.UserLoginDto;
import com.yingtao.ytzx.model.dto.h5.UserRegisterDto;
import com.yingtao.ytzx.model.entity.user.UserCollect;
import com.yingtao.ytzx.model.entity.user.UserInfo;
import com.yingtao.ytzx.model.vo.common.Result;
import com.yingtao.ytzx.model.vo.common.ResultCodeEnum;
import com.yingtao.ytzx.model.vo.h5.UserBrowseHistoryVo;
import com.yingtao.ytzx.model.vo.h5.UserCollectVo;
import com.yingtao.ytzx.model.vo.h5.UserInfoVo;
import com.yingtao.ytzx.user.service.UserInfoService;
import com.yingtao.ytzx.utils.AuthContextUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Adam
 * @create 2024-04-27 20:32
 */
@RestController
@RequestMapping("/api/user/userInfo")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;


    @PostMapping("register")
    public Result userRegister(@RequestBody UserRegisterDto userRegisterDto){
        userInfoService.userRegister(userRegisterDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("login")
    public Result login(@RequestBody UserLoginDto userLoginDto){
        String token = userInfoService.login(userLoginDto);
        return Result.build(token, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("auth/getCurrentUserInfo")
    public Result getCurrentUserInfo(HttpServletRequest request){
        String token = request.getHeader("token");
        UserInfoVo userInfoVo = userInfoService.getCurrentUserInfo(token);
        return Result.build(userInfoVo, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("isCollect/{skuId}")
    public Result isCollect(@PathVariable Long skuId){
        boolean flag = userInfoService.isCollect(skuId);
        return Result.build(flag, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("auth/findUserCollectPage/{page}/{limit}")
    public Result findUserCollectPage(@PathVariable Integer page,
                                      @PathVariable Integer limit){
        PageInfo<UserCollectVo> pageInfo = userInfoService.findUserCollectPage(page, limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("auth/findUserBrowseHistoryPage/{page}/{limit}")
    public Result findUserBrowseHistoryPage(@PathVariable Integer page,
                                            @PathVariable Integer limit){
        PageInfo<UserBrowseHistoryVo> userBrowseHistoryVoPageInfo = userInfoService.findUserBrowseHistoryPage(page, limit);
        return Result.build(userBrowseHistoryVoPageInfo, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("auth/collect/{skuId}")
    public Result collectSku(@PathVariable Long skuId){
        Boolean flag = userInfoService.collectSku(skuId)
        return Result.build(flag, ResultCodeEnum.SUCCESS);
    }

}
