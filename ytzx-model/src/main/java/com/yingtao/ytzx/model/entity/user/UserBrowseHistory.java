package com.yingtao.ytzx.model.entity.user;

import com.yingtao.ytzx.model.entity.base.BaseEntity;
import lombok.Data;

/**
 * @author Adam
 * @create 2024-05-12 22:13
 */
@Data
public class UserBrowseHistory extends BaseEntity {

    private Long userId;

    private Long skuId;
}
