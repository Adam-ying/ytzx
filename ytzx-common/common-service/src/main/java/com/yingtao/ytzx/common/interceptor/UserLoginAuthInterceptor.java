package com.yingtao.ytzx.common.interceptor;

import com.alibaba.fastjson2.JSON;
import com.yingtao.ytzx.model.entity.user.UserInfo;
import com.yingtao.ytzx.utils.AuthContextUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author Adam
 * @create 2024-04-29 19:22
 */
@Component
public class UserLoginAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userInfoJson = redisTemplate.opsForValue().get("user:token:" + request.getHeader("token"));
        AuthContextUtil.setUserInfo(JSON.parseObject(userInfoJson, UserInfo.class));
        return true;
    }
}
