package com.yingtao.ytzx.user.controller;

import com.yingtao.ytzx.model.vo.common.Result;
import com.yingtao.ytzx.model.vo.common.ResultCodeEnum;
import com.yingtao.ytzx.user.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Adam
 * @create 2024-04-27 19:46
 */
@RestController
@RequestMapping("/api/user/sms")
public class SmsController {

    @Autowired
    private SmsService smsService;


    @GetMapping("sendCode/{phone}")
    public Result sendValidateCode(@PathVariable String phone){
        smsService.sendValidateCode(phone);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
