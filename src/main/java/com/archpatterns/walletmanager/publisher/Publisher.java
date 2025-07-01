package com.archpatterns.walletmanager.publisher;

import com.archpatterns.walletmanager.dtos.Listdata;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@EnableRabbit
public class Publisher {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Value("${rabbitmq.queue.name.confirm-checkout}")
	private String confirmCheckoutQueue;

	@Value("${rabbitmq.queue.name.make-order}")
	private String makeOrderQueue;

	@Value("${rabbitmq.queue.name.reject-checkout}")
	private String rejectCheckoutQueue;

	@Value("${rabbitmq.queue.name.reset-stock}")
	private String resetStockQueue;

	public void confirmCheckout(Listdata data) {
		log.info("Publishing to confirm checkout queue: {}", confirmCheckoutQueue);
		rabbitTemplate.convertAndSend(confirmCheckoutQueue, data);
	}

	public void makeOrder(Listdata data) {
		log.info("Publishing to make order queue: {}", makeOrderQueue);
		rabbitTemplate.convertAndSend(makeOrderQueue, data);
	}

	public void rejectCheckout(Listdata data) {
		log.info("Publishing to reject checkout queue: {}", rejectCheckoutQueue);
		rabbitTemplate.convertAndSend(rejectCheckoutQueue, data);
	}

	public void resetStock(Listdata data) {
		log.info("Publishing to reset stock queue: {}", resetStockQueue);
		rabbitTemplate.convertAndSend(resetStockQueue, data);
	}

}