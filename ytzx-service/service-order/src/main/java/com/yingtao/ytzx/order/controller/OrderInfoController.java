package com.yingtao.ytzx.order.controller;

import com.github.pagehelper.PageInfo;
import com.yingtao.ytzx.model.dto.h5.OrderInfoDto;
import com.yingtao.ytzx.model.entity.order.OrderInfo;
import com.yingtao.ytzx.model.vo.common.Result;
import com.yingtao.ytzx.model.vo.common.ResultCodeEnum;
import com.yingtao.ytzx.model.vo.h5.TradeVo;
import com.yingtao.ytzx.order.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author Adam
 * @create 2024-05-17 20:16
 */
@RestController
@RequestMapping("/api/order/orderInfo")
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;

    @GetMapping("auth/trade")
    public Result trade() {
        TradeVo tradeVo = orderInfoService.trade();
        return Result.build(tradeVo, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("auth/submitOrder")
    public Result submitOrder(@RequestBody OrderInfoDto orderInfoDto) {
        Long orderId = orderInfoService.submitOrder(orderInfoDto);
        return Result.build(orderId, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("auth/{orderId}")
    public Result getOrderInfoById(@PathVariable Long orderId) {
        OrderInfo orderInfo = orderInfoService.getOrderInfoById(orderId);
        return Result.build(orderInfo, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("auth/buy/{skuId}")
    public Result buyNow(@PathVariable Long skuId) {
        TradeVo tradeVo = orderInfoService.buyNow(skuId);
        return Result.build(tradeVo, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("auth/{page}/{limit}")
    public Result getByPage(@PathVariable Integer page,
                            @PathVariable Integer limit,
                            @RequestParam(required = false, defaultValue = "") Integer orderStatus){
        PageInfo pageInfo = orderInfoService.getByPage(page, limit, orderStatus);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("auth/getOrderInfoByOrderNo/{orderNo}")
    public OrderInfo getOrderInfoByOrderNo(@PathVariable String orderNo){
        OrderInfo orderInfo = orderInfoService.getOrderInfoByOrderNo(orderNo);
        return orderInfo;
    }

    @GetMapping("auth/updateOrderStatusPayed/{orderNo}/{orderStatus}")
    public void updateOrderStatusPayed(@PathVariable String orderNo,
                                       @PathVariable Integer orderStatus){
        orderInfoService.updateOrderStatusPayed(orderNo, orderStatus);
    }

}
