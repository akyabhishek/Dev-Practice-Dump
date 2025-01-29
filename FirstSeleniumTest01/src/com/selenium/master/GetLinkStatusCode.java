package com.selenium.master;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetLinkStatusCode {
	static int invalidLink;
	
	public static void verifyLink(String linkString) {
		try {
			URL url=new URL(linkString);
			HttpURLConnection urlConnection=(HttpURLConnection) url.openConnection();
			
			//define time out
			urlConnection.setConnectTimeout(50000);
			
			urlConnection.connect();
			
			if(urlConnection.getResponseCode()==200) {
				
				
			}else {
				invalidLink++;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	static void invalidLinkCount() {
		System.out.println("Total invalid link - "+invalidLink);
	}

}
