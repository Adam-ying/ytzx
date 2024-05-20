package com.yingtao.ytzx.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yingtao.ytzx.model.dto.order.OrderStatisticsDto;
import com.yingtao.ytzx.model.entity.order.OrderInfo;
import com.yingtao.ytzx.model.vo.order.OrderStatisticsVo;

/**
 * @author Adam
 * @create 2024-04-23 20:38
 */
public interface OrderInfoService extends IService<OrderInfo> {
    OrderStatisticsVo getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto);
}
