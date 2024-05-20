package com.yingtao.ytzx.user.service;

import com.github.pagehelper.PageInfo;
import com.yingtao.ytzx.model.dto.h5.UserLoginDto;
import com.yingtao.ytzx.model.dto.h5.UserRegisterDto;
import com.yingtao.ytzx.model.vo.common.Result;
import com.yingtao.ytzx.model.vo.h5.UserBrowseHistoryVo;
import com.yingtao.ytzx.model.vo.h5.UserInfoVo;

/**
 * @author Adam
 * @create 2024-04-27 20:33
 */
public interface UserInfoService {
    void userRegister(UserRegisterDto userRegisterDto);

    String login(UserLoginDto userLoginDto);

    UserInfoVo getCurrentUserInfo(String token);

    boolean isCollect(Long skuId);

    PageInfo findUserCollectPage(Integer page, Integer limit);

    PageInfo<UserBrowseHistoryVo> findUserBrowseHistoryPage(Integer page, Integer limit);

    Boolean collectSku(Long skuId);
}
