package com.fcc.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.tomcat.util.bcel.classfile.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fcc.domain.Coupon;
import com.fcc.domain.Vendor;
import com.fcc.model.CouponDto;
import com.fcc.repository.CouponRepo;
import com.fcc.util.CommonUtils;
import com.fcc.util.Constants;

@Component
public class CouponDaoService {

	@Autowired
	CouponRepo couponRepo;
	public DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	public CouponDto saveCoupon(CouponDto couponDto, Vendor vendor) throws ParseException {
		//DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		Coupon coupon = new Coupon();
		coupon.setCouponCode(couponDto.getCouponCode());
		coupon.setPercentage(couponDto.getPercentage());
		coupon.setMaxPrice(couponDto.getMaxPrice());
		coupon.setMinPrice(couponDto.getMinPrice());
		coupon.setCreatedDate(new Date());
		// coupon.setExpiryDate(CommonUtils.getCouponExpiryDate());
		Date date = format.parse(couponDto.getExpiryDate());
		coupon.setExpiryDate(date);
		if (vendor != null) {
			coupon.setVendor(vendor);
		}
		coupon = couponRepo.save(coupon);
		couponDto.setCouponId(coupon.getCouponId());

		return couponDto;
	}

	public List<CouponDto> getAllCoupons(Vendor vendor) {
		List<Coupon> couponList;
		if (vendor == null) {
			couponList = couponRepo.findAll();
		} else {
			couponList = couponRepo.findByVendor(vendor);
		}

		// List<CouponDto> couponDtos = new ArrayList<CouponDto>();
		// CouponDto couponDto;

		return couponList.stream()
				.filter(coupon -> CommonUtils.isCouponActive(coupon.getExpiryDate()))
				.map(coupon -> {

			     CouponDto couponDto = new CouponDto();
			     couponDto.setCouponId(coupon.getCouponId());
			     couponDto.setCouponCode(coupon.getCouponCode());
			     couponDto.setPercentage(coupon.getPercentage());
			     couponDto.setExpiryDate(CommonUtils.formatDate(coupon.getExpiryDate(), Constants.DATE_FORMAT_ddMMMyyyy));
			     couponDto.setMinPrice(coupon.getMinPrice());
			     couponDto.setMaxPrice(coupon.getMaxPrice());
			// couponDto.setCreatedDate(coupon.getCreatedDate());
			// couponDtos.add(couponDto);

			    return couponDto;
		}).collect(Collectors.toList());

		/*
		 * mod on 06062021 replaced by stream for(Coupon coupon:couponList) {
		 * if(CommonUtils.isCouponActive(coupon.getExpiryDate())) { couponDto = new
		 * CouponDto(); couponDto.setCouponId(coupon.getCouponId());
		 * couponDto.setCouponCode(coupon.getCouponCode());
		 * couponDto.setPercentage(coupon.getPercentage());
		 * couponDto.setExpiryDate(CommonUtils.formatDate(coupon.getExpiryDate(),
		 * Constants.DATE_FORMAT_ddMMMyyyy));
		 * couponDto.setMinPrice(coupon.getMinPrice());
		 * couponDto.setMaxPrice(coupon.getMaxPrice());
		 * //couponDto.setCreatedDate(coupon.getCreatedDate());
		 * couponDtos.add(couponDto); }
		 * 
		 * }
		 */

		// return couponDtos;
	}

	public List<CouponDto> getAllCouponsByVendor(Vendor vendor) {
		return couponRepo.findByVendor(vendor).stream()
				.filter(coupon -> CommonUtils.isCouponActive(coupon.getExpiryDate())).map(coupon -> {
					CouponDto couponDto = new CouponDto();
					couponDto.setCouponId(coupon.getCouponId());
					couponDto.setCouponCode(coupon.getCouponCode());
					couponDto.setPercentage(coupon.getPercentage());
					couponDto.setExpiryDate(
							CommonUtils.formatDate(coupon.getExpiryDate(), Constants.DATE_FORMAT_ddMMMyyyy));
					couponDto.setMinPrice(coupon.getMinPrice());
					return couponDto;
				}).collect(Collectors.toList());

		// List<CouponDto> couponDtos = new ArrayList<CouponDto>();
		/*
		 * Mod on 06062021 replaced by Stream api CouponDto couponDto; for(Coupon
		 * coupon:couponList) { if(CommonUtils.isCouponActive(coupon.getExpiryDate())) {
		 * couponDto = new CouponDto(); couponDto.setCouponId(coupon.getCouponId());
		 * couponDto.setCouponCode(coupon.getCouponCode());
		 * couponDto.setPercentage(coupon.getPercentage());
		 * couponDto.setExpiryDate(CommonUtils.formatDate(coupon.getExpiryDate(),
		 * Constants.DATE_FORMAT_ddMMMyyyy));
		 * couponDto.setMinPrice(coupon.getMinPrice());
		 * //couponDto.setCreatedDate(coupon.getCreatedDate());
		 * couponDtos.add(couponDto); }
		 * 
		 * }
		 * 
		 * 
		 * return couponDtos;
		 */
	}

	public Coupon getCouponByCouponCode(String couponCode) {
		Coupon coupon = couponRepo.findByCouponCode(couponCode);
		return coupon;
	}

	public Coupon getCouponByCouponCodeAndCouponId(String couponCode, Integer couponId) {
		Coupon coupon = couponRepo.findByCouponCodeAndCouponId(couponCode, couponId);
		return coupon;
	}

	public double processCouponCode(double totalPrice, Coupon coupon) {

		double maxPrice = Double.valueOf(coupon.getMaxPrice());
		double percentage = Double.valueOf(coupon.getPercentage());
		double discountedRate = 100 - percentage;
		double discountedPrice = (percentage * totalPrice) / 100;
		if (discountedPrice > maxPrice) {
			discountedPrice = maxPrice;
		}

		return totalPrice - discountedPrice;
	}

	public void deleteCouponById(Coupon coupon) {
		String a = "j";
		couponRepo.delete(coupon);
	}

	public Coupon getCouponById(int id) {
		String a = "j";
		Optional<Coupon> coupon = couponRepo.findById(id);
		if (coupon.isPresent()) {
			return coupon.get();
		}
		return null;
	}
}
