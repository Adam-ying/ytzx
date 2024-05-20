package com.yingtao.ytzx.manager.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yingtao.ytzx.manager.mapper.OrderInfoMapper;
import com.yingtao.ytzx.manager.mapper.OrderStatisticsMapper;
import com.yingtao.ytzx.manager.service.OrderInfoService;
import com.yingtao.ytzx.model.dto.order.OrderStatisticsDto;
import com.yingtao.ytzx.model.entity.order.OrderInfo;
import com.yingtao.ytzx.model.entity.order.OrderStatistics;
import com.yingtao.ytzx.model.vo.order.OrderStatisticsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Adam
 * @create 2024-04-23 20:39
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

    @Autowired
    private OrderStatisticsMapper orderStatisticsMapper;

    @Override
    public OrderStatisticsVo getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto) {

        List<OrderStatistics> orderStatisticsList = orderStatisticsMapper.select(orderStatisticsDto);

        List<String> dateList = orderStatisticsList.stream().map(orderStatistics -> DateUtil.format(orderStatistics.getOrderDate(),
                "yyyy-MM-dd")).collect(Collectors.toList());

        List<BigDecimal> amountList = orderStatisticsList.stream().map(OrderStatistics::getTotalAmount).collect(Collectors.toList());

        OrderStatisticsVo orderStatisticsVo = new OrderStatisticsVo();
        orderStatisticsVo.setDateList(dateList);
        orderStatisticsVo.setAmountList(amountList);

        return orderStatisticsVo;
    }
}
