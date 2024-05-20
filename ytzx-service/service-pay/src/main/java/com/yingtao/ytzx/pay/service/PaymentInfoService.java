package com.yingtao.ytzx.pay.service;

import com.yingtao.ytzx.model.entity.pay.PaymentInfo;

import java.util.Map;

/**
 * @author Adam
 * @create 2024-05-18 21:29
 */
public interface PaymentInfoService {
    PaymentInfo savePaymentInfo(String orderNo);

    void updatePaymentStatus(Map<String, String> paramMap, int i);
}
