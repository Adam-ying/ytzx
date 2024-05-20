package com.yingtao.ytzx.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yingtao.ytzx.model.entity.base.Region;
import com.yingtao.ytzx.model.entity.user.UserAddress;
import com.yingtao.ytzx.user.mapper.UserAddressMapper;
import com.yingtao.ytzx.user.mapper.UserRegionMapper;
import com.yingtao.ytzx.user.service.UserAddressService;
import com.yingtao.ytzx.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Adam
 * @create 2024-04-29 19:52
 */
@Service
public class UserAddressServiceImpl implements UserAddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Autowired
    private UserRegionMapper userRegionMapper;

    @Override
    public void updateById(UserAddress userAddress) {

        String provinceName = userRegionMapper.selectOne(new QueryWrapper<Region>()
                .eq("code", userAddress.getProvinceCode())).getName();

        String cityName = userRegionMapper.selectOne(new QueryWrapper<Region>()
                .eq("code", userAddress.getCityCode())).getName();

        String regionName = userRegionMapper.selectOne(new QueryWrapper<Region>()
                .eq("code", userAddress.getDistrictCode())).getName();

        StringBuilder full_path = new StringBuilder();

        String full_address_join = full_path.append(provinceName).append(cityName).append(regionName).toString();

        userAddress.setFullAddress(full_address_join);

        userAddressMapper.updateById(userAddress);
    }

    @Override
    public void save(UserAddress userAddress) {
        String provinceName = userRegionMapper.selectOne(new QueryWrapper<Region>()
                .eq("code", userAddress.getProvinceCode())).getName();

        String cityName = userRegionMapper.selectOne(new QueryWrapper<Region>()
                .eq("code", userAddress.getCityCode())).getName();

        String regionName = userRegionMapper.selectOne(new QueryWrapper<Region>()
                .eq("code", userAddress.getDistrictCode())).getName();

        StringBuilder full_path = new StringBuilder();

        String full_address_join = full_path.append(provinceName).append(cityName).append(regionName).toString();

        userAddress.setFullAddress(full_address_join);

        userAddress.setUserId(AuthContextUtil.getUserInfo().getId());

        userAddressMapper.insert(userAddress);
    }

    @Override
    public List<UserAddress> findUserAddressList() {
        List<UserAddress> userAddressList = userAddressMapper.selectList(new QueryWrapper<UserAddress>()
                .eq("user_id", AuthContextUtil.getUserInfo().getId()));
        return userAddressList;
    }

    @Override
    public void removeById(Long id) {
        UserAddress userAddress = userAddressMapper.selectById(id);
        userAddress.setIsDeleted(1);
        userAddress.setUpdateTime(new Date());
        userAddressMapper.updateById(userAddress);
    }

    @Override
    public UserAddress getUserAddressById(Long id) {
        UserAddress userAddress = userAddressMapper.selectById(id);
        return userAddress;
    }
}
