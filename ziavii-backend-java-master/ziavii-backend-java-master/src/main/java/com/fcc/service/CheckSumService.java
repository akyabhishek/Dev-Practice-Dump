package com.fcc.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.paytm.pg.merchant.PaytmChecksum;

@Service
public class CheckSumService {

	@Value("${merchant.id}")
	private String mid;
	@Value("${merchant.key}")
	private String mkey;

	public String getTransactionToken(String amount, String orderId, String vendorId) throws Exception {

		JSONObject paytmParams = new JSONObject();
		String responseData = "";

		JSONObject body = new JSONObject();
		body.put("requestType", "Payment");
		body.put("mid", mid);
		body.put("websiteName", "WEBSTAGING");
		body.put("orderId", orderId);
		body.put("callbackUrl", "https://merchant.com/callback");

		JSONObject txnAmount = new JSONObject();
		txnAmount.put("value", amount);
		txnAmount.put("currency", "INR");

		JSONObject userInfo = new JSONObject();
		userInfo.put("custId", vendorId);
		body.put("txnAmount", txnAmount);
		body.put("userInfo", userInfo);

		/*
		 * Generate checksum by parameters we have in body You can get Checksum JAR from
		 * https://developer.paytm.com/docs/checksum/ Find your Merchant Key in your
		 * Paytm Dashboard at https://dashboard.paytm.com/next/apikeys
		 */

		String checksum = PaytmChecksum.generateSignature(body.toString(), mkey);

		JSONObject head = new JSONObject();
		head.put("signature", checksum);

		paytmParams.put("body", body);
		paytmParams.put("head", head);

		String post_data = paytmParams.toString();

		/* for Staging */
		URL url = new URL(
				"https://securegw.paytm.in/theia/api/v1/initiateTransaction?mid=" + mid + "&orderId=" + orderId + "");

		/* for Production */
		// URL url = new
		// URL("https://securegw.paytm.in/theia/api/v1/initiateTransaction?mid=YOUR_MID_HERE&orderId=ORDERID_98765");

		try {
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);

			DataOutputStream requestWriter = new DataOutputStream(connection.getOutputStream());
			requestWriter.writeBytes(post_data);
			requestWriter.close();

			InputStream is = connection.getInputStream();
			BufferedReader responseReader = new BufferedReader(new InputStreamReader(is));
			if ((responseData = responseReader.readLine()) != null) {
				System.out.println("Response: " + responseData);
			}
			responseReader.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		
		Object  obj = new JSONParser().parse(responseData);
		org.json.simple.JSONObject main = (org.json.simple.JSONObject)obj;
		System.out.println(obj);
		org.json.simple.JSONObject refBody = 	(org.json.simple.JSONObject) main.get("body");
		String txToken = (String)refBody.get("txnToken");
		
		
		return txToken;
	}
}
