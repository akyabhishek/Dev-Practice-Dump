package com.fcc.controller;

import static org.springframework.http.ResponseEntity.ok;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fcc.domain.Coupon;
import com.fcc.domain.Order;
import com.fcc.exception.ApiErrorCode;
import com.fcc.exception.GojoException;
import com.fcc.exception.InvalidRequestParameterException;
import com.fcc.exception.ResourceNotFoundException;
import com.fcc.model.AdminLoginDto;
import com.fcc.model.BookingDto;
import com.fcc.model.CommonDto;
import com.fcc.model.CouponDto;
import com.fcc.model.PushNotificationRequest;
import com.fcc.repository.OrdersRepo;
import com.fcc.service.CouponDaoService;
import com.fcc.service.CustomerDaoService;
import com.fcc.service.PushNotificationService;
import com.fcc.service.VendorDaoService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@RestController
@RequestMapping("/api/")
public class AdminController {

	@Autowired
	CouponDaoService couponDaoService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	PushNotificationService pushService;
	@Autowired
	OrdersRepo ordersRepo;
	@Autowired
	VendorDaoService vendorDaoService;
	@Autowired
	CustomerDaoService customerDaoService;
	public DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	@GetMapping("admin/getAllCoupons")
	public ResponseEntity<Map<String, Object>> getAllCoupons() throws FileNotFoundException, DocumentException{
		
		

		
		List<CouponDto> couponDtos = couponDaoService.getAllCoupons(null);
		
		Map<String, Object> model = new HashMap<>();
		
		model.put("coupons", couponDtos);
		model.put("statusDetail", couponDtos.isEmpty() ? "No active coupon found"
				: couponDtos.size() + " " + "Coupons found");
		model.put("success",couponDtos.isEmpty() ? false:true);
		
		return ok(model);
	}
	
	@PostMapping("admin/createCoupon")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<CouponDto> registerVendorStep2(@Valid @RequestBody CouponDto couponDto) throws ParseException{
		if(couponDto.getCouponCode() == null || couponDto.getCouponCode().isEmpty()) {
			throw new InvalidRequestParameterException(" Coupon code is missing");
		}
		
		if(couponDto.getMaxPrice() == null || couponDto.getMaxPrice().isEmpty()) {
			throw new InvalidRequestParameterException(" Max price is missing");
		}
		
		if(couponDto.getPercentage() == null || couponDto.getPercentage().isEmpty()) {
			throw new InvalidRequestParameterException(" Percentage is missing");
		}
		
		if(couponDto.getMinPrice() == null || couponDto.getMinPrice().isEmpty()) {
			throw new InvalidRequestParameterException(" Min price is missing");
		}
		
		if(couponDto.getExpiryDate() == null || couponDto.getExpiryDate().isEmpty()) {
			throw new InvalidRequestParameterException(" Expiry date is missing");
		}
		
		if(Integer.valueOf(couponDto.getMaxPrice()) < 1) {
			throw new InvalidRequestParameterException(" Max price cannot be less than 1");
		}
		if(Integer.valueOf(couponDto.getMinPrice()) < 1) {
			throw new InvalidRequestParameterException(" Min price cannot be less than 1");
		}
		
		
		
		
		CouponDto savedCouponDto = couponDaoService.saveCoupon(couponDto,null);
		
		savedCouponDto.setStatusDetail(messageSource.getMessage("coupon.created", null, Locale.US));
		savedCouponDto.setSuccess(true);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedCouponDto);
	}
	
	@PostMapping("admin/deletCouponById/{couponId}")
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<Map<String,Object>> deleteCouponById(@PathVariable int couponId){
		Coupon coupon = couponDaoService.getCouponById(couponId);
		if(coupon == null) {
			throw new ResourceNotFoundException(" No Coupon found with Id " +couponId);
		}
		
		Map<String, Object> model = new HashMap<>();
		
		couponDaoService.deleteCouponById(coupon);
		model.put("statusDetail", "Coupon deleted successfully");
		model.put("success",true);
		return ok(model);
		
	}
	
	@PostMapping("admin/searchUsers")
	public ResponseEntity<?> searchUsers(@RequestBody CommonDto searchDto){
		if(searchDto.getKeyword() == null || searchDto.getKeyword().isEmpty()) {
			throw new InvalidRequestParameterException("No keyword is present");
		}
		
		if(searchDto.getType() == null || searchDto.getType().isEmpty()) {
			throw new InvalidRequestParameterException("No type is present");
		}
		
		List<CommonDto> userInfo = searchDto.getType().equals("VENDOR")?vendorDaoService.getVendorBySearch(searchDto.getKeyword()):customerDaoService.getCustomerBySearch(searchDto.getKeyword());
		
		Map<String,Object> response = new HashMap();
		response.put("userInfo", userInfo);
		response.put("statusDetails", userInfo.size() +" "+String.valueOf(searchDto.getType().equals("VENDOR")?"vendors found":"customers found"));
		
		
		return ok(response);
	}
	
	@PostMapping("admin/login")
	public ResponseEntity<Map<String,Object>> login(@Valid @RequestBody AdminLoginDto adminLogin) throws ClientProtocolException, IOException{
		
	
		if((adminLogin.getUserName() == null || adminLogin.getUserName().isEmpty()) || (adminLogin.getPassword() == null || adminLogin.getPassword().isEmpty())) {
			throw new InvalidRequestParameterException("Either username or password is missing");
		}
		
		if(!adminLogin.getUserName().equalsIgnoreCase("admin") || !adminLogin.getPassword().equals("admin")) {
			throw new InvalidRequestParameterException("Either username or password is Incorrect");
		}
		
		Map<String, Object> model = new HashMap<>();
		model.put("statusDetail", "Logged in successfully");
		model.put("success",true);
		return ok(model);
	}
	
	@PostMapping("admin/getBookingsByDate")
	public ResponseEntity<Map<String,Object>> getBookingsByDate(@Valid @RequestBody BookingDto bookingDto) throws ClientProtocolException, IOException, NumberFormatException, ParseException{
		
	
		if((bookingDto.getStartDate() == null || bookingDto.getStartDate().isEmpty()) || (bookingDto.getEndDate() == null || bookingDto.getEndDate().isEmpty())) {
			throw new InvalidRequestParameterException("Either startDate or endDate is missing");
		}
		
		if(bookingDto.getStatus()== null || bookingDto.getStatus().isEmpty()) {
			throw new InvalidRequestParameterException("Status cannot be empty");
		}
		
	
		List<BookingDto> rangedOrders = vendorDaoService.getBookingsByDateAndVendor(format.parse(bookingDto.getStartDate()), format.parse(bookingDto.getEndDate()), Integer.valueOf(bookingDto.getStatus()));
		
		Map<String, Object> model = new HashMap<>();
		model.put("Booking Details", rangedOrders);
		model.put("statusDetail", "Total Bookings found " +rangedOrders.size());
		model.put("success",true);
		return ok(model);
	}
}
