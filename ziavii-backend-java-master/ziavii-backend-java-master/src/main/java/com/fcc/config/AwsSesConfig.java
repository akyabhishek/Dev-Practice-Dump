package com.fcc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;

@Configuration
public class AwsSesConfig {

	@Autowired
	AwsSesCred awsSesCred;
	
	
	 private AWSCredentials createAWSCredentials() {
		 
		 AWSCredentials awsCredentials = new BasicAWSCredentials(awsSesCred.awsAccessKey, awsSesCred.awsSecretKey);
		 return awsCredentials;
	 }
	 
	 
	  @Bean
	  public AmazonSimpleEmailService amazonSimpleEmailService() {

	    return AmazonSimpleEmailServiceClientBuilder.standard()
	        .withCredentials(
	            new AWSStaticCredentialsProvider(
	                new BasicAWSCredentials(
	                		awsSesCred.awsAccessKey, awsSesCred.awsSecretKey)))
	        .withRegion(Regions.AP_SOUTH_1)
	        .build();
	  }

	 
//	 AmazonSimpleEmailService client = 
//          AmazonSimpleEmailServiceClientBuilder.standard()
//	          .withCredentials(new AWSCredentialsProvider() {
//				
//				@Override
//				public void refresh() {
//					// TODO Auto-generated method stub
//					
//				}
//				
//				@Override
//				public AWSCredentials getCredentials() {
//					// TODO Auto-generated method stub
//					return null;
//				}
//			})
//            .withRegion(Regions.US_EAST_1).build();
	            
}
