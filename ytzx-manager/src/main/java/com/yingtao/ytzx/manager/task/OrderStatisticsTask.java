package com.yingtao.ytzx.manager.task;

import cn.hutool.core.date.DateUtil;
import com.yingtao.ytzx.manager.mapper.OrderInfoMapper;
import com.yingtao.ytzx.manager.mapper.OrderStatisticsMapper;
import com.yingtao.ytzx.model.entity.order.OrderStatistics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Adam
 * @create 2024-04-23 19:57
 */
@Component
@Slf4j
public class OrderStatisticsTask {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrderStatisticsMapper orderStatisticsMapper;

    @Scheduled(cron = "0 0 2 * * ?")
    public void orderTotalAmountStatistics(){
        String createTime = DateUtil.offsetDay(new Date(), -1).
                toString(new SimpleDateFormat("yyyy-MM-dd"));
        OrderStatistics orderStatistics = orderInfoMapper.selectOrderStatistics(createTime);
        if(orderStatistics == null){
            orderStatisticsMapper.insert(orderStatistics);
        }
    }
}
