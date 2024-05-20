package com.yingtao.ytzx.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yingtao.ytzx.common.exception.YtException;
import com.yingtao.ytzx.feign.product.ProductFeignClient;
import com.yingtao.ytzx.model.dto.h5.UserLoginDto;
import com.yingtao.ytzx.model.dto.h5.UserRegisterDto;
import com.yingtao.ytzx.model.entity.product.ProductSku;
import com.yingtao.ytzx.model.entity.user.UserBrowseHistory;
import com.yingtao.ytzx.model.entity.user.UserCollect;
import com.yingtao.ytzx.model.entity.user.UserInfo;
import com.yingtao.ytzx.model.vo.common.Result;
import com.yingtao.ytzx.model.vo.common.ResultCodeEnum;
import com.yingtao.ytzx.model.vo.h5.UserBrowseHistoryVo;
import com.yingtao.ytzx.model.vo.h5.UserCollectVo;
import com.yingtao.ytzx.model.vo.h5.UserInfoVo;
import com.yingtao.ytzx.user.mapper.UserBrowseHistoryMapper;
import com.yingtao.ytzx.user.mapper.UserCollectMapper;
import com.yingtao.ytzx.user.mapper.UserInfoMapper;
import com.yingtao.ytzx.user.service.UserInfoService;
import com.yingtao.ytzx.utils.AuthContextUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Adam
 * @create 2024-04-27 20:33
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserCollectMapper userCollectMapper;

    @Autowired
    private UserBrowseHistoryMapper userBrowseHistoryMapper;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Override
    public void userRegister(UserRegisterDto userRegisterDto) {
        String code = userRegisterDto.getCode();
        String username = userRegisterDto.getUsername();
        String password = userRegisterDto.getPassword();
        String nickName = userRegisterDto.getNickName();

        if (StringUtils.isEmpty(username) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(nickName) ||
                StringUtils.isEmpty(code)){
            throw new YtException(ResultCodeEnum.DATA_ERROR);
        }

        String validateCode = redisTemplate.opsForValue().get("phone:code:" + username);
        if(!code.equals(validateCode)){
            throw new YtException(ResultCodeEnum.VALIDATECODE_ERROR);
        }
        UserInfo userInfo = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("username", username));
        if(userInfo != null){
            throw new YtException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }

        userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        userInfo.setNickName(nickName);
        userInfo.setPhone(username);
        userInfo.setStatus(1);
        userInfo.setSex(0);

        userInfo.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        userInfoMapper.insert(userInfo);

        // 删除Redis中的数据
        redisTemplate.delete("phone:code:" + username) ;
    }

    @Override
    public String login(UserLoginDto userLoginDto) {
        String username = userLoginDto.getUsername();
        String password = userLoginDto.getPassword();
        if(StrUtil.isEmpty(username) || StrUtil.isEmpty(password)){
            throw new YtException(ResultCodeEnum.DATA_ERROR);
        }

        UserInfo userInfo = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("username", username));
        if(userInfo == null){
            throw new YtException(ResultCodeEnum.LOGIN_ERROR);
        }

        if(!DigestUtils.md5DigestAsHex(password.getBytes()).equals(userInfo.getPassword())){
            throw new YtException(ResultCodeEnum.LOGIN_ERROR);
        }

        if(userInfo.getStatus() == 0){
            throw new YtException(ResultCodeEnum.ACCOUNT_STOP);
        }
        String token = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set("user:token:"+token, JSON.toJSONString(userInfo), 30, TimeUnit.DAYS);
        return token;
    }

    @Override
    public UserInfoVo getCurrentUserInfo(String token) {
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtil.copyProperties(userInfo, userInfoVo);
        return userInfoVo;
    }

    @Override
    public boolean isCollect(Long skuId) {
        Long userId = AuthContextUtil.getUserInfo().getId();
        Long count = userCollectMapper.selectCount(new QueryWrapper<UserCollect>().eq("user_id", userId)
                .eq("sku_id", skuId));
        if(count > 0){
            return true;
        }
        return false;
    }

    @Override
    public PageInfo findUserCollectPage(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        Long userId = AuthContextUtil.getUserInfo().getId();
        List<UserCollect> userCollectList = userCollectMapper.selectList(new QueryWrapper<UserCollect>()
                .eq("user_id", userId).eq("is_deleted", 0));

        List<UserCollectVo> userCollectVoList = new ArrayList<>();
        for(UserCollect userCollect: userCollectList){
            ProductSku productSku = productFeignClient.getBySkuId(userCollect.getSkuId());
            UserCollectVo userCollectVo = BeanUtil.copyProperties(userCollect, UserCollectVo.class);
            userCollectVo.setSkuName(productSku.getSkuName());
            userCollectVo.setSalePrice(productSku.getSalePrice());
            userCollectVo.setThumbImg(productSku.getThumbImg());
            userCollectVoList.add(userCollectVo);
        }
        PageInfo<UserCollectVo> pageinfo = new PageInfo<>(userCollectVoList);
        return pageinfo;
    }

    @Override
    public PageInfo<UserBrowseHistoryVo> findUserBrowseHistoryPage(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        Long userId = AuthContextUtil.getUserInfo().getId();
        List<UserBrowseHistory> userBrowseHistoryList = userBrowseHistoryMapper.selectList(new QueryWrapper<UserBrowseHistory>()
                .eq("user_id", userId).eq("is_deleted", 0));
        List<UserBrowseHistoryVo> userBrowseHistoryVoList = new ArrayList<>();
        for(UserBrowseHistory userBrowseHistory: userBrowseHistoryList){
            ProductSku productSku = productFeignClient.getBySkuId(userBrowseHistory.getSkuId());
            UserBrowseHistoryVo userBrowseHistoryVo = BeanUtil
                    .copyProperties(userBrowseHistory, UserBrowseHistoryVo.class);
            userBrowseHistoryVo.setSkuName(productSku.getSkuName());
            userBrowseHistoryVo.setSalePrice(productSku.getSalePrice());
            userBrowseHistoryVo.setThumbImg(productSku.getThumbImg());
            userBrowseHistoryVoList.add(userBrowseHistoryVo);
        }
        PageInfo<UserBrowseHistoryVo> userBrowseHistoryVoPageInfo = new PageInfo<>(userBrowseHistoryVoList);
        return userBrowseHistoryVoPageInfo;
    }

    @Override
    public Boolean collectSku(Long skuId) {
        UserCollect userCollect = new UserCollect();
        userCollect.setUserId(AuthContextUtil.getUserInfo().getId());
        userCollect.setSkuId(skuId);
        int row = userCollectMapper.insert(userCollect);
        return row > 0 ? true : false;
    }
}
