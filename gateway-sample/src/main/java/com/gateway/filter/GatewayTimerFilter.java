package com.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * 自定义一个计时器过滤器
 * 
 * @author lk
 *
 */
@Slf4j
@Component
// GlobalFilter 为全局过滤器
public class GatewayTimerFilter implements GatewayFilter, Ordered {

	/**
	 * 优先级，值越小优先级越高
	 */
	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		StopWatch timer = new StopWatch();
		timer.start(exchange.getRequest().getURI().getRawPath());

		return chain.filter(exchange).then(Mono.fromRunnable(() -> {
			timer.stop();
			log.info(timer.prettyPrint());
		}));
	}

}
