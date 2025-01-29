package com.fcc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fcc.domain.Customer;
import com.fcc.domain.UserMedia;
import com.fcc.domain.Vendor;

@Repository
public interface UserMediaRepo extends JpaRepository<UserMedia, Integer> {

	List<UserMedia> findByVendor(Vendor vendor);
	List<UserMedia> findByCustomer(Customer customer);
	UserMedia findByCustomerAndMediaName(Customer customer, String mediaName);
	UserMedia findByVendorAndMediaId(Vendor vendor, Integer mediaId);
	
	
}
