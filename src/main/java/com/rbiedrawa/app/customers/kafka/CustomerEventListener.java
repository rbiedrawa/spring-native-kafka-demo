package com.rbiedrawa.app.customers.kafka;

import static com.rbiedrawa.app.config.KafkaConfiguration.Topics;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import com.rbiedrawa.app.avro.customers.events.CustomerCreated;
import com.rbiedrawa.app.customers.persistance.Customer;
import com.rbiedrawa.app.customers.persistance.CustomerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerEventListener {

	private final CustomerRepository customerRepository;

	@KafkaListener(topics = Topics.CUSTOMER_EVENTS, groupId = "customer-events-listener")
	public void consume(ConsumerRecord<String, CustomerCreated> record, Acknowledgment ack) {
		log.info("Received CustomerCreated event -> {}", record.value());
		customerRepository.save(Customer.of(record.value())).block();
		ack.acknowledge();
	}

}