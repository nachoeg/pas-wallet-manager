package com.archpatterns.walletmanager.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DataQueue {
	private Long cartItemId;
	private Long productId;
	private Integer quantity;
	private Double priceTotal;
	private String productName;
	private Long buyerId;
	private Long sellerId;
	private Long walletId;
}
