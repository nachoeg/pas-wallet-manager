package com.archpatterns.walletmanager.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.archpatterns.walletmanager.enums.OperationType;
import com.archpatterns.walletmanager.exceptions.InsufficientFundsException;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "WALLETS")
public class Wallet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private Long userId;

	@Column(nullable = false)
	@Default
	private Double balance = 0.0;

	@Column(nullable = false)
	@Default
	private LocalDateTime lastUpdate = LocalDateTime.now();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@Default
	private List<Operation> operations = new ArrayList<>();

	public void deposit(Double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("Deposit amount must be positive");
		}
		this.balance += amount;
		this.operations.add(Operation.builder().amount(amount).type(OperationType.DEPOSIT).build());
		this.lastUpdate = LocalDateTime.now();
	}

	public void withdraw(Double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("Withdrawal amount must be positive");
		}
		if (amount > this.balance) {
			throw new InsufficientFundsException("Insufficient balance for withdrawal");
		}
		this.balance -= amount;
		this.operations.add(Operation.builder().amount(amount).type(OperationType.WITHDRAWAL).build());
		this.lastUpdate = LocalDateTime.now();
	}

}
