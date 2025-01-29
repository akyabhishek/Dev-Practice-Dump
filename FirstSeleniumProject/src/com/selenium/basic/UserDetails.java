import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.By;

import java.io.FileReader;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class UserDetails {

    //use the below variable declarations
	public static WebDriver driver;
	public static JSONArray details;
	
	public static WebDriver createDriver() {    //Do not change the class name
		/* Create a driver. Assign it to static variable 'driver' and return it */
	    /* navigate to 'http://webapps.tekstac.com/FormRegistration/UserRegistration.html'  */
		
	}
	
	public JSONArray readFile(String fileName) {   //Do not change the method signature
	    //Retrieve the json file name passed as 'fileName' parameter. Parse the json, store it in 'details' array and return it
	}

	public String getName(int id) {   //Do not change the method signature
		//Parse the json to get 'Name' value. Return it.
        //Parameter 'id' is the id in the Registration.json
	}

	public String getEmail(int id) {   //Do not change the method signature
		//Parse the json to get 'Email' value. Return it.
        //Parameter 'id' is the id in the Registration.json
	}

	
	
	public String getPhone(int id) {     //Do not change the method signature   
		//Parse the json to get 'Phone' value. Return it.
        //Parameter 'id' is the id in the Registration.json
	}

	
	public String getAge(int id) {         //Do not change the method signature
		//Parse the json to get 'Age' value. Return it.
        //Parameter 'id' is the id in the Registration.json
	}

	
	public String getPassword(int id) {      //Do not change the method signature
	//Parse the json to get 'Password' value. Return it.
        //Parameter 'id' is the id in the Registration.json
	}

	
	public String getHobbies(int id) {        //Do not change the method signature
		//Parse the json to get 'Hobbies' value. Return it.
        //Parameter 'id' is the id in the Registration.json
	}

	
	public String getGender(int id) {          //Do not change the method signature
		//Parse the json to get 'Gender' value. Return it.
        //Parameter 'id' is the id in the Registration.json
	}

	public String getCity(int id) {          //Do not change the method signature
		//Parse the json to get 'City' value. Return it.
        //Parameter 'id' is the id in the Registration.json
	}

	public String getAddress(int id) {         //Do not change the method signature
		//Parse the json to get 'Address' value. Return it.
        //Parameter 'id' is the id in the Registration.json
	}

	public String getMessage() {           //Do not change the method signature
		//Find the web elements in the page. Assign the respective values from json to the form.
        //Submit the form 
        //Locate the 'Registered Successfully' message and return it
	}

	public static void main(String[] args) {
		UserDetails fieldLocator = new UserDetails();
		//Add required code
	}

}