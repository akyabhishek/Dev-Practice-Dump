import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class MainClass {
	public void Pass(){
        // Set the base URL for the API
        RestAssured.baseURI = "http://ip.jsontest.com/";

        // Create the request specification
        RequestSpecification requestSpec = RestAssured.given();

        try {
            // Send a POST request with an incorrect or lengthy request body or parameters
            Response response = requestSpec
                    .formParam("param1", "value1")
                    .formParam("param2", "value2")
                    .post();

            // Validate failure response
            int statusCode = response.getStatusCode();
            if (statusCode >= 400) {
                throw new RuntimeException("Request failed with status code: " + statusCode);
            }

            // Print the response output to the console
            String responseBody = response.getBody().asString();
            System.out.println(responseBody);
        } catch (Exception e) {
            // Handle the exception
            e.printStackTrace();
        }
    }
}
