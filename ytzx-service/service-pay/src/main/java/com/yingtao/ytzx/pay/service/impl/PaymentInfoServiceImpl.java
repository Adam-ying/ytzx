package com.yingtao.ytzx.pay.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yingtao.ytzx.feign.order.OrderFeignClient;
import com.yingtao.ytzx.feign.product.ProductFeignClient;
import com.yingtao.ytzx.model.dto.product.SkuSaleDto;
import com.yingtao.ytzx.model.entity.order.OrderInfo;
import com.yingtao.ytzx.model.entity.order.OrderItem;
import com.yingtao.ytzx.model.entity.pay.PaymentInfo;
import com.yingtao.ytzx.pay.mapper.PaymentInfoMapper;
import com.yingtao.ytzx.pay.service.PaymentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Adam
 * @create 2024-05-18 21:29
 */
@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {

    @Autowired
    private PaymentInfoMapper paymentInfoMapper;

    @Autowired
    private OrderFeignClient orderFeignClient;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Override
    public PaymentInfo savePaymentInfo(String orderNo) {
        PaymentInfo paymentInfo = paymentInfoMapper.selectOne(new QueryWrapper<PaymentInfo>().eq("order_no", orderNo));
        if(paymentInfo == null){
            OrderInfo orderInfo = orderFeignClient.getOrderInfoByOrderNo(orderNo);
            paymentInfo = new PaymentInfo();
            paymentInfo.setUserId(orderInfo.getUserId());
            paymentInfo.setPayType(orderInfo.getPayType());
            String content = "";
            for(OrderItem item : orderInfo.getOrderItemList()) {
                content += item.getSkuName() + " ";
            }
            paymentInfo.setContent(content);
            paymentInfo.setAmount(orderInfo.getTotalAmount());
            paymentInfo.setOrderNo(orderNo);
            paymentInfo.setPaymentStatus(0);
            paymentInfoMapper.insert(paymentInfo);
        }
        return paymentInfo;
    }

    @Override
    public void updatePaymentStatus(Map<String, String> map, int i) {
        PaymentInfo paymentInfo = paymentInfoMapper.selectOne(new QueryWrapper<PaymentInfo>()
                .eq("order_no", map.get("out_trade_no")));
        if(paymentInfo.getPaymentStatus() == 1){
            return;
        }
        //更新支付信息
        paymentInfo.setPaymentStatus(1);
        paymentInfo.setOutTradeNo(map.get("trade_no"));
        paymentInfo.setCallbackTime(new Date());
        paymentInfo.setCallbackContent(JSON.toJSONString(map));
        paymentInfoMapper.updateById(paymentInfo);

        //更新订单支付状态
        orderFeignClient.updateOrderStatusPayed(paymentInfo.getOrderNo(), i);

        //更新商品销量
        OrderInfo orderInfo = orderFeignClient.getOrderInfoByOrderNo(paymentInfo.getOrderNo());
        List<SkuSaleDto> skuSaleDtoList = orderInfo.getOrderItemList().stream().map(item -> {
            SkuSaleDto skuSaleDto = new SkuSaleDto();
            skuSaleDto.setSkuId(item.getSkuId());
            skuSaleDto.setNum(item.getSkuNum());
            return skuSaleDto;
        }).collect(Collectors.toList());


        productFeignClient.updateSkuSaleNum(skuSaleDtoList);
    }
}
