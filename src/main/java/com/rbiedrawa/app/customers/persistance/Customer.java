package com.rbiedrawa.app.customers.persistance;

import java.util.UUID;

import org.springframework.data.annotation.Id;

import com.rbiedrawa.app.avro.customers.events.CustomerCreated;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Customer {
	@Id
	private Long id;
	private UUID userId;
	private String name;
	private String email;

	public static Customer of(CustomerCreated event) {
		return Customer.builder()
					   .userId(UUID.fromString(event.getUserId()))
					   .email(event.getEmail())
					   .name(event.getName())
					   .build();
	}
}
