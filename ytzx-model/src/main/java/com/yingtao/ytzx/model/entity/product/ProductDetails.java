package com.yingtao.ytzx.model.entity.product;

import com.yingtao.ytzx.model.entity.base.BaseEntity;
import lombok.Data;

@Data
public class ProductDetails extends BaseEntity {

	private Long productId;
	private String imageUrls;

}