package com.yingtao.spzx.cart.controller;

import com.yingtao.spzx.cart.service.CartService;
import com.yingtao.ytzx.model.entity.h5.CartInfo;
import com.yingtao.ytzx.model.vo.common.Result;
import com.yingtao.ytzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Adam
 * @create 2024-05-15 22:03
 */
@RestController
@RequestMapping("/api/order/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("auth/addToCart/{skuId}/{skuNum}")
    public Result addToCart(@PathVariable Long skuId,
                            @PathVariable Integer skuNum){
        cartService.addToCart(skuId, skuNum);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("auth/cartList")
    public Result getCartList(){
        List<CartInfo> cartInfoList = cartService.getCartList();
        return Result.build(cartInfoList, ResultCodeEnum.SUCCESS);
    }

    @DeleteMapping("auth/deleteCart/{skuId}")
    public Result deleteCart(@PathVariable Long skuId){
        cartService.deleteCart(skuId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("auth/checkCart/{skuId}/{isChecked}")
    public Result checkCart(@PathVariable long skuId,
                            @PathVariable Integer isChecked){
        cartService.checkCart(skuId, isChecked);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("auth/allCheckCart/{isChecked}")
    public Result allCheckCart(@PathVariable Integer isChecked){
        cartService.allCheckCart(isChecked);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("auth/clearCart")
    public Result clearCart(){
        cartService.clearCart();
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/auth/getAllCkecked")
    public List<CartInfo> getAllChecked(){
        List<CartInfo> cartInfoList = cartService.getAllChecked();
        return cartInfoList;
    }

    @GetMapping("/auth/deleteChecked")
    public void deleteChecked(){
        cartService.deleteChecked();
    }
}
