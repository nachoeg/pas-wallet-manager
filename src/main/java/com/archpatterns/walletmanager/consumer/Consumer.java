package com.archpatterns.walletmanager.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.archpatterns.walletmanager.dtos.Listdata;
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

	@RabbitListener(queues = { "${rabbitmq.queue.name.check-money}"})
	public void receiveCheckMoney(@Payload Listdata data) {
		log.info("Received data: {}", data);
		if (walletService.checkMoney(data)) {
			log.info("Money check passed for data: {}", data);
			publisher.confirmCheckout(data);
			publisher.makeOrder(data);
		} else {
			log.warn("Money check failed for data: {}", data);
			publisher.rejectCheckout(data);
			publisher.resetStock(data);
		}
	}
}