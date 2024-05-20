package com.yingtao.ytzx.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yingtao.ytzx.model.entity.order.OrderInfo;
import com.yingtao.ytzx.model.entity.order.OrderItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Adam
 * @create 2024-05-17 20:27
 */
@Mapper
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {
}
