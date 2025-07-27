package com.archpatterns.walletmanager.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.archpatterns.walletmanager.dtos.Listdata;
import com.archpatterns.walletmanager.dtos.WalletDto;
import com.archpatterns.walletmanager.publisher.Publisher;
import com.archpatterns.walletmanager.services.WalletService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class Consumer {

	private final WalletService walletService;
	private final Publisher publisher;

	@RabbitListener(queues = { "${rabbitmq.queue.name.check-money}" })
	public void receiveCheckMoney(@Payload Listdata data) {
		log.info("Received data: {}", data);
		try {
			WalletDto updatedWallet = walletService.processCheckout(data);
			log.info("Checkout processed successfully, new balance: {}", updatedWallet.getBalance());
			publisher.confirmCheckout(data);
			publisher.makeOrder(data);
		} catch (Exception e) {
			log.error("Checkout failed: {}", e.getMessage());
			publisher.rejectCheckout(data);
			publisher.resetStock(data);
		}
	}
}