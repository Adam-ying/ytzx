package com.yingtao.ytzx.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yingtao.ytzx.model.entity.user.UserCollect;
import com.yingtao.ytzx.model.vo.h5.UserCollectVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Adam
 * @create 2024-04-29 20:49
 */
@Mapper
public interface UserCollectMapper extends BaseMapper<UserCollect> {
}
