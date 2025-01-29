package com.fcc.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

@Service
public class SimpleEmailDaoServie {

	  @Value("${mailFrom}")
	  public String mailFrom;
	  @Value("${awsAccessKey}")
	  public String awsAccessKey;
	  @Value("${awsSecretKey}")
	  public String awsSecretKey;
	  
	  final String FROM = "sshariq30@gmail.com";
     // public String otp = "";		 
	  // The subject line for the email.
	  final String SUBJECTOTP = "One last step to complete your registration with FCC";
	  final String SUBJECTPWD = "Recover your Password";
	  
	  
	  // The HTML body for the email.
//	  final String HTMLBODY = "<h1>Please verify your email address</h1>"
//	      + "<p>Thank you for registering with our mobile app. To complete registration process and be able to log in,"
//	      + " One time password: " 
//	      + otp
//	      + "</a><br/><br/>"
//	      + "Thank you! And we are waiting for you inside!";

	  // The email body for recipients with non-HTML email clients.
	  final String TEXTBODY = "Please verify your email address. "
	      + "Thank you for registering with our mobile app. To complete registration process and be able to log in,"
	      + " open then the following URL in your browser window: " 
	      + " http://localhost:8080/MOBILE-APP-WS-DEV/verify-email.jsp?token=$tokenValue"
	      + " Thank you! And we are waiting for you inside!";
	  
	  public AmazonSimpleEmailService amazonSimpleEmailService() {

		    return AmazonSimpleEmailServiceClientBuilder.standard()
		        .withCredentials(
		            new AWSStaticCredentialsProvider(
		                new BasicAWSCredentials(
		                		awsAccessKey, awsSecretKey)))
		        .withRegion(Regions.AP_SOUTH_1)
		        .build();
		  }
	  
	  
	  
	  public void verifyEmail(String toEmail, String pass, boolean isForgotPass)
	  {
	      try {
	    	  
	    	  String HTMLBODYOTP = "<h1>Please verify your email address</h1>"
	    		      + "<p>Thank you for registering with our mobile app. To complete registration process and be able to log in,"
	    		      + " One time password: " 
	    		      + "<p>"+pass+"</p>" 
	    		      + "</a><br/><br/>"
	    		      + "Thank you! And we are waiting for you inside!";
	    	  
	    	  String HTMLBODYPWD = "<h1>Your password is below</h1>"
	    		      + "<p>Kindly, Reset your password for security.,"
	    		      + " Password: " 
	    		      + "<p>"+pass+"</p>" 
	    		      + "</a><br/><br/>"
	    		      + "Thank you! And we are waiting for you inside!";

	    	  //AwsSesConfig awsConfig = new AwsSesConfig();
	    	 AmazonSimpleEmailService client = amazonSimpleEmailService();
	     String HTMLBODY = "";
	     String SUBJECT = "";
	     if(isForgotPass) {
	    	 HTMLBODY = HTMLBODYPWD;
	    	 SUBJECT = SUBJECTPWD;
	     }else {
	    	 HTMLBODY = HTMLBODYOTP;
	    	 SUBJECT = SUBJECTOTP;
	     }
	      SendEmailRequest request = new SendEmailRequest()
	          .withDestination(
	              new Destination().withToAddresses( toEmail ) )
	          .withMessage(new Message()
	              .withBody(new Body()
	                  .withHtml(new Content()
	                      .withCharset("UTF-8").withData(HTMLBODY))
	                  .withText(new Content()
	                      .withCharset("UTF-8").withData(TEXTBODY)))
	              .withSubject(new Content()
	                  .withCharset("UTF-8").withData(SUBJECT)))
	          .withSource(mailFrom);

	      client.sendEmail(request);
	      
	      System.out.println("Email sent!");
	      
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	  }
}
