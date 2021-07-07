package com.rbiedrawa.app.customers.kafka;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.rbiedrawa.app.avro.customers.events.CustomerCreated;
import com.rbiedrawa.app.config.KafkaConfiguration;
import com.rbiedrawa.app.core.kafka.serdes.SerdeFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerEventsKStream {

	private final SerdeFactory serdeFactory;

	@Bean
	public KStream<String, CustomerCreated> test(StreamsBuilder sb) {
		return sb.stream(KafkaConfiguration.Topics.CUSTOMER_EVENTS, Consumed.with(Serdes.String(), serdeFactory.of(CustomerCreated.class)))
				 .peek((transactionId, event) -> log.info("Consumed customer event {}.", event));
	}
}
