package com.stream.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * 自定义通道
 * @author lk
 *
 */
public interface MyTopic {

	String INPUT = "mytopic-input";
	
	String OUTPUT = "mytopic-output";

	//@input 接收消息
	@Input(INPUT)
	SubscribableChannel input();
	
	//@output 发送消息
	@Output(OUTPUT)
	MessageChannel output();
}
