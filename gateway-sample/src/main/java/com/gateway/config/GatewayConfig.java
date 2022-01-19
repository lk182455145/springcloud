package com.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import com.gateway.filter.GatewayTimerFilter;

import lombok.RequiredArgsConstructor;

/**
 * java类配置gateway路由规则
 * 
 * @author lk
 *
 */
@Configuration
@RequiredArgsConstructor
public class GatewayConfig {
	
	private final GatewayTimerFilter timerFilter;

	@Bean
	public RouteLocator routeLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(r -> r.path("/java/**")
						.and().method(HttpMethod.GET)
						.and().header("name")
						
						.filters(f -> f.stripPrefix(1)
								.filter(timerFilter)
								.addResponseHeader("java", "this is gateway java"))
						
						.uri("lb://FEIGN-CLIENT"))
				.build();
	}
}
