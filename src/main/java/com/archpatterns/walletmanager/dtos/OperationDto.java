package com.archpatterns.walletmanager.dtos;

import java.time.LocalDateTime;

import com.archpatterns.walletmanager.enums.OperationType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OperationDto {
	private Double amount;
	private LocalDateTime timestamp;
	private OperationType type;
}
