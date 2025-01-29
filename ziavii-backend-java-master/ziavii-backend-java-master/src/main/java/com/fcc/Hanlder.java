package com.fcc;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

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
import com.fcc.config.AwsSesConfig;
import com.fcc.config.AwsSesCred;


@Component
public class Hanlder {
	@Autowired
    private static JavaMailSender javaMailSender;
	@Autowired
	AwsSesCred awsSesCred;
	
	public String otp = "1230";
	 final String FROM = "sshariq30@gmail.com";
	 
	  // The subject line for the email.
	 final String SUBJECT = "One last step to complete your registration with PhotoApp";
	  
	  // The HTML body for the email.
	  final String HTMLBODY = "<h1>Please verify your email address</h1>"
	      + "<p>Thank you for registering with our mobile app. To complete registration process and be able to log in,"
	      + " Enter this One time password: " 
	      + "<p>"+otp+"</p>" 
	      + "</a><br/><br/>"
	      + "Thank you! And we are waiting for you inside!";

	  // The email body for recipients with non-HTML email clients.
	  final String TEXTBODY = "Please verify your email address. "
	      + "Thank you for registering with our mobile app. To complete registration process and be able to log in,"
	      + " open then the following URL in your browser window: " 
	      + " http://localhost:8080/MOBILE-APP-WS-DEV/verify-email.jsp?token=$tokenValue"
	      + " Thank you! And we are waiting for you inside!";
	  
	public static void main(String[] args) throws IOException, ParseException {
		
		
		new Hanlder().verifyEmail();
		  
		  
		  
		  
		
		//send("shariq1926@outlook.com","sshariq30@gmail.com","SES test","AWS ses");
		
//		
//		int t = 90;
//		int hours = t / 60;
//		int minutes = t % 60; //since both are ints, you get an int int minutes = t % 60; System.out.printf("%d:%02d", hours, minutes);. The following format displays time from − hh:mm:ss.
//		System.out.printf("%d:%02d", hours, minutes);
//		
//		LocalTime.MIN.plus( 
//			    Duration.ofMinutes(90) 
//			).toString();
		
		
//		Double d = 230.5;
//		String sd = String.valueOf(d);
//		System.out.println(sd);
//		Double newd = Double.parseDouble(sd);
//		System.out.println(newd);
//		
		
		
//		String inputText = "Jameel";
//		String output = inputText.replaceAll("\\s+","-"); 
//		System.out.println(output);
		
//		ClassLoader classLoader = Handler.class.getClassLoader();
//		File file = new File(classLoader.getResource("images").getFile());
//		System.out.println(file);
		
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2020-11-21 00:00:00"));
//		cal.add(Calendar.DATE, -7);
//		System.out.println(cal.getTime());
		
//		File uploadfolder = new ClassPathResource("/images").getFile();
//		System.out.println("File location " +uploadfolder);
//		
//		Path path = Paths.get(uploadfolder.getPath());
//		System.out.println("Path " +path);
	}
	
	 public AmazonSimpleEmailService amazonSimpleEmailService() {

		    return AmazonSimpleEmailServiceClientBuilder.standard()
		        .withCredentials(
		            new AWSStaticCredentialsProvider(
		                new BasicAWSCredentials(
		                		"AKIARSIHAEKPKI3PX374", "ScnyVzwRJn0GQnh4m1sL67UK1wROn8M4tYR1TtxY")))
		        .withRegion(Regions.AP_SOUTH_1)
		        .build();
		  }

	  public void verifyEmail()
	  {
	      try {
	    	  //AwsSesConfig awsConfig = new AwsSesConfig();
	    	 AmazonSimpleEmailService client = amazonSimpleEmailService();
	    
	      SendEmailRequest request = new SendEmailRequest()
	          .withDestination(
	              new Destination().withToAddresses( "shariq1926@outlook.com" ) )
	          .withMessage(new Message()
	              .withBody(new Body()
	                  .withHtml(new Content()
	                      .withCharset("UTF-8").withData(HTMLBODY))
	                  .withText(new Content()
	                      .withCharset("UTF-8").withData(TEXTBODY)))
	              .withSubject(new Content()
	                  .withCharset("UTF-8").withData(SUBJECT)))
	          .withSource(FROM);

	      client.sendEmail(request);
	      
	      System.out.println("Email sent!");
	      
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	  }
	
	
	
//    public static void send(String to, String from, String subject, String body){
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom(from);
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(body);
//        javaMailSender.send(message);
//    }

}
