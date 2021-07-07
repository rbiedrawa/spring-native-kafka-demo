package com.rbiedrawa.app.customers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rbiedrawa.app.customers.persistance.Customer;
import com.rbiedrawa.app.customers.persistance.CustomerRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class CustomerController {

	private final CustomerRepository customerRepository;

	@GetMapping("customers")
	Flux<Customer> customers() {
		return customerRepository.findAll();
	}
}
