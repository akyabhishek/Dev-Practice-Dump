package com.fcc.controller;
import static org.springframework.http.ResponseEntity.ok;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.omg.CORBA_2_3.portable.InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;

import com.fcc.domain.Coupon;
import com.fcc.domain.Customer;
import com.fcc.domain.Vendor;
import com.fcc.exception.ApiErrorCode;
import com.fcc.exception.GojoException;
import com.fcc.exception.InvalidRequestParameterException;
import com.fcc.exception.ResourceNotFoundException;
import com.fcc.model.CommonDto;
import com.fcc.model.CouponDto;
import com.fcc.repository.CustomerRepo;
import com.fcc.service.CouponDaoService;
import com.fcc.service.CustomerDaoService;
import com.fcc.service.DeviceMetaDataService;
import com.fcc.service.SimpleEmailDaoServie;
import com.fcc.service.VendorDaoService;
import com.fcc.util.CommonUtils;
import com.fcc.util.EmailUtils;


@RestController
@RequestMapping("/api/")
public class CommonController {
	
	@Autowired
	CustomerDaoService customerDaoService;
	@Autowired
    VendorDaoService vendorDaoService;
	@Autowired
	CouponDaoService couponDaoService;
	@Autowired
	DeviceMetaDataService deviceMetaDataService;
	@Autowired
	SimpleEmailDaoServie simpleEmailDaoServie;
//	@Autowired
//	private EmailUtils emailService;
	@Value("${server.contextPath}")
	private String contextPath;
	
	@PostMapping("user/login")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<CommonDto> userLogin(@Valid @RequestBody CommonDto commonDto) throws Exception {
		System.out.println("Context Path " +contextPath);
		CommonDto userInfo = null;
		Customer customer = null;
		Vendor vendor = null;
		Vendor vendorTemp = null;
		Customer customerTemp = null;
		String filterFlag = "";
		if(commonDto.getType() == null || commonDto.getType().isEmpty()) {
			throw new InvalidRequestParameterException(" User type is missing");
		}
		
		switch(commonDto.getType()) {
		
		case "CUSTOMER":
			if ((commonDto.getUserName() == null || commonDto.getUserName().isEmpty() || commonDto.getPassword() == null || commonDto.getPassword().isEmpty())&&(commonDto.getMobile() == null || commonDto.getMobile().isEmpty() || commonDto.getMobile() == null || commonDto.getMobile().isEmpty())) {
			throw new GojoException(ApiErrorCode.INVALID_INPUT +" Either username or mobile number is missing");
		    }
			if(!(commonDto.getUserName() == null|| commonDto.getUserName().isEmpty())) {
				filterFlag = "EMAIL";
				customer = customerDaoService.findUserByEmail(commonDto.getUserName());
				customerTemp = customerDaoService.findCustomerByTempEmail(commonDto.getUserName());
			}
			if(!(commonDto.getMobile() == null|| commonDto.getMobile().isEmpty())) {
				filterFlag = "MOBILE";
				customer = customerDaoService.findUserByMobile(commonDto.getMobile());
				customerTemp = customerDaoService.findCustomerByTempEmail(commonDto.getMobile());
			}
      		//Customer customer = customerDaoService.findUserByEmail(commonDto.getUserName());
			if (customer == null && customerTemp == null) {
				throw new ResourceNotFoundException("User not found");
			}
			else if(customer!=null && customerTemp == null) {
				if (!customer.getPassword().equals(commonDto.getPassword())) {
					throw new GojoException(ApiErrorCode.INCORRECT_CREDENTIALS +" Either username/mobile or password is incorrect");
				}
				deviceMetaDataService.saveLoginDetails(commonDto, customer.getCustomerId());
				userInfo = customerDaoService.getUserInfo(commonDto,filterFlag,false);
				
				if(customer.getTempMobile() == null && customer.isUpdate() == false) {
					if(userInfo.getIsOtpVerified() == false){
						userInfo.setSuccess(false);
						userInfo.setStatusDetail("Customer found but otp is unverified");
					}else{
						userInfo.setSuccess(true);
						userInfo.setStatusDetail("User found");
					}
				}else if((customer.getTempMobile() == null || customer.getTempMobile().isEmpty()) && customer.isUpdate() == true) {
					userInfo.setSuccess(true);
					userInfo.setStatusDetail("User found");
				}else
				{
					userInfo.setSuccess(true);
					userInfo.setStatusDetail("You have logged in using old Mobile Number. Please verify your new Mobile Number and login");
					
					
				}
				
				
				
				
				
			}else if(customer == null && customerTemp!=null) {
				userInfo = new CommonDto();
				userInfo.setSuccess(true);
				userInfo.setStatusDetail("Verify your account for this Mobile Number. Check your inbox for otp and verify");
			}
			
			//call user dao
			break;
			
		case "VENDOR":
			
			if ((commonDto.getUserName() == null || commonDto.getUserName().isEmpty() || commonDto.getPassword() == null || commonDto.getPassword().isEmpty())&&(commonDto.getMobile() == null || commonDto.getMobile().isEmpty() || commonDto.getMobile() == null || commonDto.getMobile().isEmpty())) {
				throw new GojoException(ApiErrorCode.INVALID_INPUT +" Either username or mobile number is missing");
			    }
			if(!(commonDto.getUserName() == null|| commonDto.getUserName().isEmpty())) {
				filterFlag = "EMAIL";
				vendor = vendorDaoService.findVendorByEmail(commonDto.getUserName());
				vendorTemp = vendorDaoService.findVendorByTempEmail(commonDto.getUserName());
			}
			if(!(commonDto.getMobile() == null|| commonDto.getMobile().isEmpty())) {
				filterFlag = "MOBILE";
				vendor = vendorDaoService.findVendorByMobile(commonDto.getMobile());
				vendorTemp = vendorDaoService.findVendorByTempEmail(commonDto.getMobile());
			}
	      		
				if (vendor == null && vendorTemp == null) {
					throw new ResourceNotFoundException("User not found");
				}else if(vendor!=null && vendorTemp == null) {
					if (!vendor.getPassword().equals(commonDto.getPassword())) {
						throw new GojoException(ApiErrorCode.INCORRECT_CREDENTIALS +" Either username/mobile or password is incorrect");
					}
					
					userInfo = vendorDaoService.getUserInfo(commonDto,filterFlag,false);
					System.out.println("userinfi detail" +userInfo);

					deviceMetaDataService.saveLoginDetails(commonDto, vendor.getvendorId());
					if(vendor.getTempMobile() == null && vendor.isUpdate() == false) {
						if(userInfo.getRegisterStep().equals("1")){
							userInfo.setAccountStatus("2");
							userInfo.setSuccess(false);
							userInfo.setStatusDetail("Vendor found but steps not completed");
						}else if(userInfo.getIsOtpVerified() == false) {
							userInfo.setAccountStatus("1");
							userInfo.setSuccess(false);
							userInfo.setStatusDetail("Verify your OTP");
						}
						else {
							if(userInfo.getIsPaymentSuccess() == false) {
								userInfo.setAccountStatus("1");
								userInfo.setSuccess(false);
								userInfo.setStatusDetail("Please Make payment");
								
							}else {

								userInfo.setAccountStatus("0");
								userInfo.setSuccess(true);
								userInfo.setStatusDetail("User found");
							}
						}
					}else if((vendor.getTempMobile() == null || vendor.getTempMobile().isEmpty()) && vendor.isUpdate() == true) {
						userInfo.setSuccess(true);
						userInfo.setStatusDetail("User found");
					}else
					{
						userInfo.setSuccess(true);
						userInfo.setStatusDetail("You have logged in using old Mobile Number. Please verify your new Mobile Number and login");
						
						
					}
					
				}else if(vendor == null && vendorTemp!=null) {
					userInfo = new CommonDto();
					userInfo.setSuccess(false);
					userInfo.setStatusDetail("Verify your account for this Mobile Number. Check your inbox for otp and verify");
				}
				
			//call vendor dao
			
				
				
			break;
		}
		
		
		
//		if (commonDto.getUserName() == null || commonDto.getUserName().isEmpty() || commonDto.getPassword() == null || commonDto.getPassword().isEmpty()) {
//			throw new GojoException(ApiErrorCode.INVALID_INPUT, "Either username or passowrd is missing");
//		}
//		
//		User user = userDaoService.findUserByEmail(userDto.getEmail());
//		if (user == null) {
//			throw new GojoException(ApiErrorCode.RESOURCE_NOT_FOUND, "User not found");
//		}
//		if (!user.getPassword().equals(userDto.getPassword())) {
//			throw new GojoException(ApiErrorCode.INCORRECT_CREDENTIALS, "Either username or password is incorrect");
//		}		
//		if(!user.isEmailVerified()) {
//			throw new GojoException(ApiErrorCode.USER_EMAIL_NOT_VERIFIED, "User email is not verified.");
//		}
//		if(!user.isActive()) {
//			throw new GojoException(ApiErrorCode.RESOURCE_INACTIVE, "User is inactive.");
//		}
//		UserDto userDetails = null;
//		if(userDaoService.validateUserSubscription(user) == true) {
//			userDetails = new UserDto(user.getUserId(), null, null, null, null, null, null,
//					CommonUtils.formatDate(user.getRegistrationDate(), Constants.DATE_FORMAT_ddMMMyyyy),
//					CommonUtils.formatDate(user.getTrialExpiryDate(), Constants.DATE_FORMAT_ddMMMyyyy),
//					user.isTrialActive(), user.isActive(), null, null, null, user.getClassId().getClassId(),
//					user.isEmailVerified(), null, null,null);
//			userDetails.setSuccess(true);
//			userDetails.setStatusDetail("User found");
//		}
//		/*if (user.isTrialActive() && !user.getTrialExpiryDate().after(new Date())) {
//			userDetails = new UserDto(user.getUserId(), null, null, null, null, null, null,
//					CommonUtils.formatDate(user.getRegistrationDate(), Constants.DATE_FORMAT_ddMMMyyyy),
//					CommonUtils.formatDate(user.getTrialExpiryDate(), Constants.DATE_FORMAT_ddMMMyyyy),
//					user.isTrialActive(), user.isActive(), null, null, null, user.getClassId().getClassId(),
//					user.isEmailVerified(), null, null);
//			userDetails.setSuccess(true);
//			userDetails.setStatusDetail("User found");
//		} else if(!user.isTrialActive()) { //TODO: ADd check for active subcription
//			throw new GojoException(ApiErrorCode.NO_ACTIVE_SUBSCRIPTION, "No Active Subscription for this user.");
//		}
//		
//		else {
//			//TODO: Check for user's active subscriptions
//			userDetails = new UserDto(user.getUserId(), null, null, null, null, null, null,
//					CommonUtils.formatDate(user.getRegistrationDate(), Constants.DATE_FORMAT_ddMMMyyyy),
//					CommonUtils.formatDate(user.getTrialExpiryDate(), Constants.DATE_FORMAT_ddMMMyyyy),
//					user.isTrialActive(), user.isActive(), null, null, null, user.getClassId().getClassId(),
//					user.isEmailVerified(), null, null);
//			userDetails.setSuccess(true);
//			userDetails.setStatusDetail("User found");
//		}*/
//		
		return ResponseEntity.status(HttpStatus.OK).body(userInfo);
	}
	

	@PostMapping("user/forgotPassword")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Map<String,Object>> forgotPassword(@Valid @RequestBody CommonDto commonDto){
		
		Map<String, Object> model = new HashMap<>();
		
		if((commonDto.getRequestType() == null || commonDto.getRequestType().isEmpty())){
			throw new InvalidRequestParameterException(" Request type is missing");
		}
		if((commonDto.getType() == null || commonDto.getType().isEmpty())){
			throw new InvalidRequestParameterException(" User type is missing");
		}
		
		switch(commonDto.getRequestType()) {
		case "1":
			//Check for userType and validate the mobile number
			if(commonDto.getType().equals("CUSTOMER")) {
				if(commonDto.getMobile() == null || commonDto.getMobile().isEmpty()) {
					throw new InvalidRequestParameterException(" Mobile number is missing");
				}
				
				Customer customer = customerDaoService.findUserByMobile(commonDto.getMobile());
				if(customer == null) {
					throw new ResourceNotFoundException("Invalid mobile number");
				}
				
				
				
				model.put("statusDetail", "User found");
				model.put("success", true);
				model.put("type","CUSTOMER");
				model.put("userId", customer.getCustomerId());
				
				/*
				 * if(commonDto.getPassword() == null || commonDto.getPassword().isEmpty()) {
				 * throw new InvalidRequestParameterException("Password is missing"); }
				 * 
				 * customer.setPassword(commonDto.getPassword());
				 * customerDaoService.updatePassword(customer);
				 */
			}
			if(commonDto.getType().equals("VENDOR")) {
				if(commonDto.getMobile() == null || commonDto.getMobile().isEmpty()) {
					throw new InvalidRequestParameterException(" Mobile number is missing");
				}
				Vendor vendor = vendorDaoService.findVendorByMobile(commonDto.getMobile());
				if(vendor == null) {
					throw new ResourceNotFoundException("Invalid mobile number");
				}
				
				
				
				model.put("statusDetail", "User found");
				model.put("success", true);
				model.put("type","VENDOR");
				model.put("userId", vendor.getvendorId());
				
				
			}
			
			break;
		case "2":
			
            if(commonDto.getUserId() == null) {
            	throw new InvalidRequestParameterException("User id is missing");
			}
            if(commonDto.getPassword() == null || commonDto.getPassword().isEmpty()) {
            	throw new InvalidRequestParameterException("Password is missing");
			}
			
			if(commonDto.getType().equals("CUSTOMER")) {
				Customer customer = customerDaoService.findUserById(commonDto.getUserId());
				if(customer == null) {
					throw new ResourceNotFoundException("Invalid User");
				}
				
				customer.setPassword(commonDto.getPassword());
				customerDaoService.updatePassword(customer);
				
				model.put("statusDetail", "Password reset successfull");
				model.put("success", true);
				model.put("type","CUSTOMER");
				model.put("userId", customer.getCustomerId());
			}
			if(commonDto.getType().equals("VENDOR")) {
				Vendor vendor = vendorDaoService.findVendorById(commonDto.getUserId());
				if(vendor == null) {
					throw new ResourceNotFoundException("Invalid User");
				}
				
				vendor.setPassword(commonDto.getPassword());
				vendorDaoService.updatePassword(vendor);
				
				model.put("statusDetail", "Password reset successfull");
				model.put("success", true);
				model.put("type","VENDOR");
				model.put("userId", vendor.getvendorId());
			}
			
			break;
		}
		
		
		
//		
//		if((commonDto.getUserName() == null || commonDto.getUserName().isEmpty())){
//			throw new InvalidRequestParameterException(" Username is missing");
//		}
//		
//		if(!(vendorDaoService.findVendorByEmail(commonDto.getUserName()) == null) ||!(customerDaoService.findUserByEmail(commonDto.getUserName()) == null) ) {
//			if(!(vendorDaoService.findVendorByEmail(commonDto.getUserName()) == null)) {
//				//Logic to send email with password
//				Vendor vendor = vendorDaoService.findVendorByEmail(commonDto.getUserName());
//				//emailService.sendSimpleMessage(commonDto.getUserName(), "Password", vendor.getPassword());
//				simpleEmailDaoServie.verifyEmail(commonDto.getUserName(),vendor.getPassword(),true);
//				response.setSuccess(true);
//				response.setStatusDetail("Password successfully sent to registered email");
//			}
//			
//			if(!(customerDaoService.findUserByEmail(commonDto.getUserName()) == null)) {
//				Customer customer = customerDaoService.findUserByEmail(commonDto.getUserName());
//				//emailService.sendSimpleMessage(commonDto.getUserName(), "Password", customer.getPassword());
//				simpleEmailDaoServie.verifyEmail(commonDto.getUserName(),customer.getPassword(),true);
//				response.setSuccess(true);
//				response.setStatusDetail("Password successfully sent to registered email");
//			}
//		}else {
//			throw new ResourceNotFoundException(" Invalid user");
//		}
		
		
		
		return ok(model);
	}
	
	
//	@PostMapping("user/verifyPayment")
//	@ResponseStatus(HttpStatus.OK)
//	public ResponseEntity<CommonDto> makepayment(@Valid @RequestBody CommonDto commonDto) throws IOException{
//		CommonDto response = new CommonDto();
//		if(commonDto.getType() == null || commonDto.getType().isEmpty()) {
//			throw new InvalidRequestParameterException(" User type is missing");
//		}
//	
//		if(commonDto.getUserId() == null) {
//			throw new InvalidRequestParameterException(" User id is missing");
//		}
//		
//		if(commonDto.getDeviceToken() == null || commonDto.getDeviceToken().isEmpty()) {
//			throw new InvalidRequestParameterException(" Device token is missing");
//		}
//		
//		if(commonDto.getDeviceType() == null || commonDto.getDeviceType().isEmpty()) {
//			throw new InvalidRequestParameterException(" Device type is missing");
//		}
//	
//		
//		if(commonDto.getType().equals("VENDOR")) {
//			Vendor vendor = vendorDaoService.findVendorById(commonDto.getUserId());
//			if(vendor == null) {
//				throw new ResourceNotFoundException(" Invalid user");
//			}
//
//				vendor.setPaymentSuccess(true);
//				if(vendor.getTempMobile() != null) {
//					vendor.setMobile(vendor.getTempMobile());
//					vendor.setTempMobile(null);
//				}
//					vendorDaoService.update(vendor);
//				
//				
//				deviceMetaDataService.saveLoginDetails(commonDto, vendor.getvendorId());
//				response = vendorDaoService.getVendorDetailsForpayment(vendor.getvendorId());
//				response.setIsPaymentSuccess(true);
//				response.setStatusDetail("Account activated successfully");
//				response.setSuccess(true);
//			}
//
//		
//		return ResponseEntity.status(HttpStatus.OK).body(response);
//	}
	
	
	
	@PostMapping("user/resendOtp")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<CommonDto> resendOtp(@Valid @RequestBody CommonDto commonDto){
		CommonDto response = new CommonDto();
		if(commonDto.getType().equals("CUSTOMER")) {
			Customer customer = customerDaoService.findUserById(commonDto.getUserId());
			if(!(customer == null)) {
				String otp = CommonUtils.getRandDigitsOtp();
				customerDaoService.persistOtpInDb(commonDto.getUserId(), otp);
				String email = customer.getTempMobile()!=null?customer.getTempMobile() : customer.getEmail();
				//emailService.sendSimpleMessage(email, "One Time Password", otp);
				simpleEmailDaoServie.verifyEmail(email,String.valueOf(otp),false);
				response.setStatusDetail("One time password resent");
				response.setOneTimePassword(otp);
				response.setSuccess(true);
			}else {
				throw new ResourceNotFoundException(" Invalid user");
			}
			
		}
		
		if(commonDto.getType().equals("VENDOR")) {
			Vendor vendor = vendorDaoService.findVendorById(commonDto.getUserId());
			String email = vendor.getTempMobile()!=null?vendor.getTempMobile():vendor.getEmail();
			if(!(vendor == null)) { 
				String otp = CommonUtils.getRandDigitsOtp();
				vendorDaoService.persistOtpInDb(commonDto.getUserId(), otp);
				//emailService.sendSimpleMessage(email, "One Time Password", otp);
				simpleEmailDaoServie.verifyEmail(email,String.valueOf(otp),false);
				response.setStatusDetail("One time password resent");
				response.setOneTimePassword(otp);
				response.setSuccess(true);
			}else {
				throw new ResourceNotFoundException(" Invalid user");
			}
			
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PostMapping("user/verifyOtp")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<CommonDto> verifyOtp(@Valid @RequestBody CommonDto commonDto) throws IOException{
		CommonDto response = new CommonDto();
		if(commonDto.getType() == null || commonDto.getType().isEmpty()) {
			throw new InvalidRequestParameterException(" User type is missing");
		}
		if(commonDto.getOneTimePassword() == null || commonDto.getOneTimePassword().isEmpty()) {
			throw new InvalidRequestParameterException(" One Time Password is missing");
		}
		if(commonDto.getUserId() == null) {
			throw new InvalidRequestParameterException(" User id is missing");
		}
		
		if(commonDto.getDeviceToken() == null || commonDto.getDeviceToken().isEmpty()) {
			throw new InvalidRequestParameterException(" Device token is missing");
		}
		
		if(commonDto.getDeviceType() == null || commonDto.getDeviceType().isEmpty()) {
			throw new InvalidRequestParameterException(" Device type is missing");
		}
		if(commonDto.getType().equals("CUSTOMER")) {
			
			Customer customer = customerDaoService.findUserById(commonDto.getUserId());
			if(customer == null) {
				throw new ResourceNotFoundException(" Invalid user");
			}
//			
//			String storedOtp = customer.getOneTimePass();
//			Date oldTime = customer.getOtpCreatedTime();
//			if(!storedOtp.equals(commonDto.getOneTimePassword())){
//				throw new ResourceNotFoundException(" Invalid Otp");
//			}
//			
//			
//			long diff = CommonUtils.getTimeDiffInMin(oldTime);
//			if(diff>5) {
//				response.setStatusDetail("One time password expires! Kindly resend");
//				response.setSuccess(false);
//			}else {
				customer.setOtpVerified(true);
				customer.setOneTimePass(commonDto.getOneTimePassword());
				if(customer.getTempMobile() != null) {
					customer.setMobile(customer.getTempMobile());
					customer.setTempMobile(null);
				}
				customerDaoService.update(customer, null);
				deviceMetaDataService.saveLoginDetails(commonDto, customer.getCustomerId());
				response = customerDaoService.getCustomerDetailsForOtpResponse(customer.getCustomerId());
				response.setIsOtpVerified(true);
				response.setStatusDetail("Otp Verified! PLease make payment to activate Account ");
				response.setSuccess(true);
			}
//		}
		
		if(commonDto.getType().equals("VENDOR")) {
			Vendor vendor = vendorDaoService.findVendorById(commonDto.getUserId());
			if(vendor == null) {
				throw new ResourceNotFoundException(" Invalid user");
			}
//			
		/*	String storedOtp = vendor.getOneTimePass();
			Date oldTime = vendor.getOtpCreatedTime();
			if(!storedOtp.equals(commonDto.getOneTimePassword())){
				throw new GojoException(" Invalid Otp");
			}
			long diff = CommonUtils.getTimeDiffInMin(oldTime);
			if(diff>5) {
				response.setStatusDetail("One time password expires! Kindly resend");
				response.setSuccess(false);
			}else {  */
				vendor.setOtpVerified(true);
				/*new implementation for mobile based otp*/
				vendor.setOneTimePass(commonDto.getOneTimePassword());
				/*new implementation for mobile based otp*/
				if(vendor.getTempMobile() != null) {
					vendor.setMobile(vendor.getTempMobile());
					vendor.setTempMobile(null);
				}
				vendorDaoService.update(vendor);
				
				
			//	deviceMetaDataService.saveLoginDetails(commonDto, vendor.getvendorId());
				response = vendorDaoService.getVendorDetailsForOtpResponse(vendor.getvendorId());
				response.setIsOtpVerified(true);
				response.setStatusDetail("Account activated successfully");
				response.setSuccess(true);
			}
//		}
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PostMapping("coupon/applyCoupon/{couponId}")
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<Map<String, Object>> applyCoupon(@Valid @RequestBody CouponDto couponDto, @PathVariable("couponId") Integer couponId){
		
		if(couponDto.getCouponCode() == null || couponDto.getCouponCode().isEmpty() || couponDto.gettotalPrice() == null || couponDto.gettotalPrice().isEmpty()) {
			throw new InvalidRequestParameterException(" Coupon code or Total price is missing");
		}
		
		
		
		//Coupon coupon = couponDaoService.getCouponByCouponCode(couponDto.getCouponCode());
		Coupon coupon = couponDaoService.getCouponByCouponCodeAndCouponId(couponDto.getCouponCode(), couponId);
		if(coupon == null) {
			throw new ResourceNotFoundException(" Coupon does not exist");
		}
		
		boolean isCouponActive = CommonUtils.isCouponActive(coupon.getExpiryDate());
		if(isCouponActive == false) {
			throw new GojoException(ApiErrorCode.COUPON_EXPIRED+" Coupon is expired");
		}
		boolean isCouponValidForPrice = CommonUtils.isCouponInRange(couponDto.gettotalPrice(), coupon.getMinPrice());
		if(isCouponValidForPrice == false) {
			throw new InvalidRequestParameterException(ApiErrorCode.INVALID_INPUT+" Min should be " +coupon.getMinPrice());
		}
		
		//process the coupon data and return the processed price
		double processedPrice = couponDaoService.processCouponCode(Double.valueOf(couponDto.gettotalPrice()), coupon);
		
		System.out.println(processedPrice);
		Map<String, Object> model = new HashMap<>();
		model.put("statusDetail", "Coupon applied");
		model.put("success", true);
		model.put("payableAmount", processedPrice);
		
		return ok(model);
	}
	
	@PostMapping("user/updatePassword")
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<Map<String,Object>> changePassword(@Valid @RequestBody CommonDto commonDto){
		if(commonDto.getOldPassword() == null || commonDto.getOldPassword().isEmpty()) {
			throw new InvalidRequestParameterException(" Old password is mandatory");
		}
		
		if(commonDto.getUserId() == null) {
			throw new InvalidRequestParameterException(" Userid is missing");
		}
		
		if(commonDto.getType() == null || commonDto.getType().isEmpty()){
			throw new InvalidRequestParameterException(" Type is missing");
		}
		
		if(commonDto.getType().equals("VENDOR")) {
			Vendor vendor = vendorDaoService.findVendorById(commonDto.getUserId());
			if(vendor == null) {
				throw new ResourceNotFoundException(" Invalid vendor id");
			}
			if(!commonDto.getOldPassword().equals(vendor.getPassword())) {
				throw new InvalidRequestParameterException(" Invalid password");
			}
			vendor.setPassword(commonDto.getPassword());
			vendor.setLastUpdatedDate(new Date());
			vendor = vendorDaoService.updatePassword(vendor);
		}
		
        if(commonDto.getType().equals("CUSTOMER")) {
        	Customer customer = customerDaoService.findUserById(commonDto.getUserId());
        	if(customer == null) {
        		throw new ResourceNotFoundException(" Invalid customer id");
        	}
        	if(!commonDto.getOldPassword().equals(customer.getPassword())) {
				throw new InvalidRequestParameterException(" Invalid password");
			}
        	customer.setPassword(commonDto.getPassword());
        	customer.setLastUpdatedDate(new Date());
        	customer = customerDaoService.updatePassword(customer);
        	
		}
		
		
		
		
		Map<String, Object> model = new HashMap<>();
		model.put("statusDetail", "Password updated successfully");
		model.put("success", true);
		
		return ok(model);
	}
	
	
}
	
	
