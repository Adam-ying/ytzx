package com.yingtao.ytzx.order.service;

import com.github.pagehelper.PageInfo;
import com.yingtao.ytzx.model.dto.h5.OrderInfoDto;
import com.yingtao.ytzx.model.entity.order.OrderInfo;
import com.yingtao.ytzx.model.vo.h5.TradeVo;

import java.util.List;

/**
 * @author Adam
 * @create 2024-05-17 20:18
 */
public interface OrderInfoService {
    TradeVo trade();

    Long submitOrder(OrderInfoDto orderInfoDto);

    OrderInfo getOrderInfoById(Long orderId);

    TradeVo buyNow(Long skuId);

    PageInfo getByPage(Integer page, Integer limit, Integer orderStatus);

    OrderInfo getOrderInfoByOrderNo(String orderNo);

    void updateOrderStatusPayed(String orderNo, Integer orderStatus);
}
