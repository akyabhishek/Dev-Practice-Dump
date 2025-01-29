package com.fcc.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class DistanceCalculator {
		
	private static DecimalFormat df = new DecimalFormat("0.00");
		/**
		 * Haversine
			formula:	a = sin²(Δφ/2) + cos φ1 ⋅ cos φ2 ⋅ sin²(Δλ/2)
			c = 2 ⋅ atan2( √a, √(1−a) )
			d = R ⋅ c
			where	φ is latitude, λ is longitude, R is earth’s radius (mean radius = 6,371km);
			note that angles need to be in radians to pass to trig functions!
		 * @param lat1
		 * @param lat2
		 * @param lon1
		 * @param lon2
		 * @return distance in kilometer
		 */
		public static double distance(double lat1, double lat2, double lon1, double lon2) {

			double dLat = Math.toRadians(lat2 - lat1); 
	        double dLon = Math.toRadians(lon2 - lon1); 
	        DecimalFormat df = new DecimalFormat("#.####");	  
	        // convert to radians 
	        lat1 = Math.toRadians(lat1); 
	        lat2 = Math.toRadians(lat2); 
	  
	        // apply formulae 
	        double a = Math.pow(Math.sin(dLat / 2), 2) +  
	                   Math.pow(Math.sin(dLon / 2), 2) *  
	                   Math.cos(lat1) *  
	                   Math.cos(lat2); 
	        double rad = 6371; 
	        double c = 2 * Math.asin(Math.sqrt(a)); 
	        String distance = String.format("%.6f", rad * c); 
	        //c = Math.round(c);
	        //df.setRoundingMode(RoundingMode.DOWN);
	        return Double.valueOf(distance); 
		}

		// driver code
		public static void main(String[] args) {
			double lat1 = 26.858530;
			double lat2 = 26.846251;
			double lon1 = 80.917820;
			double lon2 = 80.949028;
			 df.setRoundingMode(RoundingMode.DOWN);
			System.out.println(df.format(distance(lat1, lat2, lon1, lon2) + " Km"));
		}
	}


