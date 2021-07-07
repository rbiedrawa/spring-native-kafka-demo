package com.rbiedrawa.app.customers;

import static com.rbiedrawa.app.config.KafkaConfiguration.Topics;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import com.rbiedrawa.app.avro.customers.events.CustomerCreated;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerEventListener {

	@KafkaListener(topics = Topics.CUSTOMER_EVENTS, groupId = "customer-events-listener")
	public void consume(ConsumerRecord<String, CustomerCreated> record, Acknowledgment ack) {
		log.info("Received CustomerCreated event -> {}", record.value());
		// Example of manual ack
		ack.acknowledge();
	}

}