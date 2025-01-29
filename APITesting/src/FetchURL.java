import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import io.restassured.response.Response;

public class FetchURL {
	@Test
	public void fetchGetRequest() {
		// Set the base URL for the API
		RestAssured.baseURI = "http://ip.jsontest.com/";

		// Send GET request and retrieve the response
		Response response = given().get();

		// Verify the response status code
		assertEquals(200, response.getStatusCode());

		// Get the response body as a string
		String responseBody = response.getBody().asString();

		// Print the response output to the console
		System.out.println(responseBody);

		// Extract specific field from the JSON response
		String ipAddress = response.jsonPath().getString("ip");

		// Print the extracted field to the console
		System.out.println("IP Address: " + ipAddress);
	}
}
