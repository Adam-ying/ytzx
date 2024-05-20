package com.yingtao.ytzx.pay.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.yingtao.ytzx.common.exception.YtException;
import com.yingtao.ytzx.model.entity.pay.PaymentInfo;
import com.yingtao.ytzx.model.vo.common.ResultCodeEnum;
import com.yingtao.ytzx.pay.properties.AlipayProperties;
import com.yingtao.ytzx.pay.service.AlipayService;
import com.yingtao.ytzx.pay.service.PaymentInfoService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author Adam
 * @create 2024-05-18 21:57
 */
@Slf4j
@Service
public class AlipayServiceImpl implements AlipayService {

    @Autowired
    private PaymentInfoService paymentInfoService;

    @Autowired
    private AlipayClient alipayClient;

    @Autowired
    private AlipayProperties alipayProperties;

    @SneakyThrows
    @Override
    public String submitAlipay(String orderNo) {
        PaymentInfo paymentInfo = paymentInfoService.savePaymentInfo(orderNo);

        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();

        alipayRequest.setNotifyUrl(alipayProperties.getNotifyPaymentUrl());

        alipayRequest.setReturnUrl(alipayProperties.getReturnPaymentUrl());

        HashMap<String, Object> map = new HashMap<>();
        map.put("out_trade_no", paymentInfo.getOrderNo());
        map.put("product_code", "QUICK_WAP_WAY");
        map.put("total_amount", paymentInfo.getAmount());
        map.put("subject",paymentInfo.getContent());
        alipayRequest.setBizContent(JSON.toJSONString(map));

        AlipayTradeWapPayResponse response = alipayClient.pageExecute(alipayRequest);
        if(response.isSuccess()){
            log.info("调用成功");
            return response.getBody();
        } else {
            log.info("调用失败");
            throw new YtException(ResultCodeEnum.DATA_ERROR);
        }

    }
}
