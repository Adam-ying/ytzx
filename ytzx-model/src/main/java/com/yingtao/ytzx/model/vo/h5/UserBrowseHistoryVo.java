package com.yingtao.ytzx.model.vo.h5;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Adam
 * @create 2024-05-12 21:20
 */
@Data
public class UserBrowseHistoryVo {
    private Integer skuId;

    private String skuName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "更新时间")
    private Date updateTime;

    @Schema(description = "缩略图路径")
    private String thumbImg;

    @Schema(description = "售价")
    private BigDecimal salePrice;
}
