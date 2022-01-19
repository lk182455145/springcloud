package com.eureka.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {
	
	@Autowired
	private LoadBalancerClient client;

	@GetMapping
	public String say() {
		ServiceInstance choose = client.choose("eureka-client");
		return choose.getHost()+":"+choose.getPort();
	}
	
}
