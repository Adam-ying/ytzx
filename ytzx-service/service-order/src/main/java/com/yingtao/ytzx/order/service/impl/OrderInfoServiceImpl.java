package com.yingtao.ytzx.order.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yingtao.ytzx.common.exception.YtException;
import com.yingtao.ytzx.feign.cart.CartFeignClient;
import com.yingtao.ytzx.feign.product.ProductFeignClient;
import com.yingtao.ytzx.feign.user.UserFeignClient;
import com.yingtao.ytzx.model.dto.h5.OrderInfoDto;
import com.yingtao.ytzx.model.entity.h5.CartInfo;
import com.yingtao.ytzx.model.entity.order.OrderInfo;
import com.yingtao.ytzx.model.entity.order.OrderItem;
import com.yingtao.ytzx.model.entity.order.OrderLog;
import com.yingtao.ytzx.model.entity.product.ProductSku;
import com.yingtao.ytzx.model.entity.user.UserAddress;
import com.yingtao.ytzx.model.entity.user.UserInfo;
import com.yingtao.ytzx.model.vo.common.ResultCodeEnum;
import com.yingtao.ytzx.model.vo.h5.TradeVo;
import com.yingtao.ytzx.order.mapper.OrderInfoMapper;
import com.yingtao.ytzx.order.mapper.OrderItemMapper;
import com.yingtao.ytzx.order.mapper.OrderLogMapper;
import com.yingtao.ytzx.order.service.OrderInfoService;
import com.yingtao.ytzx.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Adam
 * @create 2024-05-17 20:18
 */
@Service
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private CartFeignClient cartFeignClient;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private OrderLogMapper orderLogMapper;

    @Override
    public TradeVo trade() {

        List<CartInfo> cartInfoList = cartFeignClient.getAllChecked();
        List<OrderItem> orderItemList = new ArrayList<>();
        BigDecimal totalAmount = new BigDecimal(0);

        for(CartInfo cartInfo: cartInfoList){
            OrderItem orderItem = new OrderItem();
            orderItem.setSkuId(cartInfo.getSkuId());
            orderItem.setSkuNum(cartInfo.getSkuNum());
            orderItem.setSkuPrice(cartInfo.getCartPrice());
            orderItem.setThumbImg(cartInfo.getImgUrl());
            orderItem.setSkuName(cartInfo.getSkuName());
            orderItemList.add(orderItem);
            totalAmount.add(cartInfo.getCartPrice().multiply(new BigDecimal(cartInfo.getSkuNum())));
        }

        TradeVo tradeVo = new TradeVo();
        tradeVo.setTotalAmount(totalAmount);
        tradeVo.setOrderItemList(orderItemList);
        return tradeVo;
    }

    @Override
    public Long submitOrder(OrderInfoDto orderInfoDto) {
        List<OrderItem> orderItemList = orderInfoDto.getOrderItemList();
        if(CollectionUtil.isEmpty(orderItemList)){
            throw new YtException(ResultCodeEnum.DATA_ERROR);
        }
        for(OrderItem orderItem: orderItemList){
            ProductSku productSku = productFeignClient.getBySkuId(orderItem.getSkuId());
            if(productSku == null){
                throw new YtException(ResultCodeEnum.DATA_ERROR);
            }
            if(productSku.getStockNum().intValue() < orderItem.getSkuNum().intValue()){
                throw new YtException(ResultCodeEnum.STOCK_LESS);
            }
        }
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderNo(String.valueOf(System.currentTimeMillis()));
        //用户id
        orderInfo.setUserId(userInfo.getId());
        //用户昵称
        orderInfo.setNickName(userInfo.getNickName());
        //用户收货地址信息
        UserAddress userAddress = userFeignClient.getUserAddress(orderInfoDto.getUserAddressId());
        orderInfo.setReceiverName(userAddress.getName());
        orderInfo.setReceiverPhone(userAddress.getPhone());
        orderInfo.setReceiverTagName(userAddress.getTagName());
        orderInfo.setReceiverProvince(userAddress.getProvinceCode());
        orderInfo.setReceiverCity(userAddress.getCityCode());
        orderInfo.setReceiverDistrict(userAddress.getDistrictCode());
        orderInfo.setReceiverAddress(userAddress.getFullAddress());

        BigDecimal totalAmount = new BigDecimal(0);
        for (OrderItem orderItem : orderItemList) {
            totalAmount = totalAmount.add(orderItem.getSkuPrice().multiply(new BigDecimal(orderItem.getSkuNum())));
        }
        orderInfo.setTotalAmount(totalAmount);
        orderInfo.setCouponAmount(new BigDecimal(0));
        orderInfo.setOriginalTotalAmount(totalAmount);
        orderInfo.setFeightFee(orderInfoDto.getFeightFee());
        orderInfo.setPayType(2);
        orderInfo.setOrderStatus(0);
        orderInfoMapper.insert(orderInfo);

        //保存订单明细
        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrderId(orderInfo.getId());
            orderItemMapper.insert(orderItem);
        }

        //记录日志
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(orderInfo.getId());
        orderLog.setProcessStatus(0);
        orderLog.setNote("提交订单");
        orderLogMapper.insert(orderLog);

        // TODO 远程调用service-cart微服务接口清空购物车数据
        cartFeignClient.deleteChecked();

        return orderInfo.getId();
    }

    @Override
    public OrderInfo getOrderInfoById(Long orderId) {
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
        return orderInfo;
    }

    @Override
    public TradeVo buyNow(Long skuId) {
        ProductSku productSku = productFeignClient.getBySkuId(skuId);
        TradeVo tradeVo = new TradeVo();
        tradeVo.setTotalAmount(productSku.getSalePrice());
        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setSkuName(productSku.getSkuName());
        orderItem.setSkuNum(1);
        orderItem.setSkuPrice(productSku.getSalePrice());
        orderItem.setThumbImg(productSku.getThumbImg());
        orderItem.setSkuId(skuId);
        orderItems.add(orderItem);
        tradeVo.setOrderItemList(orderItems);
        return tradeVo;
    }

    @Override
    public PageInfo getByPage(Integer page, Integer limit, Integer orderStatus) {
        PageHelper.startPage(page, limit);
        List<OrderInfo> orderInfoList = orderInfoMapper.selectList(new QueryWrapper<OrderInfo>().
                eq("user_id", AuthContextUtil.getUserInfo().getId()).eq("order_status", orderStatus)
                .eq("is_deleted", 0));
        for (OrderInfo orderInfo : orderInfoList) {
            List<OrderItem> orderItemList = orderItemMapper.selectList(new QueryWrapper<OrderItem>().eq("order_id", orderInfo.getId()));
            orderInfo.setOrderItemList(orderItemList);
        }
        PageInfo<OrderInfo> orderInfoPageInfo = new PageInfo<>(orderInfoList);
        return orderInfoPageInfo;
    }

    @Override
    public OrderInfo getOrderInfoByOrderNo(String orderNo) {
        OrderInfo orderInfo = orderInfoMapper.selectOne(new QueryWrapper<OrderInfo>().eq("order_no", orderNo));
        List<OrderItem> orderItemList = orderItemMapper.selectList(new QueryWrapper<OrderItem>().eq("order_id", orderInfo.getId()));
        orderInfo.setOrderItemList(orderItemList);
        return orderInfo;
    }

    @Override
    public void updateOrderStatusPayed(String orderNo, Integer orderStatus) {
        OrderInfo orderInfo = orderInfoMapper.selectOne(new QueryWrapper<OrderInfo>()
                .eq("order_no", orderNo));
        orderInfo.setPayType(orderStatus);
        orderInfo.setPaymentTime(new Date());
        orderInfoMapper.updateById(orderInfo);

        OrderLog orderLog = new OrderLog();
        orderLog.setProcessStatus(1);
        orderLog.setOrderId(orderInfo.getId());
        orderLog.setNote("支付宝支付成功");
        orderLogMapper.insert(orderLog);


    }
}
