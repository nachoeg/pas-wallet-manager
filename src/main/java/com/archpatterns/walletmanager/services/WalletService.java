package com.archpatterns.walletmanager.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.archpatterns.walletmanager.dtos.DataQueue;
import com.archpatterns.walletmanager.dtos.Listdata;
import com.archpatterns.walletmanager.dtos.OperationDto;
import com.archpatterns.walletmanager.dtos.WalletDto;
import com.archpatterns.walletmanager.exceptions.WalletNotFoundException;
import com.archpatterns.walletmanager.models.Wallet;
import com.archpatterns.walletmanager.repositories.WalletRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WalletService {

	private final WalletRepository walletRepository;

	public WalletDto createWallet(Long userId) {
		if (userId == null || userId <= 0) {
			throw new IllegalArgumentException("Invalid user ID: " + userId);
		}
		if (walletRepository.existsByUserId(userId)) {
			throw new RuntimeException("Wallet already exists for user ID: " + userId);
		}
		return toDto(walletRepository.save(Wallet.builder().userId(userId).build()));
	}

	@Transactional
	public WalletDto deposit(Long userId, Double amount) {
		Wallet wallet = walletRepository.findByUserId(userId)
				.orElseThrow(() -> new WalletNotFoundException("Wallet not found for user ID: " + userId));
		wallet.deposit(amount);
		return toDto(walletRepository.save(wallet));
	}

	@Transactional
	public WalletDto withdraw(Long userId, Double amount) {
		Wallet wallet = walletRepository.findByUserId(userId)
				.orElseThrow(() -> new WalletNotFoundException("Wallet not found for user ID: " + userId));
		wallet.withdraw(amount);
		return toDto(walletRepository.save(wallet));
	}

	public WalletDto getWallet(Long userId) {
		Wallet wallet = walletRepository.findByUserId(userId)
				.orElseThrow(() -> new WalletNotFoundException("Wallet not found for user ID: " + userId));
		return toDto(wallet);
	}

	@Transactional
	public List<OperationDto> getHistory(Long userId) {
		Wallet wallet = walletRepository.findByUserId(userId)
				.orElseThrow(() -> new WalletNotFoundException("Wallet not found for user ID: " + userId));
		return wallet.getOperations().stream().map(op -> OperationDto.builder().amount(op.getAmount())
				.timestamp(op.getTimestamp()).type(op.getType()).build()).toList();
	}

	public boolean checkMoney(Listdata data) {

		if (data == null || data.getListData() == null || data.getListData().isEmpty()) {
			return false;
		}

		for (DataQueue item : data.getListData()) {
			Long userId = item.getBuyerId();
			Double priceTotal = item.getPriceTotal();

			if (userId == null || userId <= 0 || priceTotal == null || priceTotal <= 0) {
				return false;
			}

			Wallet wallet = walletRepository.findByUserId(userId)
					.orElseThrow(() -> new WalletNotFoundException("Wallet not found for user ID: " + userId));

			if (wallet.getBalance() < priceTotal) {
				return false;
			}
		}

		return true;
	}

	private WalletDto toDto(Wallet wallet) {
		return WalletDto.builder().wallet_id(wallet.getId()).balance(wallet.getBalance()).build();
	}

}
