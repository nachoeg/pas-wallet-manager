package com.archpatterns.walletmanager.configs;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

	@Value("${rabbitmq.queue.name.check-money}")
	private String checkMoneyQueueName;

	@Value("${rabbitmq.queue.name.confirm-checkout}")
	private String confirmCheckoutQueueName;

	@Value("${rabbitmq.queue.name.reject-checkout}")
	private String rejectCheckoutQueueName;

	@Value("${rabbitmq.queue.name.make-order}")
	private String makeOrderQueueName;

	@Value("${rabbitmq.queue.name.reset-stock}")
	private String resetStockQueueName;

	@Bean
	Queue checkMoneyQueue() {
		return new Queue(checkMoneyQueueName, true);
	}

	@Bean
	Queue confirmCheckoutQueue() {
		return new Queue(confirmCheckoutQueueName, true);
	}

	@Bean
	Queue rejectCheckoutQueue() {
		return new Queue(rejectCheckoutQueueName, true);
	}

	@Bean
	Queue makeOrderQueue() {
		return new Queue(makeOrderQueueName, true);
	}

	@Bean
	Queue resetStockQueue() {
		return new Queue(resetStockQueueName, true);
	}

	@Bean
	public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
										 Jackson2JsonMessageConverter messageConverter) {
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		template.setMessageConverter(messageConverter);
		return template;
	}
}
