package com.yingtao.ytzx.user.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.yingtao.ytzx.user.service.SmsService;
import com.yingtao.ytzx.utils.HttpUtils;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Adam
 * @create 2024-04-27 19:48
 */
@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void sendValidateCode(String phone) {
        String code = redisTemplate.opsForValue().get("phone:code:" + phone);
        if(!StrUtil.isEmpty(code)){
            return;
        }
        code = RandomUtil.randomNumbers(4);
        redisTemplate.opsForValue().set("phone:code:" + phone, code, 5, TimeUnit.MINUTES);
        sendSms(phone, code);
    }

    private void sendSms(String phone, String code) {
        String host = "https://dfsns.market.alicloudapi.com";
        String path = "/data/send_sms";
        String method = "POST";
        String appcode = "6f14362f30294151aff8b921c7d696c8";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("content","code:"+code);
        bodys.put("template_id", "CST_ptdie100");  //注意，CST_ptdie100该模板ID仅为调试使用，调试结果为"status": "OK" ，即表示接口调用成功，然后联系客服报备自己的专属签名模板ID，以保证短信稳定下发
        bodys.put("phone_number", phone);


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
