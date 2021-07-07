package com.rbiedrawa.app.core;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class UuidGenerator {

	public String gen() {
		return UUID.randomUUID().toString();
	}
}
