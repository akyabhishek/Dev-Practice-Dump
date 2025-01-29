package com.bharath.spring.springcore.properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

	public static void main(String[] args) {
		
		ApplicationContext context=new ClassPathXmlApplicationContext("com/bharath/spring/springcore/properties/setconfig.xml");
		Languages languages=(Languages) context.getBean("languages");
		System.out.println(languages.getLanguage());
	}

}
