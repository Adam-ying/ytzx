package com.yingtao.ytzx.feign.order;

import com.yingtao.ytzx.model.entity.order.OrderInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Adam
 * @create 2024-05-18 21:41
 */
@FeignClient(value = "service-order")
public interface OrderFeignClient {
    @GetMapping("/api/order/orderInfo/auth/getOrderInfoByOrderNo/{orderNo}")
    public OrderInfo getOrderInfoByOrderNo(@PathVariable String orderNo);

    @GetMapping("/api/order/orderInfo/auth/updateOrderStatusPayed/{orderNo}/{orderStatus}")
    public void updateOrderStatusPayed(@PathVariable String orderNo,
                                       @PathVariable Integer orderStatus);
}
