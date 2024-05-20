package com.yingtao.ytzx.manager.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yingtao.ytzx.common.exception.YtException;
import com.yingtao.ytzx.manager.helper.MenuHelper;
import com.yingtao.ytzx.manager.mapper.SysMenuMapper;
import com.yingtao.ytzx.manager.mapper.SysRoleMenuMapper;
import com.yingtao.ytzx.manager.mapper.SysUserMapper;
import com.yingtao.ytzx.manager.service.SysUserService;
import com.yingtao.ytzx.model.dto.system.LoginDto;
import com.yingtao.ytzx.model.dto.system.SysUserDto;
import com.yingtao.ytzx.model.entity.system.SysMenu;
import com.yingtao.ytzx.model.entity.system.SysRole;
import com.yingtao.ytzx.model.entity.system.SysUser;
import com.yingtao.ytzx.model.vo.common.ResultCodeEnum;
import com.yingtao.ytzx.model.vo.system.LoginVo;
import com.yingtao.ytzx.model.vo.system.SysMenuVo;
import com.yingtao.ytzx.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Adam
 * @create 2024-04-13 19:25
 */
@Service
public class SysUserServiceImpl implements SysUserService {


    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public LoginVo login(LoginDto loginDto) {

        String captcha = loginDto.getCaptcha();
        String codeKey = loginDto.getCodeKey();

        String redisData = redisTemplate.opsForValue().get("user:login:validatecode:" + codeKey);
        if(StrUtil.isBlank(redisData) || !StrUtil.equalsIgnoreCase(redisData, captcha)){
            throw new YtException(ResultCodeEnum.VALIDATECODE_ERROR);
        }

        redisTemplate.delete("user:login:validatecode:" + codeKey)

        SysUser sysUser = sysUserMapper.selectByUserName(loginDto.getUserName());
        if(sysUser == null){
//            throw new RuntimeException("用户名或密码错误");
            throw new YtException(ResultCodeEnum.LOGIN_ERROR);
        }
        String inputPassword = loginDto.getPassword();
        String md5InputPassword = DigestUtils.md5DigestAsHex(inputPassword.getBytes());
        if(!md5InputPassword.equals(sysUser.getPassword())){
//            throw new RuntimeException("用户名或密码错误");
            throw new YtException(ResultCodeEnum.LOGIN_ERROR);
        }


        String token = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set("user:login:" + token,
                JSON.toJSONString(sysUser), 30, TimeUnit.MINUTES);


        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        loginVo.setRefresh_token("");
        return loginVo;
    }

    @Override
    public SysUser getUserInfo(String token) {
        String userJson = redisTemplate.opsForValue().get("user:login:" + token);
        return JSON.parseObject(userJson, SysUser.class);
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete("user:login:" + token);
    }

    @Override
    public PageInfo<SysRole> findByPage(Integer pageNum, Integer pageSize, SysUserDto sysUserDto) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysRole> sysRoleList = sysUserMapper.findByPage(sysUserDto);
        PageInfo sysRolePageInfo = new PageInfo(sysRoleList);
        return sysRolePageInfo;
    }

    @Override
    public void saveSysUser(SysUser sysUser) {
        sysUserMapper.saveSysUser(sysUser);
    }

    @Override
    public void updateSysUser(SysUser sysUser) {
        sysUserMapper.updateSysUser(sysUser);
    }

    @Override
    public void deleteById(Long userId) {
        sysUserMapper.deleteById(userId);
    }

    @Override
    public List<SysMenuVo> findUserMenuList() {
        Long userId = AuthContextUtil.get().getId();
        List<SysMenu> sysMenuList = sysMenuMapper.selectListByUserId(userId);
        List<SysMenu> sysMenuList1 = new ArrayList<>();

        for(SysMenu sysMenu: sysMenuList){
            if(sysMenu.getId().longValue() == 0){
                SysMenu sysMenu1 = MenuHelper.buildTree(sysMenu, sysMenuList);
                sysMenuList1.add(sysMenu1);
            }
        }

        return this.buildTree(sysMenuList1);
    }

    private List<SysMenuVo> buildTree(List<SysMenu> sysMenuList) {
        List<SysMenuVo> sysMenuVoList = new ArrayList<>();
        for(SysMenu sysMenu: sysMenuList){
            SysMenuVo sysMenuVo = new SysMenuVo();
            sysMenuVo.setName(sysMenu.getComponent());
            sysMenuVo.setTitle(sysMenu.getTitle());
            if(!CollectionUtil.isEmpty(sysMenu.getChildren())){
                sysMenuVo.setChildren(buildTree(sysMenu.getChildren()));
            }
            sysMenuVoList.add(sysMenuVo);
        }
        return sysMenuVoList;
    }
}
