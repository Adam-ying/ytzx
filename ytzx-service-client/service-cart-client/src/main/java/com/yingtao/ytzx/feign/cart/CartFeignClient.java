package com.yingtao.ytzx.feign.cart;

import com.yingtao.ytzx.model.entity.h5.CartInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author Adam
 * @create 2024-05-17 20:07
 */
@FeignClient(value = "cart-service")
public interface CartFeignClient {
    @GetMapping("/api/order/cart/auth/getAllCkecked")
    public abstract List<CartInfo> getAllChecked();

    @GetMapping("/api/order/cart/auth/deleteChecked")
    public void deleteChecked();
}
