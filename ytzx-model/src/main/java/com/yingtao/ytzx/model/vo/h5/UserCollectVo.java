package com.yingtao.ytzx.model.vo.h5;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Adam
 * @create 2024-05-12 20:47
 */
@Data
public class UserCollectVo {
    private Integer skuId;

    private String skuName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "缩略图路径")
    private String thumbImg;

    @Schema(description = "售价")
    private BigDecimal salePrice;
}
