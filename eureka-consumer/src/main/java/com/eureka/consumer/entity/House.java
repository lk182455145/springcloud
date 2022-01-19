package com.eureka.consumer.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class House {

	private String name;
	
	private String are;
	
	public static void main(String[] args) {
		House build = House.builder().are("aa").name("bb").build();
		System.out.println(build.getAre());
	}
}
