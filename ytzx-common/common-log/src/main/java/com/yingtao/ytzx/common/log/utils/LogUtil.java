package com.yingtao.ytzx.common.log.utils;

import com.alibaba.fastjson.JSON;
import com.yingtao.ytzx.common.log.annotation.Log;
import com.yingtao.ytzx.model.entity.system.SysOperLog;
import com.yingtao.ytzx.utils.AuthContextUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author Adam
 * @create 2024-04-24 19:55
 */
public class LogUtil {

    public static void beforeHandleLog(Log sysLog, ProceedingJoinPoint joinPoint,
                                       SysOperLog sysOperLog){

        // 设置操作模块名称
        sysOperLog.setTitle(sysLog.title());
        sysOperLog.setOperatorType(sysLog.operatorType().name());

        // 获取目标方法信息
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        sysOperLog.setMethod(method.getDeclaringClass().getName());

        // 获取请求相关参数
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        sysOperLog.setRequestMethod(request.getMethod());
        sysOperLog.setOperUrl(request.getRequestURI());
        sysOperLog.setOperIp(request.getRemoteAddr());

        // 设置请求参数
        if(sysLog.isSaveRequestData()){
            String requestMethod = sysOperLog.getRequestMethod();
            if(HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)){
                String params = Arrays.toString(joinPoint.getArgs());
                sysOperLog.setOperParam(params);
            }
        }
        sysOperLog.setOperName(AuthContextUtil.get().getUserName());
    }

    public static void afterHandlLog(Log sysLog, Object proceed, SysOperLog sysOperLog,
                                     int status, String errorMsg){
        if(sysLog.isSaveResponseData()){
            sysOperLog.setJsonResult(JSON.toJSONString(proceed));
        }
        sysOperLog.setStatus(status);
        sysOperLog.setErrorMsg(errorMsg);
    }
}
