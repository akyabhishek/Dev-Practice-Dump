package com.spring.Framework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class FrameworkApplication {

	public static void main(String[] args) {
		
		ApplicationContext applicationContext = SpringApplication.run(FrameworkApplication.class, args);
		
		BinarySearchImpl binarySearch=applicationContext.getBean(BinarySearchImpl.class);
		
		int num =binarySearch.binarySerach(new int[] {12,2,3}, 3);
		System.out.println(num);
		
	}
}
