package com.ribbon.config;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.ribbon.rule.HashCodeRule;

@Configuration
//配置某服务的负载均很策略
@RibbonClient(name = "eureka-client", configuration = HashCodeRule.class)
public class RibbonConfig {

	//配置全局的负载均衡策略
//	@Bean
//	public IRule defaultLBS() {
//		return new RandomRule();
//	}
}
