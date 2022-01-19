package com.stream.controller;

import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stream.channel.DlqTopic;
import com.stream.channel.ErrorTopic;
import com.stream.channel.FallbackTopic;
import com.stream.channel.Group;
import com.stream.channel.MyTopic;
import com.stream.channel.RequeueTopic;
import com.stream.entity.MessageBean;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MytopicController {
	
	private final MyTopic myTopic;
	
	private final Group group;
	
	private final ErrorTopic errorTopic;
	
	private final RequeueTopic requeueTopic;
	
	private final DlqTopic dlqTopic;
	
	private final FallbackTopic fallbackTopic;

	/**
	 * 广播模式
	 * @param message
	 */
	@GetMapping("topic")
	public void topic(@RequestParam("message") String message) {
		myTopic.output().send(MessageBuilder.withPayload(message).build());
	}
	
	/**
	 * 分组 & 分区
	 * @param message
	 */
	@GetMapping("group")
	public void group(@RequestParam("message") String message) {
		group.output().send(MessageBuilder.withPayload(message).build());
	}
	
	/**
	 * 异常重试（单机版）
	 * @param message
	 */
	@GetMapping("errortopic")
	public void error(@RequestParam("message") String message) {
		MessageBean mb = new MessageBean();
		mb.setPayload(message);
		errorTopic.output().send(MessageBuilder.withPayload(mb).build());
	}
	
	/**
	 * 异常重试，当有异常时重新返回队列
	 * @param message
	 */
	@GetMapping("requeuetopic")
	public void requeue(@RequestParam("message") String message) {
		MessageBean mb = new MessageBean();
		mb.setPayload(message);
		requeueTopic.output().send(MessageBuilder.withPayload(mb).build());
	}
	
	/**
	 * 死信队列
	 * @param message
	 */
	@GetMapping("dlqtopic")
	public void dlq(@RequestParam("message") String message) {
		MessageBean mb = new MessageBean();
		mb.setPayload(message);
		dlqTopic.output().send(MessageBuilder.withPayload(mb).build());
	}
	
	/**
	 * fallback + 升版
	 * @param message
	 */
	@GetMapping("fallback")
	public void fallback(@RequestParam("message") String message, @RequestParam("version") String version) {
		MessageBean mb = new MessageBean();
		mb.setPayload(message);
		fallbackTopic.output().send(MessageBuilder.withPayload(mb).setHeader("version", version).build());
	}
}
