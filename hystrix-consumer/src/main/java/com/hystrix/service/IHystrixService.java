package com.hystrix.service;

import org.springframework.cloud.openfeign.FeignClient;

import com.feign.service.IFeignService;
import com.hystrix.service.impl.HystrixServiceImpl;

@FeignClient(value = "feign-client", fallback = HystrixServiceImpl.class)
public interface IHystrixService extends IFeignService {

}
