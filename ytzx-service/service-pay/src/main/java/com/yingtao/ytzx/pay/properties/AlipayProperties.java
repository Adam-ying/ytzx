package com.yingtao.ytzx.pay.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Adam
 * @create 2024-05-18 21:51
 */
@Data
@ConfigurationProperties(prefix = "ytzx.alipay")
public class AlipayProperties {
    private String alipayUrl;
    private String appPrivateKey;
    public  String alipayPublicKey;
    private String appId;
    public  String returnPaymentUrl;
    public  String notifyPaymentUrl;

    public final static String format="json";
    public final static String charset="utf-8";
    public final static String sign_type="RSA2";
}
