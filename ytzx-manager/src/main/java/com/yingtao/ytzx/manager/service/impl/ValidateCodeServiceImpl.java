package com.yingtao.ytzx.manager.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.yingtao.ytzx.manager.service.ValidateCodeService;
import com.yingtao.ytzx.model.vo.system.ValidateCodeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Adam
 * @create 2024-04-13 20:44
 */
@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public ValidateCodeVo generateValidateCode() {
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(150, 48, 4, 20);
        String codeValue = circleCaptcha.getCode();
        String imageBase64 = circleCaptcha.getImageBase64();

        String codeKey = UUID.randomUUID().toString().replace("-", "");

        redisTemplate.opsForValue().set("user:login:validatecode:" + codeKey,
                codeValue, 5, TimeUnit.MINUTES);

        ValidateCodeVo validateCodeVo = new ValidateCodeVo();
        validateCodeVo.setCodeKey(codeKey);
        validateCodeVo.setCodeValue("data:image/png;base64," + imageBase64);

        return validateCodeVo;
    }
}
