package com.yingtao.ytzx.common.log.aspect;

import com.yingtao.ytzx.common.log.annotation.Log;
import com.yingtao.ytzx.common.log.service.AsyncOperLogService;
import com.yingtao.ytzx.common.log.utils.LogUtil;
import com.yingtao.ytzx.model.entity.system.SysOperLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Adam
 * @create 2024-04-24 19:34
 */
@Component
@Slf4j
@Aspect
public class LogAspect { // 环绕通知切面类定义

    @Autowired
    private AsyncOperLogService asyncOperLogService;

    @Around(value = "@annotation(sysLog)")
    public Object doAroundAdvice(ProceedingJoinPoint joinPoint, Log sysLog){

        SysOperLog sysOperLog = new SysOperLog();
        LogUtil.beforeHandleLog(sysLog, joinPoint, sysOperLog);
        Object proceed = null;

        try{
            proceed = joinPoint.proceed();
            LogUtil.afterHandlLog(sysLog, proceed, sysOperLog, 0, null);
        }catch (Throwable e){
            e.printStackTrace();
            LogUtil.afterHandlLog(sysLog, proceed, sysOperLog, 1, e.getMessage());
            throw new RuntimeException();
        }

        asyncOperLogService.saveSysOperLog(sysOperLog);
        return proceed;
    }
}
