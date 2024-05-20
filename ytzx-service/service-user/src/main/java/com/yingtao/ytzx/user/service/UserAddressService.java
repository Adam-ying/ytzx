package com.yingtao.ytzx.user.service;

import com.yingtao.ytzx.model.entity.user.UserAddress;

import java.util.List;

/**
 * @author Adam
 * @create 2024-04-29 19:52
 */
public interface UserAddressService {
    void updateById(UserAddress userAddress);

    void save(UserAddress userAddress);

    List<UserAddress> findUserAddressList();

    void removeById(Long id);

    UserAddress getUserAddressById(Long id);
}
