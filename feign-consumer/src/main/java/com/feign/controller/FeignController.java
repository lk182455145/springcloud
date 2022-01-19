package com.feign.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feign.service.IEurekaClientService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FeignController {

	private final IEurekaClientService service;
	
	@GetMapping
	public String say() {
		return service.say();
	}
	
//	@GetMapping("hi")
//	public String sayHi() {
//		return service.sayHi();
//	}
}
