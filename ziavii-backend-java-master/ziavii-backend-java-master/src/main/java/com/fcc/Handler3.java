package com.fcc;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Handler3 {
public static void main(String[] args) {
	
//	LocalDateTime start = LocalDateTime.parse( "2021-09-07T16:00:00" ) ;
//	LocalDateTime stop = LocalDateTime.parse( "2021-09-08T16:00:00" ) ;
//	
//	List< LocalDateTime > slots = new ArrayList<>() ;
//	LocalDateTime ldt = start ;
//	while (
//	    ldt.isBefore( stop ) 
//	) {
//	    slots.add( ldt ) ;
//	    // Prepare for the next loop. 
//	    ldt = ldt.plusHours( 1 ) ;
//	}
//	
//	int count = 0;
//	for(LocalDateTime ld : slots) {
//		count++;
//		System.out.println(count);
//		//System.out.println(ld.getHour());
//	}
	//System.out.println(count);
	
	
	
	
	 String[] openArray = getHourMin("15:00");
	    String[] closeArray = getHourMin("3:00");
	 
 	    LocalTime startTime = LocalTime.of(Integer.valueOf(trimLeadingZeroes(openArray[0])), Integer.valueOf(openArray[1]));
	    LocalTime endTime = LocalTime.of(Integer.valueOf(closeArray[0]), Integer.valueOf(closeArray[1]));

	    if(Integer.valueOf(trimLeadingZeroes(openArray[0])) > Integer.valueOf(closeArray[0])) {
	    	System.out.println("It bigger");
	    }
	    
	    //long hours = ChronoUnit.HOURS.between(startTime, endTime);
	    long min =   ChronoUnit.MINUTES.between(startTime, endTime);
	    //long a =  min/Long.valueOf("15");
	    //System.out.println(Math.abs(hours));
		//System.out.println(( Math.abs(hours)*60)/15);
		//final long n =(Math.abs(hours)*60)/15;
		final long n = Math.abs(min)/Long.valueOf("15");
/*PT15M*/
/*PT-15M*/
	    //Duration piece = Duration.between(LocalTime.of(Integer.valueOf(trimLeadingZeroes("08")), Integer.valueOf("00")), LocalTime.of(Integer.valueOf(trimLeadingZeroes("09")), Integer.valueOf("00"))).dividedBy(Long.valueOf(n));
          Duration piece = Duration.parse("PT15M");
	    LocalTime[] partitionTimes = IntStream.rangeClosed(0, Integer.valueOf(String.valueOf(n)))
	        .mapToObj(i -> startTime.plus(piece.abs().multipliedBy(i)))
	        .toArray(LocalTime[]::new);
	    
//	    List<LocalTime> partitionTimes = new ArrayList<LocalTime>();
//	    for(int i = 0 ; i<n ;i++) {
//	    	startTime = startTime.plus(piece.abs().multipliedBy(i));
//	    	partitionTimes.add(startTime);
//	    }
	   // System.out.println(partitionTimes);
	    
	    System.out.println(Arrays.toString(partitionTimes));
}

public static String[] getHourMin(String slot) {
	 String[] splitArr = slot.split(":");
	 return splitArr;
}

private static String trimLeadingZeroes(String inputStringWithZeroes){
    final Integer trimZeroes = Integer.parseInt(inputStringWithZeroes);
    return trimZeroes.toString();
}
}
