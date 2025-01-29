package com.fcc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fcc.domain.Vendor;
import com.fcc.domain.VendorRatings;

@Repository
public interface VendorRatingsRepo extends JpaRepository<VendorRatings, Integer>{

	@Query("SELECT AVG(vr.rating) AS rating_average FROM VendorRatings vr where vendor_id = ?1")
	public double getAverageRatingByVendorId(Integer vendorId);
	
	VendorRatings findByVendor(Vendor vendor);
}

