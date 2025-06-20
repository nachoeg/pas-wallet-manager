package com.archpatterns.walletmanager.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.archpatterns.walletmanager.dtos.ErrorDto;
import com.archpatterns.walletmanager.exceptions.InsufficientFundsException;
import com.archpatterns.walletmanager.exceptions.WalletNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class Handler {

	@ExceptionHandler(WalletNotFoundException.class)
	public ResponseEntity<ErrorDto> handleWalletNotFoundException(WalletNotFoundException ex) {
		log.error("Wallet not found: {}", ex.getMessage());
		ErrorDto error = ErrorDto.builder().code(HttpStatus.NOT_FOUND.value()).message("Wallet not found")
				.detail(ex.getMessage()).localizedException(ex.getLocalizedMessage()).build();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@ExceptionHandler(InsufficientFundsException.class)
	public ResponseEntity<ErrorDto> handleInsufficientFundsException(InsufficientFundsException ex) {
		log.error("Insufficient funds: {}", ex.getMessage());
		ErrorDto error = ErrorDto.builder().code(HttpStatus.BAD_REQUEST.value()).message("Insufficient funds")
				.detail(ex.getMessage()).localizedException(ex.getLocalizedMessage()).build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDto> handleGeneralException(Exception ex) {
		log.error("An error occurred: {}", ex.getMessage());
		ErrorDto error = ErrorDto.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.message("An unexpected error occurred").detail(ex.getMessage())
				.localizedException(ex.getLocalizedMessage()).build();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorDto> handleIllegalArgumentException(IllegalArgumentException ex) {
		log.error("Invalid argument: {}", ex.getMessage());
		ErrorDto error = ErrorDto.builder().code(HttpStatus.BAD_REQUEST.value()).message("Invalid argument")
				.detail(ex.getMessage()).localizedException(ex.getLocalizedMessage()).build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorDto> handleRuntimeException(RuntimeException ex) {
		log.error("Runtime exception: {}", ex.getMessage());
		ErrorDto error = ErrorDto.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.message("A runtime error occurred").detail(ex.getMessage())
				.localizedException(ex.getLocalizedMessage()).build();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}

}