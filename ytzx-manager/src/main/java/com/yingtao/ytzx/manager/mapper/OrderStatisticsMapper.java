package com.yingtao.ytzx.manager.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yingtao.ytzx.model.dto.order.OrderStatisticsDto;
import com.yingtao.ytzx.model.entity.order.OrderStatistics;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Adam
 * @create 2024-04-23 20:17
 */
@Mapper
public interface OrderStatisticsMapper extends BaseMapper<OrderStatistics> {

    List<OrderStatistics> select(OrderStatisticsDto orderStatisticsDto);
}
