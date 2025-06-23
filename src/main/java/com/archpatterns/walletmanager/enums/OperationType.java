package com.archpatterns.walletmanager.enums;

public enum OperationType {

	DEPOSIT, // This can be used for adding funds to a wallet
	WITHDRAWAL, // This can be used for withdrawing funds from a wallet
	TRANSFER, // This can be used for transferring funds between wallets
	REFUND, // This can be used for returning funds to a user
	CHARGEBACK // This can be used for operations that reverse a previous transaction

}
