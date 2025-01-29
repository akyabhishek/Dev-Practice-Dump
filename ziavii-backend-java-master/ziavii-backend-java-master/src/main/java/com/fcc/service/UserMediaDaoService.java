package com.fcc.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.fcc.domain.Customer;
import com.fcc.domain.UserMedia;
import com.fcc.domain.Vendor;
import com.fcc.exception.ApiErrorCode;
import com.fcc.exception.GojoException;
import com.fcc.exception.ResourceNotFoundException;
import com.fcc.model.UserMediaDto;
import com.fcc.repository.UserMediaRepo;
import com.fcc.util.CommonUtils;

@Component
public class UserMediaDaoService {

	@Autowired
	UserMediaRepo userMediaRepo;
	@Autowired
	CustomerDaoService customerDaoService;
	@Autowired
	VendorDaoService vendorDaoService;
	@Value("${server.contextPath}")
	private String contextPath;
	@Autowired
	CommonUtils commonUtils;

	public UserMediaDto saveImagesPathAndName(Integer userId, String userType, String name) {
		UserMedia userMedia;
		UserMediaDto userMediaDto = new UserMediaDto();
		if (userType.equals("CUSTOMER")) {
			List<UserMedia> imagesList = userMediaRepo.findByCustomer(customerDaoService.findUserById(userId));
			if(imagesList.size() < 1) {
				userMedia = new UserMedia();
				userMedia.setCustomer(customerDaoService.findUserById(userId));
				
				userMedia.setMediaUrl("api/picture/" + name);
				userMedia.setMediaName(name);
				userMedia.setCreatedAt(new Date());
				userMedia = userMediaRepo.save(userMedia);
				userMediaDto.setUserMediaId(userMedia.getMediaId());
				userMediaDto.setImageUrl(userMedia.getMediaUrl());
			}else {
				userMedia = imagesList.get(0);
				userMedia.setLastUpdatedAt(new Date());
				userMedia.setMediaName(name);
				userMedia.setMediaUrl("api/picture/" + name);
				userMedia = userMediaRepo.save(userMedia);
				userMediaDto.setUserMediaId(userMedia.getMediaId());
				userMediaDto.setImageUrl(userMedia.getMediaUrl());
			}
			
		}
		if (userType.equals("VENDOR")) {
			userMedia = new UserMedia();
			userMedia.setVendor(vendorDaoService.findVendorById(userId));
			
			userMedia.setMediaUrl("api/picture/" + name);
			userMedia.setMediaName(name);
			userMedia.setCreatedAt(new Date());
			userMedia = userMediaRepo.save(userMedia);
			userMediaDto.setUserMediaId(userMedia.getMediaId());
			userMediaDto.setImageUrl(userMedia.getMediaUrl());
		}

		

		return userMediaDto;

	}

	public List<UserMediaDto> getImagesbyUserId(Integer userId, String type) {
		Vendor vendor = null;
		Customer customer = null;
		List<UserMedia> userImages = new ArrayList<UserMedia>();
		List<UserMediaDto> userMediaDtoList = new ArrayList<UserMediaDto>();
		UserMediaDto userMediaDto = null;
		if (type.equals("VENDOR")) {
			vendor = vendorDaoService.findVendorById(userId);
			if (vendor == null) {
				throw new GojoException(ApiErrorCode.RESOURCE_NOT_FOUND + " Invalid vendor Id");
			}

			userImages = userMediaRepo.findByVendor(vendor);
			for (UserMedia userMedia : userImages) {

				userMediaDto = new UserMediaDto();
				//userMediaDto.setImageName(userMedia.getMediaName());
				//userMediaDto.setUserMediaId(userMedia.getMediaId());
				userMediaDto.setImageId(userMedia.getMediaId());
				userMediaDto.setImageUrl(contextPath + userMedia.getMediaUrl());
				userMediaDtoList.add(userMediaDto);
			}
		}
		if (type.equals("CUSTOMER")) {
			customer = customerDaoService.findUserById(userId);
			//
		}

		return userMediaDtoList;
	}

	public String getImageByVenodorId(Vendor vendor) {
		List<UserMedia> userImages = new ArrayList<UserMedia>();
		userImages = userMediaRepo.findByVendor(vendor);

		if(userImages == null || userImages.size() < 1) {
			return "";
		}
		return userImages.get(0).getMediaUrl();
	}
	
	public List<String> getImagesByVendor(Vendor vendor) {
		List<UserMedia> userImages = new ArrayList<UserMedia>();
		userImages = userMediaRepo.findByVendor(vendor);
		List<String> images = new ArrayList<String>();
		for(UserMedia userImage : userImages) {
			images.add(contextPath + userImage.getMediaUrl());
		}
		
		return images;
	}
	
	
	
	public List<String> getImagesByCustomer(Customer customer) {
		List<UserMedia> userImages = new ArrayList<UserMedia>();
		userImages = userMediaRepo.findByCustomer(customer);
		List<String> images = new ArrayList<String>();
		for(UserMedia userImage : userImages) {
			images.add(contextPath + userImage.getMediaUrl());
		}
		
		return images;
	}
	
	public UserMedia updateCustomerMedia(MultipartFile image, Customer customer, String fileName) throws IOException {
		//UserMedia customerMedia = userMediaRepo.findByCustomerAndMediaName(customer, fileName);
		List<UserMedia> customerMediaList = userMediaRepo.findByCustomer(customer);
		UserMedia customerMedia =  customerMediaList.get(0);
		if(customerMedia == null) {
			throw new GojoException(" An error occured");
		}
		
		String picName = customerMedia.getMediaName().substring(0, customerMedia.getMediaName().lastIndexOf("."));
		
		String fullPicName = commonUtils.saveImages(image, picName);
		customerMedia.setLastUpdatedAt(new Date());
		customerMedia.setMediaName(fullPicName);
		customerMedia.setMediaUrl("images/" + fullPicName);
		UserMedia customerImagesUpdated = userMediaRepo.save(customerMedia);
		//userMediaDto.setUserMediaId(userMedia.getMediaId());
		return customerImagesUpdated;
	}
	
	
	public void deleteByImageAndVendorId(Vendor vendor, Integer imageId) {
		UserMedia vendorPic = userMediaRepo.findByVendorAndMediaId(vendor, imageId);
		if(vendorPic == null) {
			throw new ResourceNotFoundException("Image not found");
		}
		userMediaRepo.delete(vendorPic);
		commonUtils.deleteFile(vendorPic.getMediaName());
	}

}
