package com.yingtao.ytzx.feign.user;

import com.yingtao.ytzx.model.entity.user.UserAddress;
import com.yingtao.ytzx.model.vo.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Adam
 * @create 2024-05-17 21:42
 */
@FeignClient(value = "service-user")
public interface UserFeignClient {

    @GetMapping("/api/user/userAddress/getUserAddress/{id}")
    public UserAddress getUserAddress(@PathVariable Long id);


}
