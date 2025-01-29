package com.fcc.util;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

public class Handler {

	public static void main(String[] args) {
		
		
		      
		//Date date = getFirstLastDayOfMonth(new Date(), true);
		
		
//		Random rand = new Random();
//		String rndm = Integer.toString(rand.nextInt()) + (System.currentTimeMillis() / 1000L);
//		String txnid = CommonUtils.hashCal("SHA-256", rndm).substring(0, 20);
//		System.out.println(txnid);
		
		
//		LocalTime startTime = LocalTime.of(9, 00);
//		LocalTime endTime = LocalTime.of(21, 00);
//		long span = Duration.between(startTime, endTime).toNanos();
// 
//		long hours = ChronoUnit.HOURS.between(startTime, endTime);
//		System.out.println((hours*60)/15);
//		final long n = hours*60/15;
//		 // Number of pieces
//	        LongStream.rangeClosed(0, n)
//		    .map(i -> i * span / n)
//		    .mapToObj(i -> startTime.plusNanos(i))
//		    .forEach(System.out::println);
		
		
		
	/*	 LocalTime startTime = LocalTime.of(10, 00);
		    LocalTime endTime = LocalTime.of(21, 00);

		    long hours = ChronoUnit.HOURS.between(startTime, endTime);
			System.out.println((hours*60)/15);
			final long n = hours*60/15;
//		    final int n = 24; // Number of pieces
		    Duration piece = Duration.between(startTime, endTime).dividedBy(n);
		    LocalTime[] partitionTimes = IntStream.rangeClosed(0, Integer.valueOf(String.valueOf(n)))
		        .mapToObj(i -> startTime.plus(piece.multipliedBy(i)))
		        .toArray(LocalTime[]::new);
		    System.out.println(Arrays.toString(partitionTimes));  */
	        
	        
		
//		System.out.println("Started");
//		//LocalDateTime dateTime = LocalDateTime.parse("2018-05-05 11:50:55");
//		System.out.println("End");
//		LocalDate currentDate = LocalDate.now();
//		LocalDateTime currentTime = currentDate.atTime(9, 15);
//		
//	    currentTime.isBefore(LocalDateTime.now());
//		long milli = currentTime.toEpochSecond(ZoneOffset.UTC);
//		LocalDate now = LocalDate.now();
////		System.out.println("Current time " + now);
////		System.out.println(CommonUtils.getNearestHourQuarter(now));
//		
//		//Date now = new Date();
//		System.out.println("Java util date " +now);
//		
////		LocalDate date = CommonUtils.convertToLocalDateViaInstant(now);
////		System.out.println("Java 8 API Date " +date);
//		LocalDate.of(2020, 9, 1);
//		now.isAfter(now);
		
	}
	
	public static Date getFirstLastDayOfMonth(Date date, boolean isFirst) {
		Calendar cal = Calendar.getInstance();
		Date dateRepresentation = null;
		if (isFirst) {
			cal.set(Calendar.YEAR, date.getYear());
			cal.set(Calendar.MONTH, date.getMonth());
			cal.set(Calendar.DAY_OF_MONTH, 1);
			dateRepresentation = cal.getTime();
		} else {
			cal.setTime(date);

			cal.add(Calendar.MONTH, 1);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.add(Calendar.DATE, -1);
			dateRepresentation = cal.getTime();
		}

		return dateRepresentation;
	}
}
