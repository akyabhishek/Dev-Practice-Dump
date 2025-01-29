package com.fcc.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.fcc.domain.Customer;
import com.fcc.domain.LoginRecord;
import com.fcc.domain.UserMedia;
import com.fcc.domain.Vendor;
import com.fcc.exception.ResourceNotFoundException;
import com.fcc.model.CommonDto;
import com.fcc.model.CustomerDto;
import com.fcc.model.UserMediaDto;
import com.fcc.repository.CustomerRepo;
import com.fcc.repository.LoginRecordRepo;
import com.fcc.repository.MasterGenderRepo;
import com.fcc.repository.UserMediaRepo;
import com.fcc.repository.VendorRepo;
import com.fcc.util.CommonUtils;

@Component
public class CustomerDaoService {

	@Autowired
	CustomerRepo customerRepo;
	@Autowired
	VendorRepo vendorRepo;
	@Autowired
	LoginRecordRepo loginRecordRepo;
	@Autowired
	UserMediaDaoService userMediaDaoService;
	@Autowired
	UserMediaRepo userMediaRepo;
	@Autowired
	MasterGenderRepo masterGenderRepo;
	@Autowired
	MasterGenderDaoService masterGenderDaoService;
	
	@Value("${server.contextPath}")
	private String contextPath;
	@Autowired
	CommonUtils commonUtils;

	public CustomerDto save(CustomerDto customerDto, MultipartFile[] images) throws Exception {
		Customer customer = new Customer();
		try {
			customer.setName(customerDto.getName());
			customer.setEmail(customerDto.getEmail());
			customer.setMobile(customerDto.getMobile());
			customer.setType(customerDto.getType());
			customer.setPassword(customerDto.getPassword());
			if(masterGenderDaoService.getGenderById(customerDto.getGenderId()) == null) {
				throw new ResourceNotFoundException("Invalid gender selection");
			}
			customer.setGender(masterGenderDaoService.getGenderById(customerDto.getGenderId()));
			customer.setCreatedDate(new Date());
			customer = customerRepo.save(customer);
			customerDto.setCustomerId(customer.getCustomerId());
			for (MultipartFile image : images) {
				String imageName = commonUtils.saveImages(image, customerDto.getName().replaceAll("\\s+","-"));
				userMediaDaoService.saveImagesPathAndName(customerDto.getCustomerId(), customerDto.getType(),
						imageName);
				// save name and url in usermedia table
				// store image in folder
			}
			

		}

		catch (Exception e) {
			throw new Exception("Error Occured while saving User data: " + e.getMessage());
		}
		return customerDto;
	}

	public Customer findUserByEmail(String email) {
		Optional<Customer> customer = customerRepo.findByEmail(email);
		if (customer.isPresent()) {
			return customer.get();
		}
		return null;
	}
	
	public Customer findCustomerByTempEmail(String mobile) {
		Optional<Customer> customer = customerRepo.findByTempMobile(mobile);
		if (customer.isPresent()) {
			return customer.get();
		}
		return null;
	}

	public Customer findUserByMobile(String mobile) {
		Optional<Customer> customer = customerRepo.findByMobile(mobile);
		if (customer.isPresent()) {
			return customer.get();
		}
		return null;
	}

	public Customer findUserById(Integer userId) {
		Optional<Customer> customer = customerRepo.findById(userId);
		if (customer.isPresent()) {
			return customer.get();
		}
		return null;
	}

	public CommonDto getUserInfo(CommonDto commonDto, String filterFlag, boolean isEmailUpdated) {
		// List<Vendor> vendorsList =
		// vendorRepo.findByLatititudeLongitude(commonDto.getLatitude(),
		// commonDto.getLongitude());
		Customer customer = null;

		UserMediaDto userMediaDto = new UserMediaDto();
		List<UserMediaDto> userMediaDtos = new ArrayList<UserMediaDto>();

		if (filterFlag.contentEquals("EMAIL")) {
			if(isEmailUpdated == false) {
				customer = findUserByEmail(commonDto.getUserName());
			}else {
				customer = findCustomerByTempEmail(commonDto.getUserName());
			}
		}
		if (filterFlag.contentEquals("MOBILE")) {
			customer = findUserByMobile(commonDto.getMobile());
		}

		if (customer.isOtpVerified() == true || (customer.isOtpVerified() == false && customer.getTempMobile()!=null)) {
	//		UserMedia userMedia = userMediaRepo.findByCustomer(customer);
	//		userMediaDto.setImageName(userMedia.getMediaName());
//			userMediaDto.setImageUrl(contextPath + userMedia.getMediaUrl());
//			userMediaDtos.add(userMediaDto);
//			commonDto.setUserImages(userMediaDtos);
			// set picturepath in response
			commonDto.setMobile(customer.getMobile());
			commonDto.setUserName(customer.getEmail());
			commonDto.setIsOtpVerified(customer.isOtpVerified());
			commonDto.setUserId(customer.getCustomerId());
			commonDto.setName(customer.getName());
			commonDto.setGender(customer.getGender().getGender());
			commonDto.setImages(userMediaDaoService.getImagesByCustomer(customer));

		} else {
			commonDto.setUserId(customer.getCustomerId());
			commonDto.setIsOtpVerified(customer.isOtpVerified());
		}

		// commonDto.setVendors(vendorsList);
		return commonDto;
	}

	public void persistOtpInDb(Integer customerId, String otp) {
		Optional<Customer> customer = customerRepo.findById(customerId);
		customer.get().setOneTimePass(otp);
		customer.get().setOtpCreatedTime(new Date());
		customerRepo.save(customer.get());
	}

	public void saveLoginDetails(CommonDto commonDto, String filterFlag) {
		LoginRecord loginRecord = new LoginRecord();
		switch (filterFlag) {
		case "EMAIL":
			loginRecord.setEmail(commonDto.getUserName());

			break;
		case "MOBILE":
			loginRecord.setMobile(commonDto.getMobile());
			break;
		}
		
		loginRecord.setDeviceToken(commonDto.getDeviceToken());
		loginRecord.setDeviceType(commonDto.getDeviceType());
		loginRecord.setPassword(commonDto.getPassword());
		loginRecord.setLoginDateTime(new Date());
		loginRecordRepo.save(loginRecord);
		System.out.println("Saved login info");

	}

	public Customer update(Customer customer, MultipartFile image) throws IOException {
		if(image != null) {
			if(!image.isEmpty()) {
				
				String imageName = commonUtils.saveImages(image, customer.getName().replaceAll("\\s+","-"));
				userMediaDaoService.saveImagesPathAndName(customer.getCustomerId(), customer.getType(),
						imageName);
				// save name and url in usermedia table
				// store image in folder
			
		}
		}
		
		
		
		return customerRepo.save(customer);
	}
	
	
	public CommonDto getCustomerDetailById(Customer customer) {
		CommonDto commonDto = new CommonDto();
		
		commonDto.setUserId(customer.getCustomerId());
		commonDto.setName(customer.getName());
		commonDto.setMobile(customer.getMobile());
		commonDto.setUserName(customer.getEmail());
		commonDto.setGender(customer.getGender().getGender());
		//commonDto.setImages(userMediaDaoService.getImagesByCustomer(customer));
		List<String> userImages = userMediaDaoService.getImagesByCustomer(customer);
		if(userImages.size() > 0) {
			commonDto.setImage(userMediaDaoService.getImagesByCustomer(customer).get(0));
		}
		
		
		return commonDto;
	}
	
	
	public Customer updatePassword(Customer customer) {
		return customerRepo.save(customer);
	}
	
	public CommonDto getCustomerDetailsForOtpResponse(Integer customerId) {
		Customer customer = findUserById(customerId);
		CommonDto customerInfo = new CommonDto();
		customerInfo.setUserName(customer.getEmail());
		customerInfo.setLatitude(customer.getLatitude());
		customerInfo.setLongitude(customer.getLongitude());
		customerInfo.setType("CUSTOMER");
		
		return getUserInfo(customerInfo, "EMAIL", false);
	}
	public List<CommonDto> getCustomerBySearch(String searchTerm) {
		List<Customer> customerList = customerRepo.searchCustomer(searchTerm);
		//getVendorDetailById();
		List<CommonDto> customerInfo = customerList.stream()
				.map(customer -> getCustomerDetailById(customer)						
						)
				
				.collect(Collectors.toList());
		
		return customerInfo;
	}
}
