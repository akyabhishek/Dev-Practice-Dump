package com.fcc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fcc.domain.Customer;
import com.fcc.domain.Vendor;
import com.fcc.domain.VendorRatingMapping;
import com.fcc.domain.VendorRatings;
import com.fcc.exception.GojoException;
import com.fcc.exception.InvalidRequestParameterException;
import com.fcc.exception.ResourceNotFoundException;
import com.fcc.repository.CustomerRepo;
import com.fcc.repository.VendorRatingMappingRepo;
import com.fcc.repository.VendorRatingsRepo;

@Component
public class VendorRatingDaoServices {

	@Autowired
	VendorRatingsRepo vendorRatingsRepo;
	@Autowired
	CustomerRepo customerRepo;
	@Autowired
	VendorRatingMappingRepo vendorRatingMappingRepo;
	
	public double getAvgRatingByVendorId(Vendor vendor) {
		//double avgRating = vendorRatingsRepo.getAverageRatingByVendorId(vendorId);
		String ratings = "";
		double avgRating = 0;
		VendorRatings vendorRating = vendorRatingsRepo.findByVendor(vendor);
		double rating = vendorRating.getRating();
		int count = vendorRating.getCount();
		if(count>0) {
			avgRating = rating/count;
			ratings = String.format("%.2f", avgRating);
			avgRating = Double.valueOf(ratings);
		}
	
		return avgRating;
	}
	
	
	public void createDefaultRating(Vendor vendor) {
		VendorRatings vendorRating = new VendorRatings();
		vendorRating.setCreatedDate(new Date());
		vendorRating.setRating(0);
		vendorRating.setVendor(vendor);
		vendorRatingsRepo.save(vendorRating);
	}
	
	
	public VendorRatings updateRating(Vendor vendor, Customer customer, double rating) {
 		 VendorRatings vendorRating = vendorRatingsRepo.findByVendor(vendor);
 		 if(vendorRating == null) {
 			 throw new ResourceNotFoundException("No rating found for this vendor");
 		 }
 		 
 		 Integer rated = customer.getIsRated();
 		 VendorRatingMapping vendorRatingMapping = vendorRatingMappingRepo.findByVendorAndCustomer(vendor, customer);
 	     if(vendorRatingMapping != null) {
 	    	throw new InvalidRequestParameterException("Already rated");
 	     }
// 		 if(rated == 1) {
// 			 throw new InvalidRequestParameterException("Already rated");
// 		 }
 		double addedRating =  vendorRating.getRating() + rating;
		vendorRating.setRating(addedRating);
	    vendorRating.setVendor(vendor);
	    int ratingCount = vendorRating.getCount() + 1;
	    vendorRating.setCount(ratingCount);
	    vendorRating = vendorRatingsRepo.save(vendorRating);
	    
	    //Save rating mapping for a customer and vendor
 		VendorRatingMapping vRatingMapping = new VendorRatingMapping();
 		vRatingMapping.setVendor(vendor);
 		vRatingMapping.setCustomer(customer);
 		vendorRatingMappingRepo.save(vRatingMapping);
	   
	    //customer.setIsRated(1);
	    // customerRepo.save(customer);
		 //VendorRatings vendorRating = null;
		 
		 
//		if(vendorRatings.size() == 1 && vendorRatings.get(0).getRating() == 0 ) {
//			vendorRating = vendorRatings.get(0);
//			vendorRating.setRating(rating);
//			vendorRating.setVendor(vendor);
//			//vendorRating.setCustomer(customer);
//			vendorRatingsRepo.save(vendorRating);
//		}
//		else {
//			vendorRating = new VendorRatings();
//			vendorRating.setCreatedDate(new Date());
//			vendorRating.setRating(rating);
//			vendorRating.setVendor(vendor);
//			//vendorRating.setCustomer(customer);
//			vendorRating = vendorRatingsRepo.save(vendorRating);
//		}
		
		return vendorRating;
		
	}
	
//	public VendorRatings getVendorRatingByVendor(Vendor vendor) {
//		
//	}
//	
//	public double updateVendorRating(Vendor vendor, Customer customer) {
//		
//	}
}
