package com.hystrix.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hystrix.service.IHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class HystrixController {

	private final IHystrixService hystrixService;

	@GetMapping("error")
	public String error() {
		return hystrixService.error();
	}

	@GetMapping("timeout")
	public String timeout(int timeout) {
		return hystrixService.retry(timeout);
	}

	@GetMapping("timeout2")
	@HystrixCommand(fallbackMethod = "timeout2FallBack", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000") })
	public String timeout2(int timeout) {
		return hystrixService.retry(timeout);
	}

	public String timeout2FallBack(int timeout) {
		return "this is timeout2FallBack";
	}
}
