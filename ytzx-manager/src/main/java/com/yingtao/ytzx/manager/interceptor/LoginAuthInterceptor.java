package com.yingtao.ytzx.manager.interceptor;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.yingtao.ytzx.model.entity.system.SysUser;
import com.yingtao.ytzx.model.vo.common.Result;
import com.yingtao.ytzx.model.vo.common.ResultCodeEnum;
import com.yingtao.ytzx.utils.AuthContextUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 * @author Adam
 * @create 2024-04-14 19:50
 */
@Component
public class LoginAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        if("OPTIONS".equals(method)){
            return true;
        }

        String token = request.getHeader("token");
        if(StrUtil.isEmpty(token)){
            responseNoLoginInfo(response);
            return false;
        }

        String userJson = redisTemplate.opsForValue().get("user:login:" + token);
        if(StrUtil.isEmpty(userJson)){
            responseNoLoginInfo(response);
            return false;
        }

        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        AuthContextUtil.set(sysUser);
        redisTemplate.expire("user:login:" + token, 30, TimeUnit.MINUTES);

        return true;
    }

    private void responseNoLoginInfo(HttpServletResponse response) {
        Result<Object> result = Result.build(null, ResultCodeEnum.LOGIN_AUTH);
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try{
            writer = response.getWriter();
            writer.print(JSON.toJSONString(result));
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(writer != null){
                writer.close();
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        AuthContextUtil.remove();
    }
}
