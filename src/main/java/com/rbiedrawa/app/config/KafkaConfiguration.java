package com.rbiedrawa.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.config.TopicBuilder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;

@EnableKafka
@EnableKafkaStreams
@Configuration
public class KafkaConfiguration {
	private static final int DEFAULT_PARTITION_COUNT = 6;

	@Bean
	NewTopic customerEventsTopic() {
		return TopicBuilder.name(Topics.CUSTOMER_EVENTS)
						   .partitions(DEFAULT_PARTITION_COUNT)
						   .build();
	}

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Topics {
		public static final String CUSTOMER_EVENTS = "customer.events";
	}
}
