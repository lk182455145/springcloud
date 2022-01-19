package com.config.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {

	@Value("${name}")
	private String name;
	
	@Value("${my.message}")
	private String message;
	
	@GetMapping("name")
	public String getname() {
		return name;
	}
	
	@GetMapping("message")
	public String getMessage() {
		return message;
	}
}
