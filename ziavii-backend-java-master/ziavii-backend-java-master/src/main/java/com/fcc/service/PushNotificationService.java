package com.fcc.service;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fcc.model.PushNotificationRequest;

@Service
public class PushNotificationService {

	@Value("${server.key}")
    private String serverKey;
	
	public void sendNotification(JSONObject dataObject, String deviceTiken) throws ClientProtocolException, IOException {
		
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost("https://fcm.googleapis.com/fcm/send");
		post.setHeader("Content-type", "application/json");
		post.setHeader("Authorization", "key="+serverKey);

		JSONObject message = new JSONObject();
		message.put("to", deviceTiken);
		message.put("priority", "high");

//		JSONObject data = new JSONObject();
//		data.put("title", pushObj.getTitle());
//		data.put("body", pushObj.getBody());
//		data.put("", value)

		message.put("data", dataObject);

		post.setEntity(new StringEntity(message.toString(), "UTF-8"));
		HttpResponse response = client.execute(post);
		System.out.println(response);
		System.out.println(message);
	}
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		JSONObject dataObject = new JSONObject();
		PushNotificationService pn = new PushNotificationService();
		pn.sendNotification(dataObject,"cDPkFiNiREmZB5X2DZTWEG:APA91bGgFeG9ZKJQWXY7eCls8suuPhBQ8r9OBz0B3Kj-6MD3lSXymmpvRttczo-WKh5S5qwLbI2oF4i7FLQLiOF-7D6bfggqoIP-GRVaRn5eO7L1EIFmh7WHHRTCFnqeCWWqCYSgfSLP");
	}
}
