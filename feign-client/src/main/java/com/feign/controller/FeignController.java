package com.feign.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.feign.dto.User;
import com.feign.service.IFeignService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class FeignController implements IFeignService {
	
	@Value("${server.port}")
	private String port;

	@Override
	public String say() {
		return "this is FeignController: " + port;
	}

	@Override
	public User sayHi(@RequestBody User user) {
		user.setAge(18);
		return user;
	}

	@Override
	public String retry(@RequestParam("timeout") int timeout) {
		while(--timeout >= 0) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		log.info("this port:{}", port);
		return port;
	}

	@Override
	public String error() {
		throw new RuntimeException("this is error");
	}

}
