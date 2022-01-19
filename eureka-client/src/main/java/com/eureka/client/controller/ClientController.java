package com.eureka.client.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

	@GetMapping
	public String say(HttpServletRequest request) {
		return "this is eureka client " + request.getLocalPort();
	}
	
	@GetMapping("hi")
	public String sayHi(HttpServletRequest request) {
		return "this is eureka client Hi " + request.getLocalPort();
	}
}
