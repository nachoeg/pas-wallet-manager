package com.archpatterns.walletmanager.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WalletDto {
	private Long wallet_id;
	private Double balance;
}