package com.yingtao.ytzx.user.controller;

import com.yingtao.ytzx.model.entity.user.UserAddress;
import com.yingtao.ytzx.model.vo.common.Result;
import com.yingtao.ytzx.model.vo.common.ResultCodeEnum;
import com.yingtao.ytzx.user.mapper.UserAddressMapper;
import com.yingtao.ytzx.user.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Adam
 * @create 2024-04-29 19:50
 */
@RestController
@RequestMapping("/api/user/userAddress")
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;

    @PutMapping("auth/updateById")
    public Result updateById(@RequestBody UserAddress userAddress){
        userAddressService.updateById(userAddress);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("auth/save")
    public Result save(@RequestBody UserAddress userAddress){
        userAddressService.save(userAddress);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("auth/findUserAddressList")
    public Result findUserAddressList(){
        List<UserAddress> userAddressList = userAddressService.findUserAddressList();
        return Result.build(userAddressList, ResultCodeEnum.SUCCESS);
    }

    @DeleteMapping("auth/removeById/{id}")
    public Result removeById(@PathVariable Long id){
        userAddressService.removeById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("getUserAddress/{id}")
    public UserAddress getUserAddress(@PathVariable Long id){
        UserAddress userAddress = userAddressService.getUserAddressById(id);
        return userAddress;
    }
}
