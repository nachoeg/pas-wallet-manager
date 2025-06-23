package com.archpatterns.walletmanager.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.archpatterns.walletmanager.dtos.CreateWalletRequest;
import com.archpatterns.walletmanager.dtos.OperationDto;
import com.archpatterns.walletmanager.dtos.Response;
import com.archpatterns.walletmanager.dtos.TransactionRequest;
import com.archpatterns.walletmanager.dtos.WalletDto;
import com.archpatterns.walletmanager.services.WalletService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "wallet-manager/api", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class WalletController {

	private final WalletService walletService;

	@PostMapping("/create")
	public ResponseEntity<Response<WalletDto>> createWallet(@RequestBody CreateWalletRequest request) {
		WalletDto wallet = walletService.createWallet(request.getUserId());
		Response<WalletDto> response = new Response<WalletDto>("Wallet created successfully", wallet);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping("/{user_id}/balance")
	public ResponseEntity<Response<WalletDto>> balance(@PathVariable Long user_id) {
		WalletDto wallet = walletService.getWallet(user_id);
		Response<WalletDto> response = new Response<WalletDto>("Wallet balance retrieved successfully", wallet);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/{user_id}/deposit")
	public ResponseEntity<Response<WalletDto>> deposit(@PathVariable Long user_id,
			@RequestBody TransactionRequest request) {
		WalletDto wallet = walletService.deposit(user_id, request.getAmount());
		Response<WalletDto> response = new Response<WalletDto>("Deposit successful", wallet);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/{user_id}/withdraw")
	public ResponseEntity<Response<WalletDto>> withdraw(@PathVariable Long user_id,
			@RequestBody TransactionRequest request) {
		WalletDto wallet = walletService.withdraw(user_id, request.getAmount());
		Response<WalletDto> response = new Response<WalletDto>("Withdrawal successful", wallet);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{user_id}/history")
	public ResponseEntity<List<OperationDto>> getWalletHistory(@PathVariable Long user_id) {
		List<OperationDto> history = walletService.getHistory(user_id);
		return ResponseEntity.ok(history);
	}

}