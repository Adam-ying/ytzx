package com.yingtao.ytzx.pay.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.yingtao.ytzx.model.vo.common.Result;
import com.yingtao.ytzx.model.vo.common.ResultCodeEnum;
import com.yingtao.ytzx.pay.properties.AlipayProperties;
import com.yingtao.ytzx.pay.service.AlipayService;
import com.yingtao.ytzx.pay.service.PaymentInfoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Adam
 * @create 2024-05-18 21:54
 */
@Slf4j
@Controller
@RequestMapping("/api/order/alipay")
public class AlipayController {

    @Autowired
    private AlipayService alipayService;

    @Autowired
    private AlipayProperties alipayProperties;

    @Autowired
    private PaymentInfoService paymentInfoService;

    @GetMapping("submitAlipay/{orderNo}")
    @ResponseBody
    public Result submitAlipay(@PathVariable String orderNo){
        String form = alipayService.submitAlipay(orderNo);
        return Result.build(form, ResultCodeEnum.SUCCESS);
    }

    @RequestMapping("callback/notify")
    @ResponseBody
    public String alipayNotify(@RequestParam Map<String, String> paramMap, HttpServletRequest request){
        log.info("AlipayController...alipayNotify方法执行了...");
        boolean signVerified = false;
        try {
            AlipaySignature.rsaCheckV1(paramMap,
                    alipayProperties.getAlipayPublicKey(), AlipayProperties.charset, AlipayProperties.sign_type)
        }catch (AlipayApiException e){
            e.printStackTrace();
        }
        String tradeStatus = paramMap.get("trade_status");
        if(signVerified){
            if("TRADE_SUCCESS".equals(tradeStatus) || "TRADE_FINISHED".equals(tradeStatus)){
                paymentInfoService.updatePaymentStatus(paramMap, 2);
                return "success";
            }
        }else{
            return "failure";
        }
        return "failure";
    }
}
