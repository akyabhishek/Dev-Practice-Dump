package com.fcc;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;

import com.paytm.pg.merchant.PaytmChecksum;

public class CheckSumHandler {

	public static void main(String[] args) throws Exception {
		String MID = "tRFOqZ48004688438252";
		String MKEY = "tNQsnAQechoXlKcb";
		
		/*String orderId = "ORDERID_98760";*/
		String orderId = "14";
		
		JSONObject paytmParams = new JSONObject();

		JSONObject body = new JSONObject();
		body.put("requestType", "Payment");
		body.put("mid", MID);
		body.put("websiteName", "WEBSTAGING");
		body.put("orderId", orderId);
		body.put("callbackUrl", "https://merchant.com/callback");

		JSONObject txnAmount = new JSONObject();
		txnAmount.put("value", "1200.00");
		txnAmount.put("currency", "INR");

		JSONObject userInfo = new JSONObject();
		userInfo.put("custId", "1");
		body.put("txnAmount", txnAmount);
		body.put("userInfo", userInfo);

		/*
		* Generate checksum by parameters we have in body
		* You can get Checksum JAR from https://developer.paytm.com/docs/checksum/
		* Find your Merchant Key in your Paytm Dashboard at https://dashboard.paytm.com/next/apikeys
		*/

		String checksum = PaytmChecksum.generateSignature(body.toString(), MKEY);

		JSONObject head = new JSONObject();
		head.put("signature", checksum);

		paytmParams.put("body", body);
		paytmParams.put("head", head);

		String post_data = paytmParams.toString();

		/* for Staging */
		URL url = new URL("https://securegw.paytm.in/theia/api/v1/initiateTransaction?mid="+MID+"&orderId="+orderId+"");

		/* for Production */
		// URL url = new URL("https://securegw.paytm.in/theia/api/v1/initiateTransaction?mid=YOUR_MID_HERE&orderId=ORDERID_98765");

		try {
		 HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		 connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setDoOutput(true);

		DataOutputStream requestWriter = new DataOutputStream(connection.getOutputStream());
		requestWriter.writeBytes(post_data);
		requestWriter.close();
		String responseData = "";
		InputStream is = connection.getInputStream();
		BufferedReader responseReader = new BufferedReader(new InputStreamReader(is));
		if ((responseData = responseReader.readLine()) != null) {
			System.out.println("Response: " + responseData);
		}
		 responseReader.close();
		} catch (Exception exception) {
		exception.printStackTrace();
		}
	}
}
