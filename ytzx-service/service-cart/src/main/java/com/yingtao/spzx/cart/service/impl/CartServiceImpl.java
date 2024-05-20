package com.yingtao.spzx.cart.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.yingtao.spzx.cart.service.CartService;
import com.yingtao.ytzx.feign.product.ProductFeignClient;
import com.yingtao.ytzx.model.entity.h5.CartInfo;
import com.yingtao.ytzx.model.entity.product.ProductSku;
import com.yingtao.ytzx.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Adam
 * @create 2024-05-15 22:04
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ProductFeignClient productFeignClient;


    public String getCartKey(Long userId) {
        return "user:cart" + userId;
    }

    @Override
    public void addToCart(Long skuId, Integer skuNum) {
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);

        Object cartInfoObj = stringRedisTemplate.opsForHash().get(cartKey, String.valueOf(skuId));
        CartInfo cartInfo = null;
        if (cartInfoObj != null) {
            cartInfo = JSON.parseObject(cartInfoObj.toString(), CartInfo.class);
            cartInfo.setSkuNum(cartInfo.getSkuNum() + skuNum);
            cartInfo.setUpdateTime(new Date());
        } else {
            cartInfo = new CartInfo();

            ProductSku productSku = productFeignClient.getBySkuId(skuId);
            cartInfo.setUpdateTime(new Date());
            cartInfo.setCartPrice(productSku.getSalePrice());
            cartInfo.setSkuId(skuId);
            cartInfo.setSkuNum(skuNum);
            cartInfo.setImgUrl(productSku.getThumbImg());
            cartInfo.setIsChecked(1);
            cartInfo.setSkuName(productSku.getSkuName());
            cartInfo.setUserId(userId);
            cartInfo.setCreateTime(new Date());
        }
        stringRedisTemplate.opsForHash().put(cartKey, String.valueOf(skuId), JSON.toJSONString(cartInfo));
    }

    @Override
    public List<CartInfo> getCartList() {
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);
        List<Object> cartInfoObjList = stringRedisTemplate.opsForHash().values(cartKey);
        if (cartInfoObjList != null) {
            List<CartInfo> cartInfoList = cartInfoObjList.stream().map(x -> JSON
                            .parseObject(x.toString(), CartInfo.class)).
                    sorted((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime())).collect(Collectors.toList());
            return cartInfoList;
        }
        return new ArrayList<>();
    }

    @Override
    public void deleteCart(Long skuId) {
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);
        stringRedisTemplate.opsForHash().delete(cartKey, String.valueOf(userId));
    }

    @Override
    public void checkCart(long skuId, Integer isChecked) {
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);
        Boolean hasKey = stringRedisTemplate.opsForHash().hasKey(cartKey, String.valueOf(skuId));
        if (hasKey) {
            Object cartInfoObj = stringRedisTemplate.opsForHash().get(cartKey, String.valueOf(skuId));
            CartInfo cartInfo = JSON.parseObject(cartInfoObj.toString(), CartInfo.class);
            cartInfo.setIsChecked(isChecked);
            stringRedisTemplate.opsForHash().put(cartKey, String.valueOf(skuId), JSONObject.toJSONString(cartInfo));
        }
    }

    @Override
    public void allCheckCart(Integer isChecked) {
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);
        List<Object> cartInfoObjList = stringRedisTemplate.opsForHash().values(cartKey);
        if (CollectionUtil.isNotEmpty(cartInfoObjList)) {
            cartInfoObjList.stream().map(x -> {
                CartInfo cartInfo = JSON.parseObject(x.toString(), CartInfo.class);
                cartInfo.setIsChecked(isChecked);
                return cartInfo;
            }).forEach(x -> stringRedisTemplate.opsForHash().put(cartKey, String.valueOf(x.getSkuId()), JSON.toJSONString(x)));
        }
    }

    @Override
    public void clearCart() {
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);
        stringRedisTemplate.delete(cartKey);
    }

    @Override
    public List<CartInfo> getAllChecked() {
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);
        List<Object> cartInfoObjList = stringRedisTemplate.opsForHash().values(cartKey);
        if (CollectionUtil.isNotEmpty(cartInfoObjList)) {
            List<CartInfo> cartInfoList = cartInfoObjList.stream().map(x -> JSON.parseObject(x.toString(), CartInfo.class))
                    .filter(cartinfo -> cartinfo.getIsChecked() == 1).collect(Collectors.toList());
            return cartInfoList;
        }
        return new ArrayList<>();
    }

    @Override
    public void deleteChecked() {
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);
        List<Object> cartInfoObjList = stringRedisTemplate.opsForHash().values(cartKey);
        if (CollectionUtil.isNotEmpty(cartInfoObjList)) {
            cartInfoObjList.stream().map(x -> JSON.parseObject(x.toString(), CartInfo.class))
                    .filter(cartInfo -> cartInfo.getIsChecked() == 1)
                    .forEach(cartInfo -> stringRedisTemplate.opsForHash().delete(cartKey, String.valueOf(cartInfo.getSkuId())));
        }
    }
}
