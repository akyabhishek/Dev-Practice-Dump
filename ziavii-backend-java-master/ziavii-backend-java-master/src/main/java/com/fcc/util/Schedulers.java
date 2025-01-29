package com.fcc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fcc.domain.Order;
import com.fcc.repository.OrdersRepo;
import com.fcc.service.OrderDaoService;
import com.fcc.service.VendorDaoService;

@Component
public class Schedulers {

	@Autowired
	VendorDaoService vendorDaoService;
	
	
	//@Scheduled(initialDelay = 1000, fixedRate = 10000)
		@Scheduled(cron = "0 01 00 01 * ?")
		public void monthlyTaskScheduler() throws ParseException {
			//vendorDaoService.calculateMonthlyIncome();
			vendorDaoService.initiateVendorMonthly();
		    System.out.println("Current time is :: " + Calendar.getInstance().getTime());
		}
		
		
		@Scheduled(cron = "0 02 00 01 * ?")
		public void EnablePayScheduler() throws ParseException {
			//vendorDaoService.calculateMonthlyIncome();
			vendorDaoService.enablePayEOM();
		    System.out.println("Current time is :: " + Calendar.getInstance().getTime());
		}
	
	//Capture all confirmed bookings and there total price at the 1st of every month for the last month.
	
	//The scheduler will execute the job on 3:00 am at 1st of every month.
	//The job is to create an entry in a database for every vendor with total amount earned in the last month.
	//The vendor will have the isPaid flag in the transaction table.
		@Scheduled(cron = "0 01 00 05 * ?")
		public void modifyBookingAbility() {
			vendorDaoService.updateBookingStatusVendor();
			System.out.println("Current time is :: " + Calendar.getInstance().getTime());
		}
	
}
