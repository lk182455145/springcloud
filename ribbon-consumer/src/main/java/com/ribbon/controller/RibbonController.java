package com.ribbon.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RibbonController {
	
	private final RestTemplate restTemplate;

	@GetMapping
	public String say() {
		return restTemplate.getForObject("http://eureka-client", String.class);
	}
}
