package com.rbiedrawa.app.customers;


import static com.rbiedrawa.app.config.KafkaConfiguration.Topics;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.rbiedrawa.app.avro.customers.events.CustomerCreated;
import com.rbiedrawa.app.core.UuidGenerator;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RandomEventGenerator {
	private final KafkaTemplate<String, CustomerCreated> kafkaTemplate;
	private final UuidGenerator uuidGenerator;

	@Scheduled(fixedDelay = 5_000)
	public void produceRandomEvent() {
		send(newRandomCustomerEvent());
	}

	@SneakyThrows
	private void send(CustomerCreated event) {
		kafkaTemplate.send(Topics.CUSTOMER_EVENTS, event.getUserId(), event).get();
		log.info("Successfully sent CustomerCreated event {} ", event);
	}

	private CustomerCreated newRandomCustomerEvent() {
		return CustomerCreated.newBuilder()
							  .setEid(uuidGenerator.gen())
							  .setUserId(uuidGenerator.gen())
							  .setName(String.format("Name %s", uuidGenerator.gen()))
							  .setEmail(String.format("%s@test.com", uuidGenerator.gen()))
							  .build();
	}
}
