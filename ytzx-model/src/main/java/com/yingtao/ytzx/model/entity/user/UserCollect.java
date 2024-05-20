package com.yingtao.ytzx.model.entity.user;

import com.yingtao.ytzx.model.entity.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author Adam
 * @create 2024-04-29 20:52
 */
@Data
@Schema(description = "用户收藏实体类")
public class UserCollect extends BaseEntity {

    private Long userId;

    private Long skuId;
}
