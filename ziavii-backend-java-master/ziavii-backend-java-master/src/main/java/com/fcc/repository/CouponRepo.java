package com.fcc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fcc.domain.Coupon;
import com.fcc.domain.Vendor;



@Repository
public interface CouponRepo extends JpaRepository<Coupon, Integer> {

	Coupon findByCouponCode(String couponCode);
	
	List<Coupon> findByVendor(Vendor vendor);
	
	Coupon findByCouponCodeAndCouponId(String couponCode, Integer couponId);
	
	
	
}
