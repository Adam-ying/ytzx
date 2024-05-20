package com.yingtao.spzx.cart.service;

import com.yingtao.ytzx.model.entity.h5.CartInfo;

import java.util.List;

/**
 * @author Adam
 * @create 2024-05-15 22:04
 */
public interface CartService {
    void addToCart(Long skuId, Integer skuNum);

    List<CartInfo> getCartList();

    void deleteCart(Long skuId);

    void checkCart(long skuId, Integer isChecked);

    void allCheckCart(Integer isChecked);

    void clearCart();

    List<CartInfo> getAllChecked();

    void deleteChecked();
}
