package com.hystrix.test;

import com.hystrix.service.IHystrixService;

import feign.Feign;

public class FeignKey {

	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		System.out.println(Feign.configKey(IHystrixService.class, IHystrixService.class.getMethod("retry", int.class)));
	}

}
