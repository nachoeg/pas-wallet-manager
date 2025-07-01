package com.archpatterns.walletmanager.publisher;

import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@EnableRabbit
public class Publisher {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	@Qualifier("confirm-checkout")
	private String confirmCheckoutQueue;

	@Autowired
	@Qualifier("make-order")
	private String makeOrderQueue;

	@Autowired
	@Qualifier("reject-checkout")
	private String rejectCheckoutQueue;

	@Autowired
	@Qualifier("reset-stock")
	private String resetStockQueue;

	public void confirmCheckout(Object data) {
		log.info("Publishing to confirm checkout queue: {}", confirmCheckoutQueue);
		rabbitTemplate.convertAndSend(confirmCheckoutQueue, data);
	}

	public void makeOrder(Object data) {
		log.info("Publishing to make order queue: {}", makeOrderQueue);
		rabbitTemplate.convertAndSend(makeOrderQueue, data);
	}

	public void rejectCheckout(Object data) {
		log.info("Publishing to reject checkout queue: {}", rejectCheckoutQueue);
		rabbitTemplate.convertAndSend(rejectCheckoutQueue, data);
	}

	public void resetStock(Object data) {
		log.info("Publishing to reset stock queue: {}", resetStockQueue);
		rabbitTemplate.convertAndSend(resetStockQueue, data);
	}

}