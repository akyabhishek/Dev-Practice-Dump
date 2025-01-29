package com.fcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fcc.domain.Customer;
import com.fcc.domain.Vendor;
import com.fcc.domain.VendorRatingMapping;

@Repository
public interface VendorRatingMappingRepo extends JpaRepository<VendorRatingMapping, Integer>{

	VendorRatingMapping findByVendorAndCustomer(Vendor vendor, Customer customer);
}
