package com.yingtao.ytzx.manager.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yingtao.ytzx.model.dto.order.OrderStatisticsDto;
import com.yingtao.ytzx.model.entity.order.OrderInfo;
import com.yingtao.ytzx.model.entity.order.OrderStatistics;
import com.yingtao.ytzx.model.vo.order.OrderStatisticsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Adam
 * @create 2024-04-23 20:11
 */
@Mapper
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {
    OrderStatistics selectOrderStatistics(String createTime);

}
