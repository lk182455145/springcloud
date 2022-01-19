package com.hystrix.service.impl;

import org.springframework.stereotype.Service;

import com.feign.dto.User;
import com.hystrix.service.IHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class HystrixServiceImpl implements IHystrixService {

	@Override
	public String say() {
		return null;
	}
	
	@Override
	public User sayHi(User user) {
		return null;
	}

	@Override
	public String retry(int timeout) {
		return "you are late!!!!!";
	}

	@Override
	@HystrixCommand(fallbackMethod = "error2")
	public String error() {
		throw new RuntimeException("my error");
//		return "this is Hystrix error";
	}
	
	@HystrixCommand(fallbackMethod = "error3")
	public String error2() {
		throw new RuntimeException("my error2");
	}
	
	public String error3() {
		return "ok,this is error3";
	}

}
