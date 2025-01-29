package com.fcc;

import java.util.Calendar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class FccrestapiApplication {

	public static void main(String[] args) {
	//	  System.out.println("hello shally");
		SpringApplication.run(FccrestapiApplication.class, args);
	}
    
	
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
	
	
	//@Scheduled(initialDelay = 1000, fixedRate = 10000)
	@Scheduled(cron = "0 40 22 15 * ?")
	public void run() {
	    System.out.println("Current time is :: " + Calendar.getInstance().getTime());
	}
	
	/*
	-- insert into fcc_local_07022021.master_gender(gender_id, gender)values(1,'MALE');
	-- insert into fcc_local_07022021.master_gender(gender_id, gender)values(2,'FEMALE');

	-- insert into fcc_local_07022021.master_booking_status(status_id,status)values(1,'CONFIRMED');
	-- insert into fcc_local_07022021.master_booking_status(status_id,status)values(2,'CANCELLED');
	-- insert into fcc_local_07022021.master_booking_status(status_id,status)values(3,'PENDING');

	-- insert into fcc_local_07022021.shop_type(shop_type_id,type)values(1,'Saloon');
	-- insert into fcc_local_07022021.shop_type(shop_type_id,type)values(2,'Parlour');
	-- insert into fcc_local_07022021.shop_type(shop_type_id,type)values(3,'Unisex');  */
}

//@Component
//public class DemoData implements ApplicationRunner {
//
//    @Autowired
//    private final EntityRepository repo;
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//
//        repo.save(new Entity(...));
//    }
//
//	
//}