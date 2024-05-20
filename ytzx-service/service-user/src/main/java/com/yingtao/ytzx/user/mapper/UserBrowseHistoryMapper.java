package com.yingtao.ytzx.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yingtao.ytzx.model.entity.user.UserBrowseHistory;
import com.yingtao.ytzx.model.vo.h5.UserBrowseHistoryVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Adam
 * @create 2024-05-12 21:25
 */
@Mapper
public interface UserBrowseHistoryMapper extends BaseMapper<UserBrowseHistory> {

}
