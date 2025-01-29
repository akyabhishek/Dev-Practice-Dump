package com.fcc.controller;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fcc.domain.Customer;
import com.fcc.domain.UserMedia;
import com.fcc.domain.Vendor;
import com.fcc.exception.ApiErrorCode;
import com.fcc.exception.GojoException;
import com.fcc.exception.InvalidRequestParameterException;
import com.fcc.exception.ResourceNotFoundException;
import com.fcc.model.CommonDto;
import com.fcc.model.CustomerDto;
import com.fcc.service.CustomerDaoService;
import com.fcc.service.SimpleEmailDaoServie;
import com.fcc.service.UserMediaDaoService;
import com.fcc.util.CommonUtils;
import com.fcc.util.EmailUtils;




@RestController
@RequestMapping("/api/")
public class CustomerController {

	@Autowired
	CustomerDaoService customerDaoService;
	@Autowired
	UserMediaDaoService customerMediaDaoService;
//	@Autowired
//	private EmailUtils emailService;
	@Autowired
	private MessageSource messageSource;
	@Value("${server.contextPath}")
	private String contextPath;
	@Autowired
	SimpleEmailDaoServie simpleEmailDaoServie;
	
	
	@RequestMapping(value="customer/updateCreateOrCustomer", method=RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<CustomerDto> registerCustomer(@RequestPart(required = false, value="images") MultipartFile[] images, 
			@Valid @RequestPart(required = true, value="customerDto") CustomerDto customerDto) throws Exception {
		
//		if (images.length == 0) {
//			 throw new GojoException(ApiErrorCode.INVALID_INPUT + " Image is missing");
//		}
		if (customerDto.getName() == null || customerDto.getName().isEmpty()) {
			throw new InvalidRequestParameterException(" Full Name is missing");
		}
		if (customerDto.getGenderId() == null) {
			throw new InvalidRequestParameterException(" Gender is missing");
		}
		
		if (customerDto.getEmail() == null || customerDto.getEmail().isEmpty() || customerDto.getPassword() == null || customerDto.getPassword().isEmpty()) {
			throw new InvalidRequestParameterException(" Either customer email or passowrd is missing");
		}
		if (customerDto.getMobile() == null || customerDto.getMobile().isEmpty()) {
			throw new InvalidRequestParameterException("Contact Number is missing");
		}				

		if (customerDto.getPassword() == null || customerDto.getPassword().isEmpty()) {
			throw new InvalidRequestParameterException(" password is missing");
		}

		
		if (customerDto.getType() == null || customerDto.getType().isEmpty()) {
			throw new InvalidRequestParameterException(" Type is missing");
		}
		
		/*Change for Mobile as a primary authenticator*/
		
		Customer customer = customerDaoService.findUserByMobile(customerDto.getMobile());
		if (customer != null) {
			throw new GojoException("Mobile number is already registered by another customer");
		}
		
		
//		Customer customer = customerDaoService.findUserByEmail(customerDto.getEmail());
//		if (customer != null) {
//			throw new GojoException(ApiErrorCode.EMAIL_ALREADY_EXIST + " Email is already registered by another customer");
//		}
		/*Change for Mobile as a primary authenticator*/

		CustomerDto customerSaved = customerDaoService.save(customerDto, images);
		//String oneTimePassword = CommonUtils.getRandDigitsOtp();
		//customerDaoService.persistOtpInDb(customerSaved.getCustomerId(),oneTimePassword);
		//simpleEmailDaoServie.otp = oneTimePassword;
		//simpleEmailDaoServie.verifyEmail(customerSaved.getEmail(),String.valueOf(oneTimePassword),false);
		//emailService.sendSimpleMessage(customerSaved.getEmail(), "One Time Password", oneTimePassword);
//		String encAuthHeader = CommonUtils.setAuhCredentials(String.valueOf(customerSaved.getCustomerId()), "", "USER",String.valueOf(System.currentTimeMillis() / 1000));
//		String verifyUrl = "http://localhost:8080/api/user/verifyEmail?q=" + URLEncoder.encode(encAuthHeader);
//		emailService.sendSimpleMessage(customerSaved.getEmail(), "Please verify your email address", verifyUrl);
		// send verification mail
		customerSaved.setStatusDetail(messageSource.getMessage("customer.created", null, Locale.US));
		customerSaved.setSuccess(true);
		return ResponseEntity.status(HttpStatus.CREATED).body(customerSaved);

	}
	
	@RequestMapping(value="customer/updateCreateOrCustomer", method=RequestMethod.PUT)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<CustomerDto> updateCustomer(@RequestPart(required = false, value="images") MultipartFile images, 
			@Valid @RequestPart(required = true, value="customerDto") CustomerDto customerDto) throws Exception{
		
		if(customerDto.getCustomerId() == null) {
			throw new InvalidRequestParameterException(" Customer id is missing");
		}
		Customer customer = customerDaoService.findUserById(customerDto.getCustomerId());
		
		String status = "Customer details updated successfully";
		boolean success = true;
		if(customer == null) {
			throw new ResourceNotFoundException(" Customer id"
					+ "doesnot exist");
		}
		
		
		
		if (customerDto.getName() == null || customerDto.getName().isEmpty()) {
			throw new InvalidRequestParameterException(" Full Name is missing");
		}
		if (customerDto.getMobile() == null || customerDto.getMobile().isEmpty()) {
			throw new InvalidRequestParameterException(" Mobile number is missing");
		}
		if (customerDto.getEmail() == null || customerDto.getEmail().isEmpty()) {
			throw new InvalidRequestParameterException(" Email address is missing");
		}
		
		if(!customer.getMobile().equals(customerDto.getMobile())) {
			if (customerDaoService.findUserByMobile(customerDto.getMobile()) != null) {
				throw new GojoException("Mobile is already registered by another customer");
			}
			customer.setTempMobile(customerDto.getMobile());
			//String oneTimePassword = CommonUtils.getRandDigitsOtp();
			//customerDaoService.persistOtpInDb(customer.getCustomerId(),oneTimePassword);
			//emailService.sendSimpleMessage(customer.getEmail(), "One Time Password", oneTimePassword);
			//simpleEmailDaoServie.verifyEmail(customer.getEmail(),String.valueOf(oneTimePassword),false);
			customer.setOtpVerified(false);
			customer.setUpdate(true);
			status = "Please verify your new Mobile number";
			success = false;
		}
		
		
		
		customer.setName(customerDto.getName());
		//customer.setMobile(customerDto.getMobile());
		customer.setEmail(customerDto.getEmail());
		customer.setLastUpdatedDate(new Date());
		Customer customerUpdated = customerDaoService.update(customer, images);

		//To be completed after discussion
		customerDto.setCustomerId(customerUpdated.getCustomerId());
		customerDto.setStatusDetail(status);
		customerDto.setSuccess(success);
		return ResponseEntity.status(HttpStatus.CREATED).body(customerDto);
	}
	
	
	
	
	@GetMapping("customer/customerById/{id}")
	public ResponseEntity<CommonDto> getCustomerDetailById(@PathVariable Integer id) throws ParseException{
		Customer customer = customerDaoService.findUserById(id);
		if(customer == null) {
			throw new ResourceNotFoundException(" Invalid customer Id");
		}
		
		CommonDto commonDto = customerDaoService.getCustomerDetailById(customer);
		commonDto.setStatusDetail("Customer found");
		commonDto.setSuccess(true);
		
		return ResponseEntity.status(HttpStatus.OK).body(commonDto);
		
	}
	
	@RequestMapping(value="customer/updateImage/{customerId}", method=RequestMethod.PUT, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<CommonDto> updateProfilePic(MultipartFile image, @PathVariable Integer customerId) throws Exception {
        CommonDto responseDto = new CommonDto();
		Customer customer = customerDaoService.findUserById(customerId);
		if(customer == null) {
			throw new ResourceNotFoundException(" Customer id"
					+ "doesnot exist");
		}
		
		if (image == null) {
			 throw new ResourceNotFoundException(" Image is missing");
		}
		UserMedia customerMedia = customerMediaDaoService.updateCustomerMedia(image, customer, image.getOriginalFilename());
		List<String> customerImages = new ArrayList<String>();
		customerImages.add(contextPath + customerMedia.getMediaUrl());
		
		responseDto.setStatusDetail("Customer picture updated successfully");
		responseDto.setSuccess(true);
		responseDto.setUserId(customerId);
		responseDto.setName(customer.getName());
		responseDto.setImages(customerImages);
		
		
	
		return ResponseEntity.status(HttpStatus.OK).body(responseDto);
		
		
	}
		
	
	
}
