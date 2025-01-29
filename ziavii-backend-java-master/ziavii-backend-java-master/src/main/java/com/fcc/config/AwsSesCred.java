package com.fcc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AwsSesCred {

	  @Value("${mailFrom}")
	  public String mailFrom;
	  @Value("${awsAccessKey}")
	  public String awsAccessKey;
	  @Value("${awsSecretKey}")
	  public String awsSecretKey;
	
}
