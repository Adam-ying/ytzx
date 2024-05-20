package com.yingtao.ytzx.manager.controller;

import com.yingtao.ytzx.manager.service.OrderInfoService;
import com.yingtao.ytzx.model.dto.order.OrderStatisticsDto;
import com.yingtao.ytzx.model.vo.common.Result;
import com.yingtao.ytzx.model.vo.common.ResultCodeEnum;
import com.yingtao.ytzx.model.vo.order.OrderStatisticsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Adam
 * @create 2024-04-23 20:37
 */
@RestController
@RequestMapping("/admin/order/orderInfo")
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;

    @GetMapping("/getOrderStatisticsData")
    public Result getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto){
        OrderStatisticsVo orderStatisticsVo = orderInfoService.getOrderStatisticsData(orderStatisticsDto);
        return Result.build(orderStatisticsVo, ResultCodeEnum.SUCCESS);
    }
}
