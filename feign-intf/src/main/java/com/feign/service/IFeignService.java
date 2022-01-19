package com.feign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.feign.dto.User;

@FeignClient("feign-client")
public interface IFeignService {

	@GetMapping("say")
	public String say();
	
	@PostMapping("sayHi")
	public User sayHi(@RequestBody User user);
	
	@GetMapping
	public String retry(@RequestParam("timeout") int timeout);
	
	@GetMapping("error")
	public String error();
}
