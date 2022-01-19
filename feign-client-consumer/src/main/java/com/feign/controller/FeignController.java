package com.feign.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.feign.dto.User;
import com.feign.service.IFeignService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FeignController {

	private final IFeignService feignService;
	
	@GetMapping("say")
	public String say() {
		return feignService.say();
	}
	
	@PostMapping("sayH")
	public User sayH(@RequestBody User user) {
		return feignService.sayHi(user);
	}
	
	@GetMapping("retry")
	public String retry(@RequestParam("timeout") int timeout) {
		return feignService.retry(timeout);
	}
	
}
