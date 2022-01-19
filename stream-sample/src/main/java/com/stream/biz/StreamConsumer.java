package com.stream.biz;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

import com.stream.channel.DlqTopic;
import com.stream.channel.ErrorTopic;
import com.stream.channel.FallbackTopic;
import com.stream.channel.Group;
import com.stream.channel.MyTopic;
import com.stream.channel.RequeueTopic;
import com.stream.entity.MessageBean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableBinding(value = {Sink.class,MyTopic.class,Group.class,ErrorTopic.class,RequeueTopic.class,DlqTopic.class,FallbackTopic.class})
public class StreamConsumer {
	
	private AtomicInteger count = new AtomicInteger(1);

	// Sink是stream默认的通信通道
	@StreamListener(Sink.INPUT)
	public void consumer(Object payload) {
		log.info("this consumer: {}", payload);
	}
	
	@StreamListener(MyTopic.INPUT)
	public void mytopicConsumer(Object payload) {
		log.info("this mytopic: {}", payload);
	}
	
	@StreamListener(Group.INPUT)
	public void groupConsumer(Object payload) {
		log.info("this gruop: {}", payload);
	}
	
	@StreamListener(ErrorTopic.INPUT)
	public void errorConsumer(MessageBean mb) {
		log.info("are you ok?");
		if(count.incrementAndGet() % 3 == 0) {
			log.info("this error: {}", mb.getPayload());
			count.set(0);
		}else {
			log.error("no no no ");
			throw new RuntimeException();
		}
	}
	
	@StreamListener(RequeueTopic.INPUT)
	public void requeueConsumer(MessageBean mb) {
		log.info("requeue - are you ok?");
		try {
			Thread.sleep(5000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		throw new RuntimeException("requeue - no ok");
	}
	
	@StreamListener(DlqTopic.INPUT)
	public void dlqConsumer(MessageBean mb) {
		log.info("dlq - are you ok?");
		if(count.incrementAndGet() % 3 == 0) {
			log.info("dlq - this error: {}", mb.getPayload());
		}else {
			log.error("dlq - no no no ");
			throw new RuntimeException();
		}
	}
	
	@StreamListener(FallbackTopic.INPUT)
	public void fallbackConsumer(MessageBean mb,@Header("version")String version) {
		log.info("fallback - are you ok?");
		if("1.0".equals(version)) {
			log.info("fallback - ok 1.0 {}", mb.getPayload());
		}else {
			log.info("fallback - no ok");
			throw new RuntimeException();
		}
	}
	
	/**
	 * inputChannel = 名称+组名+errors
	 * @param message
	 */
	@ServiceActivator(inputChannel = "fallback-topic.fallback-group.errors")
	public void fallback(Message<?> message) {
		log.info("fallback!!!!!!!!!!!!!!!");
	}
}
