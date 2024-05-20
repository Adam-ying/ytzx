package com.yingtao.ytzx.manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yingtao.ytzx.common.log.service.AsyncOperLogService;
import com.yingtao.ytzx.manager.mapper.SysOperLogMapper;
import com.yingtao.ytzx.model.entity.system.SysOperLog;
import org.springframework.stereotype.Service;


/**
 * @author Adam
 * @create 2024-04-24 20:41
 */
@Service
public class AsyncOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog> implements AsyncOperLogService {
    @Override
    public void saveSysOperLog(SysOperLog sysOperLog) {
        save(sysOperLog);
    }
}
