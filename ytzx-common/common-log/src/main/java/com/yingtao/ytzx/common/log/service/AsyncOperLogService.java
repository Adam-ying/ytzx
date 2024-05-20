package com.yingtao.ytzx.common.log.service;

import com.yingtao.ytzx.model.entity.system.SysOperLog;

/**
 * @author Adam
 * @create 2024-04-24 20:39
 */
public interface AsyncOperLogService{
    void saveSysOperLog(SysOperLog sysOperLog);
}
