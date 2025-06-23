package com.archpatterns.walletmanager.models;

import java.time.LocalDateTime;

import com.archpatterns.walletmanager.enums.OperationType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "OPERATIONS")
public class Operation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Default
	private Double amount = 0.0;

	@Default
	private LocalDateTime timestamp = LocalDateTime.now();

	@Column(nullable = false)
	private OperationType type;

}
