package com.fcc.controller;

import static org.springframework.http.ResponseEntity.ok;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.fcc.domain.Order;
import com.fcc.domain.Vendor;
import com.fcc.domain.VendorRatings;
import com.fcc.domain.VendorServices;
import com.fcc.exception.ApiErrorCode;
import com.fcc.exception.GojoException;
import com.fcc.exception.InvalidRequestParameterException;
import com.fcc.exception.ResourceNotFoundException;
import com.fcc.model.AppointmentDto;
import com.fcc.model.BookingStatus;
import com.fcc.model.CommonDto;
import com.fcc.model.CouponDto;
import com.fcc.model.OpeningClosingTimeDto;
import com.fcc.model.ServicesDto;
import com.fcc.model.StatusDetailsDto;
import com.fcc.model.TimeSlotDto;
import com.fcc.model.VendorConfirmCancelModel;
import com.fcc.model.VendorDto;
import com.fcc.model.VendorRatingDto;
import com.fcc.repository.AppointmentRepo;
import com.fcc.repository.ServicesBookedRepo;
import com.fcc.repository.VendorRepo;
import com.fcc.repository.VendorServicesRepo;
import com.fcc.service.AppointmentDaoService;
import com.fcc.service.CouponDaoService;
import com.fcc.service.CustomerDaoService;
import com.fcc.service.DeviceMetaDataService;
import com.fcc.service.OpenCloseTimeDaoService;
import com.fcc.service.OrderDaoService;
import com.fcc.service.PushNotificationService;
import com.fcc.service.ShopTypeService;
import com.fcc.service.SimpleEmailDaoServie;
import com.fcc.service.UserMediaDaoService;
import com.fcc.service.VendorDaoService;
import com.fcc.service.VendorRatingDaoServices;
import com.fcc.service.VendorServicesDaoService;
import com.fcc.util.CommonUtils;
import com.itextpdf.text.DocumentException;

@RestController
@RequestMapping("/api/")
public class VendorController {

	@Autowired
	VendorDaoService vendorDaoService;
//	@Autowired
//	private EmailUtils emailService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private VendorServicesDaoService vendorServicesDaoService;
	@Autowired
	private OpenCloseTimeDaoService openCloseTimeDaoService;
	@Autowired
	private VendorRepo vendorRepo;
	@Autowired
	private AppointmentDaoService appointmentDaoService;
	@Autowired
	private UserMediaDaoService userMediaDaoService;
	@Autowired
	private CustomerDaoService customerDaoService;
	@Autowired
	private OrderDaoService orderDaoService;
	@Autowired
	private PushNotificationService pushNotService;
	@Autowired
	DeviceMetaDataService deviceMetaDataService;
	@Autowired
	VendorRatingDaoServices vendorRatingDaoServices;
	@Autowired
	SimpleEmailDaoServie simpleEmailDaoServie;
	@Autowired
	ShopTypeService shopTypeService;
	@Autowired
	VendorServicesRepo vendorServicesRepo;
	@Autowired
	ServicesBookedRepo servicesBookedRepo;
	@Autowired
	CommonUtils commonUtils;
	@Autowired
	CouponDaoService couponDaoService;
	@Autowired
	AppointmentRepo appointmentRepo;

	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@RequestMapping(value="vendor/updateCreateVendor", method=RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
public ResponseEntity<VendorDto> registerVendorStep1(@RequestPart(required = false, value="images") MultipartFile[] images, @Valid @RequestPart(required = true, value="vendorDto") VendorDto vendorDto) throws Exception {
		
		List<Integer> shopTypes = new ArrayList<Integer>();
		for(int i = 1; i<4; i++) {
			shopTypes.add(i);
		}
	/*	shopTypes.add(1);
		shopTypes.add(2);
		shopTypes.add(3); */
		
		if (vendorDto.getName() == null || vendorDto.getName().isEmpty()) {
			throw new GojoException(ApiErrorCode.INVALID_INPUT + " Full Name is missing");
		}
		if (vendorDto.getEmail() == null || vendorDto.getEmail().isEmpty()) {
			throw new GojoException(ApiErrorCode.INVALID_INPUT +" Either customer email or passowrd is missing");
		}
		if (vendorDto.getMobile() == null || vendorDto.getMobile().isEmpty()) {
			throw new GojoException(ApiErrorCode.INVALID_INPUT + "Contact Number is missing");
		}				

		if (vendorDto.getPassword() == null || vendorDto.getPassword().isEmpty()) {
			throw new GojoException(ApiErrorCode.INVALID_INPUT + " password is missing");
		}
		if (vendorDto.getLatitude() == null || vendorDto.getLatitude().isEmpty()) {
			throw new Exception( "Latitude is missing");
		}
		if (vendorDto.getLongitude() == null || vendorDto.getLongitude().isEmpty()) {
			throw new Exception("Longitude is missing");
		}
		
		if (vendorDto.getAddress() == null || vendorDto.getAddress().isEmpty()) {
			throw new Exception("Address is missing");
		}
		
		
		if (vendorDto.getType() == null || vendorDto.getType().isEmpty()) {
			throw new GojoException(ApiErrorCode.INVALID_INPUT + " Type is missing");
		}
		
		if (vendorDto.getShopTypeId() == null) {
			
			throw new Exception("Shop type or missing.");
		}
		if(!shopTypes.contains(vendorDto.getShopTypeId())) {
			throw new Exception("Invalid shop type should be either 1 or 2 0r 3");
		}
		
		/*Change for Mobile as a primary authenticator*/
		
		Vendor vendor = vendorDaoService.findVendorByMobile(vendorDto.getMobile());
		if (vendor != null) {
			throw new GojoException("Mobile number is already registered by another vendor");
		}
//		Vendor vendor = vendorDaoService.findVendorByEmail(vendorDto.getEmail());
//		if (vendor != null) {
//			throw new GojoException(ApiErrorCode.EMAIL_ALREADY_EXIST + " Email is already registered by another vendor");
//		}
		/*Change for Mobile as a primary authenticator*/

		VendorDto vendorSaved = vendorDaoService.save(vendorDto,images);
		vendorRatingDaoServices.createDefaultRating(vendorDaoService.findVendorById(vendorSaved.getVendorId()));
//		String oneTimePassword = CommonUtils.getRandDigitsOtp();
		//customerDaoService.persistOtpInDb(customerSaved.getCustomerId(),oneTimePassword);
		//emailService.sendSimpleMessage(vendorSaved.getEmail(), "One Time Password", oneTimePassword);
//		String encAuthHeader = CommonUtils.setAuthCredentials(String.valueOf(customerSaved.getCustomerId()), "", "USER",String.valueOf(System.currentTimeMillis() / 1000));
//		String verifyUrl = "http://localhost:8080/api/user/verifyEmail?q=" + URLEncoder.encode(encAuthHeader);
//		emailService.sendSimpleMessage(customerSaved.getEmail(), "Please verify your email address", verifyUrl);
		// send verification mail
		vendorSaved.setStatusDetail(messageSource.getMessage("vendor.created", null, Locale.US));
		vendorSaved.setSuccess(true);
		return ResponseEntity.status(HttpStatus.CREATED).body(vendorSaved);

	}
	
	@SuppressWarnings("rawtypes")
	@PostMapping("vendor/addServicesandTimings")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<CommonDto> registerVendorStep2(@Valid @RequestBody String vendorServices) throws ParseException{
		CommonDto response = new CommonDto();
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(vendorServices);
		String vendorId = json.get("vendor_id").toString();
		String isUpdateFlag = json.get("update_flag").toString();
		Vendor vendor = vendorDaoService.findVendorById(Integer.parseInt(vendorId));
		if(vendor == null) {
			throw new GojoException(" Vendor doesnot exist");
		}
		
		
		if(isUpdateFlag.equals("1")) {
			List<String> deleteServicesIds = commonUtils.getDeleteServiceIds((JSONArray)json.get("services"), vendor);
			long deletedServicesCount = 0;
			
			//List<VendorServices> vendorServiceList = vendorServicesRepo.findByVendor(vendor);
			//List<VendorServices> vendorServiceList = new ArrayList<VendorServices>();
			if(deleteServicesIds.size() > 0) {
				
				/* deleteServicesIds.stream().forEach(serviceId -> {
					VendorServices service = vendorServicesDaoService.findServiceById(Integer.valueOf(serviceId));
					if(service != null) {
						vendorServicesDaoService.deleteBookedServicesByService(service);
						deletedServicesCount = deletedServicesCount + vendorServicesDaoService.deleteByService(service.getServiceId());
					}
				}); */
				
				
				
				
				for(String serviceId:deleteServicesIds ) {
					VendorServices service = vendorServicesDaoService.findServiceById(Integer.valueOf(serviceId));
					if(service != null) {
						vendorServicesDaoService.deleteBookedServicesByService(service);
						deletedServicesCount = deletedServicesCount + vendorServicesDaoService.deleteByService(service.getServiceId());
					}
					
				}
			}
			
//			for(VendorServices vendorService:vendorServiceList) {
//				vendorServicesDaoService.deleteBookedServicesByService(vendorService);
//			}
			//deletedServicesCount = vendorServicesDaoService.deleteServicesByVendorId(vendor);
			
			// delete the order mapping with the service & also cancel the order
			if(deletedServicesCount < 1 && deleteServicesIds.size() >= 1) {
				throw new ResourceNotFoundException("Error occured while updating services");
			}
			
			
			
//			long deletedTimingsCount = openCloseTimeDaoService.deleteServicesByVendorId(vendor);
//			if(deletedTimingsCount < 1) {
//				throw new ResourceNotFoundException("Error occured while updating timings");
//			}
		}
		
		JSONArray services = (JSONArray)json.get("services");
		ServicesDto servicesDto;
		Iterator i = services.iterator();
		Iterator j = services.iterator();;
		
		
		JSONArray openCloseTimings = (JSONArray)json.get("open_close_time");
		OpeningClosingTimeDto openCloseTimeDto;
		Iterator m = openCloseTimings.iterator();
		Iterator n = openCloseTimings.iterator();
		
		/*new implementation to check for null or empty*/
		while (i.hasNext()) {
			servicesDto = new ServicesDto();
			JSONObject service = (JSONObject) i.next();
			servicesDto.setVendorId(Integer.valueOf(vendorId));
			servicesDto.setServiceName((String)service.get("serviceName"));
			servicesDto.setServicePrice((String)service.get("servicePrice"));
			servicesDto.setTimeInMin((String)service.get("timeInMin"));
			servicesDto.setServiceId((String)service.get("serviceId"));
			
			if(servicesDto.getVendorId() == null) {
				throw new GojoException(ApiErrorCode.INVALID_INPUT+" Vendor Id missing");
			}
			if(servicesDto.getServiceName() == null || servicesDto.getServiceName().isEmpty()) {
				throw new GojoException(ApiErrorCode.INVALID_INPUT+" Service name is missing");
			}
			if(servicesDto.getTimeInMin() == null || servicesDto.getTimeInMin().isEmpty()) {
				throw new GojoException(ApiErrorCode.INVALID_INPUT+" Service Time is missing");
			}
			if(servicesDto.getServicePrice() == null || servicesDto.getServicePrice().isEmpty()) {
				throw new GojoException(ApiErrorCode.INVALID_INPUT+" Service price is missing");
			}
			
		}
		
		
		while (n.hasNext()) {
			openCloseTimeDto = new OpeningClosingTimeDto();
			JSONObject openCloseTime = (JSONObject)n.next();
			openCloseTimeDto.setVendorId(Integer.valueOf(vendorId));
			openCloseTimeDto.setOpenTime((String)openCloseTime.get("openTime"));
			openCloseTimeDto.setCloseTime((String)openCloseTime.get("closeTime"));
			openCloseTimeDto.setStatus((String)openCloseTime.get("status"));
			openCloseTimeDto.setWeekDay((String)openCloseTime.get("weekDay"));
			openCloseTimeDto.setOpClId((String)openCloseTime.get("opClId"));
			
			if(openCloseTimeDto.getVendorId() == null) {
				throw new GojoException(ApiErrorCode.INVALID_INPUT +" Vendor Id is missing");
				
			}
			if(openCloseTimeDto.getStatus() == null || openCloseTimeDto.getStatus().isEmpty()) {
				throw new GojoException(ApiErrorCode.INVALID_INPUT +" Status is missing");
			}
			if(!openCloseTimeDto.getStatus().equals("CLOSE")) {
				if(openCloseTimeDto.getOpenTime() == null || openCloseTimeDto.getOpenTime().isEmpty()) {
					throw new GojoException(ApiErrorCode.INVALID_INPUT +" Open time is missing");
				}
				if(openCloseTimeDto.getCloseTime() == null || openCloseTimeDto.getCloseTime().isEmpty()) {
					throw new GojoException(ApiErrorCode.INVALID_INPUT +" Close time is missing");
				}
			}
			
			if(openCloseTimeDto.getWeekDay() == null || openCloseTimeDto.getWeekDay().isEmpty()) {
				throw new GojoException(ApiErrorCode.INVALID_INPUT +" Weekday is missing");
			}
					
		}
		
		/*new implementation to check for null or empty*/
		
		
		while (j.hasNext()) {
			servicesDto = new ServicesDto();
			JSONObject service = (JSONObject) j.next();
			servicesDto.setVendorId(Integer.valueOf(vendorId));
			servicesDto.setServiceName((String)service.get("serviceName"));
			servicesDto.setServicePrice((String)service.get("servicePrice"));
			servicesDto.setTimeInMin((String)service.get("timeInMin"));
			servicesDto.setServiceId((String)service.get("serviceId"));
			servicesDto = vendorServicesDaoService.addServices(servicesDto);
		}
		
		
		
		while (m.hasNext()) {
			openCloseTimeDto = new OpeningClosingTimeDto();
			JSONObject openCloseTime = (JSONObject)m.next();
			openCloseTimeDto.setVendorId(Integer.valueOf(vendorId));
			openCloseTimeDto.setOpenTime((String)openCloseTime.get("openTime"));
			openCloseTimeDto.setCloseTime((String)openCloseTime.get("closeTime"));
			openCloseTimeDto.setStatus((String)openCloseTime.get("status"));
			openCloseTimeDto.setWeekDay((String)openCloseTime.get("weekDay"));
			openCloseTimeDto.setOpClId((String)openCloseTime.get("opClId"));
			openCloseTimeDto = openCloseTimeDaoService.addTimes(openCloseTimeDto);
					
		}
		
		
		vendor.setRegisterStep("2");
		vendorDaoService.update(vendor);
//		String oneTimePassword = CommonUtils.getRandDigitsOtp();
		if(isUpdateFlag.equals("0")) {
			//vendorDaoService.persistOtpInDb(vendor.getvendorId(),oneTimePassword);
//			/simpleEmailDaoServie.otp = oneTimePassword;
			//simpleEmailDaoServie.verifyEmail(vendor.getEmail(),String.valueOf(oneTimePassword),false);
			//emailService.sendSimpleMessage(vendor.getEmail(), "One Time Password", oneTimePassword);
			//vendorRatingDaoServices.createDefaultRating(vendor);
		}
		
//		//populate default rating of zero 
//		if(isUpdateFlag.equals("0")) {
//			vendorRatingDaoServices.createDefaultRating(vendor);
//		}
		response.setMobile(vendor.getMobile());
		response.setUserId(vendor.getvendorId());
		response.setStatusDetail(isUpdateFlag.equals("0")?"Please enter the One time password":"Services amd timings updated successfully");
		response.setSuccess(true);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	
	@RequestMapping(value="vendor/updateCreateVendor", method=RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<VendorDto> updateCustomer(@Valid @RequestBody VendorDto vendorDto) throws Exception{
		
		String status = "Vendor details updated successfully";
		
		boolean success = true;
		if (vendorDto.getVendorId() == null) {
			throw new InvalidRequestParameterException( " Vendor id is missing");
		}
		
		Vendor vendor = vendorDaoService.findVendorById(vendorDto.getVendorId());
		if(vendor == null) {
			throw new ResourceNotFoundException(" Invalid Vendor Id");
		}
		
		if (vendorDto.getName() == null || vendorDto.getName().isEmpty()) {
			throw new InvalidRequestParameterException( " Full Name is missing");
		}
		if (vendorDto.getAddress() == null || vendorDto.getAddress().isEmpty()) {
			throw new InvalidRequestParameterException(" Address is missing");
		}
		if (vendorDto.getMobile() == null || vendorDto.getMobile().isEmpty()) {
			throw new InvalidRequestParameterException(" Mobile is missing");
		}
		if (vendorDto.getLatitude() == null || vendorDto.getLatitude().isEmpty()) {
			throw new InvalidRequestParameterException(" Latitude is missing");
		}
		if (vendorDto.getLongitude() == null || vendorDto.getLongitude().isEmpty()) {
			throw new InvalidRequestParameterException(" Longitude is missing");
		}
		if (vendorDto.getEmail() == null || vendorDto.getEmail().isEmpty()) {
			throw new InvalidRequestParameterException(" Email address is missing");
		}
		
		if (vendorDto.getShopType() == null) {
			throw new InvalidRequestParameterException(" shop type is missing");
		}
		
		if (vendorDto.getSeats() == null || vendorDto.getSeats().isEmpty()) {
			throw new InvalidRequestParameterException(" seats missing");
		}
		
		//Remove mobile update from update logic and place in verify otp logic
		if(!vendor.getMobile().equals(vendorDto.getMobile())) {
			
			if (vendorDaoService.findVendorByMobile(vendorDto.getMobile()) != null) {
				throw new GojoException("Mobile is already registered by another vendor");
			}
			
			
			vendor.setTempMobile(vendorDto.getMobile());
			// String oneTimePassword = CommonUtils.getRandDigitsOtp();
			// vendorDaoService.persistOtpInDb(vendor.getvendorId(),oneTimePassword);
			//emailService.sendSimpleMessage(vendorDto.getEmail(), "One Time Password", oneTimePassword);
			// simpleEmailDaoServie.verifyEmail(vendorDto.getEmail(),String.valueOf(oneTimePassword),false);
			vendor.setOtpVerified(false);
			vendor.setUpdate(true);
			status = "Please activate your new Mobile Number";
			success = false;
			
		}
		
		vendor.setName(vendorDto.getName());
		vendor.setAddress(vendorDto.getAddress());
		vendor.setEmail(vendorDto.getEmail());
		vendor.setLatitude(vendorDto.getLatitude());
		vendor.setLongitude(vendorDto.getLongitude());
		vendor.setShopType(shopTypeService.getShopTypeById(Integer.valueOf(vendorDto.getShopType())));
		vendor.setSeats(vendorDto.getSeats());
		vendor.setLastUpdatedDate(new Date());
		Vendor vendorUpdated = vendorDaoService.update(vendor);

		//To be completed after discussion
		vendorDto.setVendorId(vendorUpdated.getvendorId());
		vendorDto.setStatusDetail(status);
		vendorDto.setSuccess(success);
		
		return ResponseEntity.status(HttpStatus.OK).body(vendorDto);
	}
	
	
	
//	@PostMapping("vendor/search")
//	public ResponseEntity<Map<String, Object>> getVendorsByName(@Valid @RequestBody CommonDto vendorDto){
//		if(vendorDto.getKeyword() == null || vendorDto.getKeyword().isEmpty()) {
//			throw new InvalidRequestParameterException(" Please enter the keyword");
//		}
//		List<CommonDto> vendorDtos = new ArrayList<CommonDto>();
//		Map<String, Object> model = new HashMap<>();
//        vendorDtos = vendorDaoService.getVendorListByName(vendorDto.getKeyword());
//		model.put("vendors", vendorDtos);
//		model.put("statusDetail", vendorDtos.isEmpty() ? messageSource.getMessage("no.vendor.found", null, Locale.US)
//				: vendorDtos.size() + " " + messageSource.getMessage("vendors.found", null, Locale.US));
//		model.put("success",vendorDtos.isEmpty() ? false:true);
//		return ok(model);
//	}
	
	
	@PostMapping("vendor/search")
	public ResponseEntity<Map<String, Object>> searchVendors(@Valid @RequestBody CommonDto searchDto){
		List<VendorDto> vendorDtos = new ArrayList<VendorDto>();
		
		if(searchDto.getUserId() == null) {
			throw new InvalidRequestParameterException(" Customer id is missing");
		}
		
		Customer customer = customerDaoService.findUserById(searchDto.getUserId());
		if(searchDto.getKeyword() == null || searchDto.getKeyword().isEmpty()) {
			throw new InvalidRequestParameterException(" Please enter the keyword");
		}
		if(customer == null) {
			throw new ResourceNotFoundException("Invalid customer id");
		}
		
		if (searchDto.getLatitude() == null || searchDto.getLatitude().isEmpty()) {
			throw new InvalidRequestParameterException(" Latitude is missing");
		}
		if (searchDto.getLongitude() == null || searchDto.getLongitude().isEmpty()) {
			throw new InvalidRequestParameterException(" Longitude is missing");
		}
		
		searchDto.setShopTypeId(customer.getGender().getGenderId());
		vendorDtos = vendorDaoService.getNearByVendorList(searchDto,true);
		Map<String, Object> model = new HashMap<>();
		model.put("vendors", vendorDtos);
		model.put("statusDetail", vendorDtos.isEmpty() ? messageSource.getMessage("no.vendor.found", null, Locale.US)
				: vendorDtos.size() + " " + messageSource.getMessage("vendors.found", null, Locale.US));
		model.put("success",vendorDtos.isEmpty() ? false:true);
		
		return ok(model);
	}
	
	
	
	
	
	@PostMapping("vendor/vendorByLoc")
	public ResponseEntity<Map<String, Object>> getVendorsByLocation(@Valid @RequestBody CommonDto commonDto){
		List<VendorDto> vendorDtos = new ArrayList<VendorDto>();
		Customer customer = customerDaoService.findUserById(commonDto.getUserId());
		if(customer == null) {
			throw new ResourceNotFoundException("Invalid customer id");
		}
		
		if (commonDto.getLatitude() == null || commonDto.getLatitude().isEmpty()) {
			throw new InvalidRequestParameterException(" Latitude is missing");
		}
		if (commonDto.getLongitude() == null || commonDto.getLongitude().isEmpty()) {
			throw new InvalidRequestParameterException(" Longitude is missing");
		}
		commonDto.setShopTypeId(customer.getGender().getGenderId());
		commonDto.setKeyword("");
		vendorDtos = vendorDaoService.getNearByVendorList(commonDto,false);
		Map<String, Object> model = new HashMap<>();
		model.put("vendors", vendorDtos);
		model.put("statusDetail", vendorDtos.isEmpty() ? messageSource.getMessage("no.vendor.found", null, Locale.US)
				: vendorDtos.size() + " " + messageSource.getMessage("vendors.found", null, Locale.US));
		model.put("success",vendorDtos.isEmpty() ? false:true);
		return ok(model);
		
	}
	
	@GetMapping("vendor/vendorById/{id}")
	public ResponseEntity<CommonDto> getVendorDetailById(@PathVariable Integer id) throws ParseException{
		Vendor vendor = null;
		try {
			vendor = vendorDaoService.findVendorById(id);
		}
		catch(Exception exc) {
			exc.printStackTrace();
		}
		
		if(vendor == null) {
			throw new ResourceNotFoundException("Invalid vendor Id");
		}
		
		CommonDto commonDto = vendorDaoService.getVendorDetailById(vendor, true);
		commonDto = vendorDaoService.getAllUnPaidDues(vendor, commonDto);
		commonDto.setImage(null);
		commonDto.setStatusDetail("Vendor found");
		commonDto.setSuccess(true);
		
		return ResponseEntity.status(HttpStatus.OK).body(commonDto);
		
	}
	
	@GetMapping("vendor/servicesByVendorId/{id}")
	public ResponseEntity<CommonDto> getServicesByVendorId(@PathVariable Integer id) throws ParseException{
		CommonDto response = new CommonDto();
		Vendor vendor = vendorDaoService.findVendorById(id);
		if(vendor == null) {
			throw new ResourceNotFoundException(" Invalid Vendor id");
		}
		
		response = vendorDaoService.getServicesByVendorId(vendor);
		if(response == null) {
			throw new GojoException(ApiErrorCode.RESOURCE_INACTIVE+" Shop is closed today");
		}
		response.setStatusDetail("Services");
		response.setSuccess(true);
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
	}
	
	@PostMapping("vendor/timeSlots")
	public ResponseEntity<Map<String, Object>> getTimeSlots(@Valid @RequestBody TimeSlotDto timeSlotDto) throws java.text.ParseException{
		Vendor vendor = vendorDaoService.findVendorById(timeSlotDto.getVendorId());
		if(vendor == null) {
			throw new ResourceNotFoundException(" Invalid  vendor id");
		}
		
		if(timeSlotDto.getSelectedDate() == null || timeSlotDto.getSelectedDate().isEmpty()) {
			throw new InvalidRequestParameterException(" No date is selected");
		}
		List<TimeSlotDto> timeSlotDtos = vendorDaoService.getTimeSlots(vendor, timeSlotDto.getSelectedDate());
		List<ServicesDto> serviceDtos = vendorDaoService.getServicesList(vendor);
		Map<String, Object> model = new LinkedHashMap<>();
		
		model.put("success",timeSlotDtos.isEmpty() ? false:true);
		model.put("SelectedDate", timeSlotDto.getSelectedDate());
		model.put("timeSlots", timeSlotDtos);
		model.put("Services", serviceDtos);
		model.put("statusDetail", timeSlotDtos.isEmpty() ? "Error occurr"
				+ "ed"
				: timeSlotDtos.size() + " " + "TimeSlots  " +"and " +serviceDtos.size()+" " + "Services found");
		
		
		return ok(model);
	}
	
	@SuppressWarnings("rawtypes")
	@PostMapping("vendor/bookSlot")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Map<String, Object>> confirmBooking(@Valid @RequestBody String bookings) throws ParseException, java.text.ParseException{
		Map<String, Object> model = new HashMap<>();
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(bookings);
		System.out.println(json);
		String vendorId = json.get("vendorId").toString();
		String customerId = json.get("customerId").toString();
		String paymentModeId = json.get("paymentMode").toString();
		String selectedDate = json.get("selectedDate").toString();
		String totalPrice = json.get("totalPrice").toString();
		String transactionId = json.get("transactionId").toString();
		JSONArray services = (JSONArray)json.get("services");
		JSONArray slots = (JSONArray)json.get("selectedSlots");
		JSONArray slotsRef = (JSONArray)json.get("selectedSlots");
		Iterator serviceIterator = services.iterator();
		Iterator slotIterator = slots.iterator();
		Iterator slotIteratorRef = slotsRef.iterator();
		Integer serviceCount = services.size();
		
		/*Prevent Concurrent booking for a slot*/
		Vendor vendorRef = vendorDaoService.findVendorById(Integer.valueOf(vendorId));
		if(vendorRef == null) {
			throw new ResourceNotFoundException("Invalid vendor");
		}
		int w = 0;
		while (w<slotsRef.size() - 1) {
		
			JSONObject timeSlotRef = (JSONObject)slotIteratorRef.next(); 
			System.out.println("Service object " +timeSlotRef);
			
			Integer slotCount = appointmentRepo.getSlotCountByDateAndStatus((String)timeSlotRef.get("slot"), CommonUtils.zeroTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(selectedDate)));
			Integer seatCount = Integer.valueOf(vendorRef.getSeats());
			if(slotCount >= seatCount) {
				throw new GojoException("Slot is already booked by another customer.");
			}
					
		    w++;			
		}
		/*Prevent Concurrent booking for a slot*/
		
		Order createdOrder = orderDaoService.createOrder(selectedDate, customerId, vendorId, paymentModeId, totalPrice, serviceCount, transactionId);
		
		int i = 0;
		while (i<slots.size() - 1) {
		
			JSONObject timeSlot = (JSONObject)slotIterator.next(); 
			System.out.println("Service object " +timeSlot);
			
			appointmentDaoService.bookSlots(selectedDate, (String)timeSlot.get("slot"), customerId, vendorId,createdOrder);		
		    i++;			
		}
		
		while (serviceIterator.hasNext()) {
			JSONObject serviceId = (JSONObject)serviceIterator.next(); 
			vendorServicesDaoService.saveBookings((Long)serviceId.get("serviceId"), createdOrder, selectedDate);
		}
		
        String token = deviceMetaDataService.getUserDeviceToken(createdOrder.getVendor().getvendorId(), createdOrder.getVendor().getType());
		
		//Push notification to Vendor
        if(token !=null && !token.isEmpty()) {
        	org.json.JSONObject data = new org.json.JSONObject();
    		data.put("title", "Booking confirmation/Cancellation");
    		data.put("body", "Booking had been made by customer");
    		data.put("vendorID", vendorId);
    		data.put("customerId", customerId);
    		data.put("orderId", createdOrder.getOrderId());
    		data.put("bookingStatus", createdOrder.getStatus().getStatusId());
    		//data.put("isConfirmed", String.valueOf(paymentModeId.equals("1")?true:false));
    		data.put("statusCode", "2");
    		
    		try {
    			pushNotService.sendNotification(data, token);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        }
		
		
		//model.put("bookedSlot", slots);
		model.put("statusDetail", "Booking complete");
		model.put("success", true);
		
		return ok(model);
	}
	
	@RequestMapping(value="vendor/updateImages/{vendorId}", method=RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<VendorDto> updatePics(MultipartFile[] images, @PathVariable Integer vendorId) throws IOException{
		Vendor vendor = vendorDaoService.findVendorById(vendorId);
		if(vendor == null) {
			throw new ResourceNotFoundException("Vendor not found");
		}
	
		
		VendorDto vendorDto = vendorDaoService.updateImages(vendor, images);
		vendorDto.setStatusDetail("Images updated successfully");
		vendorDto.setSuccess(true);
		
		return ResponseEntity.status(HttpStatus.OK).body(vendorDto);
		
	}
	@DeleteMapping("vendor/deleteImages/{vendorId}/{imageId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<CommonDto> deleteImagesByVendorAndImageId(@PathVariable Integer vendorId, @PathVariable Integer imageId){
	    Vendor vendor = vendorDaoService.findVendorById(vendorId);
	    if(vendor == null) {
	    	throw new ResourceNotFoundException("Invalid vendor Id");
	    }
		userMediaDaoService.deleteByImageAndVendorId(vendor, imageId);
		
		CommonDto responseDto = new CommonDto();
		responseDto.setStatusDetail("Picture successfully deleted");
		responseDto.setSuccess(true);
		
		return ResponseEntity.status(HttpStatus.OK).body(responseDto);
	}
	
	
	@PostMapping("vendor/confirmCancelOrder")
	public ResponseEntity<Map<String, Object>> confirmCancelOrder(@Valid @RequestBody VendorConfirmCancelModel confirmCancelModel) throws Exception{
		if(confirmCancelModel.getVendorId() == null) {
			throw new InvalidRequestParameterException(" Vendor Id is missing");
		}
		if(confirmCancelModel.getOrderId() == null) {
			throw new InvalidRequestParameterException(" Order Id is missing");
		}
		
		if(confirmCancelModel.getCancelConfirmFlag() == null ||confirmCancelModel.getCancelConfirmFlag( ).isEmpty()) {
			throw new InvalidRequestParameterException(" Invalid customer id");
		}
		
		Vendor vendor = vendorDaoService.findVendorById(confirmCancelModel.getVendorId());
		if(vendor == null) {
			throw new ResourceNotFoundException(" Invalid vendor id");
		}
		
		Order order = orderDaoService.getOrderById(confirmCancelModel.getOrderId());
		if(order == null) {
			throw new ResourceNotFoundException(" Invalid order id");
		}
		
		vendorDaoService.confirmCancelOrder(vendor, order, confirmCancelModel.getCancelConfirmFlag());
		String token = deviceMetaDataService.getUserDeviceToken(order.getCustomer().getCustomerId(), order.getCustomer().getType());
		
		//Push notification to customer
		org.json.JSONObject data = new org.json.JSONObject();
		data.put("title", "Booking confirmation/Cancellation");
		//data.put("body", "Your order has been " +String.valueOf(confirmCancelModel.getCancelConfirmFlag()).equals("1") != null?"Confirmed":"Cancelled"+" by vendor");
		if(token !=null && !token.isEmpty()) {
			if(confirmCancelModel.getCancelConfirmFlag().equals("1")) {
				data.put("body", "Your booking has been Confirmed by vendor");
			}else {
				data.put("body", "Your booking has been Cancelled by vendor");
			}
			data.put("vendorID", vendor.getvendorId());
			data.put("customerId", order.getCustomer().getCustomerId());
			data.put("orderId", order.getOrderId());
			data.put("bookingStatus", order.getStatus().getStatusId());
			//data.put("isConfirmed", String.valueOf(confirmCancelModel.getCancelConfirmFlag()).equals("1")?true:false);
			data.put("statusCode", "2");
			
			pushNotService.sendNotification(data, token);
		}
		
		 List<AppointmentDto> slots = appointmentDaoService.getSlotsByOrder(order);
		
		Map<String, Object> model = new HashMap<>();
		model.put("status", "success");
		model.put("bookedSlots", slots);
		//model.put("statusDetails", "Order " +String.valueOf(confirmCancelModel.getCancelConfirmFlag()).equals("1")?"Confirmed":"Cancelled"+" successfully");
		if(confirmCancelModel.getCancelConfirmFlag().equals("1")) {
			model.put("statusDetails", "Booking Confirmed successfully");
		}else {
			model.put("statusDetails", "Booking Cancelled successfully");
		}
		
		return ok(model);
	}
	
	
//	@PostMapping("vendor/vendorByLoc/")
//	public ResponseEntity<Map<String, Object>> confirmBooking(@PathVariable Integer vendorId, @PathVariable Integer customerId){
//		Vendor vendor = vendorDaoService.findVendorById(vendorId);
//		Customer customer = customerDaoService.findUserById(customerId);
//		
//		if(vendor == null || customer == null) {
//			throw new GojoException(" Either customer or vendor not found");
//		}
//		
//		
//		
//	
//		Map<String, Object> model = new HashMap<>();
//		model.put("vendors", vendorDtos);
//		model.put("statusDetail", vendorDtos.isEmpty() ? messageSource.getMessage("no.vendor.found", null, Locale.US)
//				: vendorDtos.size() + " " + messageSource.getMessage("vendors.found", null, Locale.US));
//		model.put("success",vendorDtos.isEmpty() ? false:true);
//		return ok(model);
//		
//	}
	
	@PostMapping("vendor/bookingStatus")
	public ResponseEntity<Map<String, Object>> bookingStatusList(@RequestBody BookingStatus statusDto) throws java.text.ParseException{
//		Date createdDate = null;
		List<Order> orders = new ArrayList<Order>();
		if(statusDto.getType() == null || statusDto.getType().isEmpty()) {
			throw new InvalidRequestParameterException("User type is missing");
		}
		if(statusDto.getType().equals("VENDOR")) {
			if(statusDto.getVendorId() == null || statusDto.getVendorId().isEmpty()) {
				throw new InvalidRequestParameterException("Vendor id is missing");	
				
			}
			
		}
		
		if(statusDto.getType().equals("CUSTOMER")) {
			if(statusDto.getCustomerId() == null || statusDto.getCustomerId().isEmpty()) {
				throw new InvalidRequestParameterException("Customer id is missing");	
			}
			
		}
		
		orders = orderDaoService.getOrdersByStatus(statusDto);
		Map<String, Object> mapper = new HashMap<>();
		Map<String, Object> mapper2 = new HashMap<>();
		List<StatusDetailsDto> bookingStatusDtos = new ArrayList<StatusDetailsDto>();
		StatusDetailsDto bookingStatusDto;
		for(Order order:orders) {
			bookingStatusDto = new StatusDetailsDto();
			CommonDto userDto = new CommonDto();
			String orderId = String.valueOf(order.getOrderId());
			String totalPrice = String.valueOf(order.getTotalPrice());
			List<ServicesDto> services = vendorServicesDaoService.getServicesByOrder(order);
			bookingStatusDto.setOrderId(orderId);
			bookingStatusDto.setTotalPrice(totalPrice);
			bookingStatusDto.setServices(services);
			bookingStatusDto.setOrderDate(String.valueOf(order.getOrderDate()));
			bookingStatusDto.setAppointmentDate(String.valueOf(order.getAppointmentDate()));
			bookingStatusDto.setSlots(appointmentDaoService.getSlotsByOrder(order));
			if(statusDto.getType().equals("VENDOR")) {
				userDto = customerDaoService.getCustomerDetailById(order.getCustomer());
				userDto.setUserName(null);
			}else if(statusDto.getType().equals("CUSTOMER")) {
				userDto = vendorDaoService.getVendorDetailById(order.getVendor(), false);
				userDto.setUserName(null);
				userDto.setLatitude(null);
				userDto.setLongitude(null);
				userDto.setImages(null);
				userDto.setServices(null);
			}
//            if(statusDto.getType().equals("CUSTOMER")) {
//            	vendorDaoService.getVendorDetailById(vendor)
//			}
			bookingStatusDto.setUserinfo(userDto);
			bookingStatusDto.setTotalTime(CommonUtils.getSumOfServicesTime(services));
			bookingStatusDtos.add(bookingStatusDto);
			
			
//			mapper = new HashMap<>();
//			mapper.put("order_id", order.getOrderId());
//			mapper.put("customer_id", order.getCustomer().getCustomerId());
//			mapper.put("total_price", order.getTotalPrice());
//			mapper2.put("data", mapper);
		}
		
     
		
	
		
		Map<String, Object> model = new HashMap<>(); 
		model.put("status", "success");
		model.put("details", bookingStatusDtos);
		
		return ok(model);
		
	}
	
	@GetMapping("vendor/paymentCounts/{vendorId}")
	public ResponseEntity<Map<String, Object>> getPaymentCounts(@PathVariable Integer vendorId){
		Vendor vendor = vendorDaoService.findVendorById(vendorId);
		if(vendor == null) {
			throw new ResourceNotFoundException(" Invalid order id");
		}
		
		String[] paymentCount = vendorDaoService.getOfflineOnlinePayCount(vendor);
		CommonDto commonDto = new CommonDto(); 
		commonDto = vendorDaoService.getAllUnPaidDues(vendor, commonDto);
		commonDto.setBookingAllowed(vendor.getIsBookingAllowed() == 0?false:true);
		
		
		
		
		Map<String, Object> model = new LinkedHashMap<>();
		model.put("statusDetails", "success");
		//model.put("Resource", messageSource.getMessage("no.action.found", null, Locale.ENGLISH));
		model.put("onlineCounts", paymentCount[0]);
		model.put("offlineCounts", paymentCount[1]);
		model.put("paymentInfo", commonDto);
		model.put("provider", "armk@icici");
		model.put("payeeName", "ARMAN KHAN");
	
		
		
		return ok(model);
	}
	
	@PostMapping("vendor/updateRating/")
	public ResponseEntity<Map<String, Object>> updateVendorRatings(@Valid @RequestBody VendorRatingDto vendorRatingDto){
		
		if(vendorRatingDto.getVendorId() == null || vendorRatingDto.getVendorId().isEmpty()) {
			throw new InvalidRequestParameterException("No vendor found");
		}
        if(vendorRatingDto.getCustomerId() == null || vendorRatingDto.getCustomerId().isEmpty()) {
        	throw new InvalidRequestParameterException("No customer found");
		}
        if(vendorRatingDto.getRating() == null || vendorRatingDto.getRating().isEmpty()) {
        	throw new InvalidRequestParameterException("No rating found");
        }
		
		Vendor vendor = vendorDaoService.findVendorById(Integer.valueOf(vendorRatingDto.getVendorId()));
		if(vendor == null) {
			throw new ResourceNotFoundException(" Invalid vendor id");
		}
		Customer customer = customerDaoService.findUserById(Integer.valueOf(vendorRatingDto.getCustomerId()));
		
		if(customer == null) {
			throw new ResourceNotFoundException(" Invalid customer id");
		}
		
		VendorRatings vendorRatings= vendorRatingDaoServices.updateRating(vendor, customer, Double.valueOf(vendorRatingDto.getRating()));
		if(vendorRatings == null) {
			throw new GojoException(ApiErrorCode.INTERNAL_ERROR+" Error in updating rating");
		}
		
		Map<String, Object> model = new HashMap<>();
		model.put("statusDetails", "success");
		model.put("rating", vendorRatingDto.getRating());
		//model.put("offlineCounts", paymentCount[1]);
		
		return ok(model);
	}
	
	
	@PostMapping("vendor/payDues")
	public ResponseEntity<Map<String, Object>> payToFcc(@RequestBody CommonDto commonDto){
		
		Boolean status = vendorDaoService.isTransactionCompleted(commonDto);
	
		Map<String, Object> model = new HashMap<>();
		model.put("statusDetails", status == true?"Payment successfull":"Payment failed");
		model.put("success", status == true?"success":"false");
		
		return ok(model);
	}
	
	
	@GetMapping("vendor/getBookingsUnPaid/{vendorId}")
	public ResponseEntity<Map<String, Object>> getBookingsUnPaidFcc(@PathVariable("vendorId") Integer vendorId){
		
		Vendor vendor = vendorDaoService.findVendorById(vendorId);
		if(vendor == null) {
			throw new ResourceNotFoundException(" Invalid order id");
		}
		
		List<StatusDetailsDto> bookingStatusDtos = new ArrayList<StatusDetailsDto>();
		List<Order> bookings = vendorDaoService.getBookingsByFccPayStatus(vendor.getvendorId());
		StatusDetailsDto bookingStatusDto;
		Double totalIncome = Double.valueOf("0.0");
		for(Order order:bookings) {
			bookingStatusDto = new StatusDetailsDto();
			CommonDto userDto = new CommonDto();
			String orderId = String.valueOf(order.getOrderId());
			String totalPrice = String.valueOf(order.getTotalPrice());
			List<ServicesDto> services = vendorServicesDaoService.getServicesByOrder(order);
			bookingStatusDto.setOrderId(orderId);
			bookingStatusDto.setTotalPrice(totalPrice);
			bookingStatusDto.setServices(services);
			bookingStatusDto.setOrderDate(String.valueOf(order.getOrderDate()));
			bookingStatusDto.setAppointmentDate(String.valueOf(order.getAppointmentDate()));
			bookingStatusDto.setSlots(appointmentDaoService.getSlotsByOrder(order));
			userDto = customerDaoService.getCustomerDetailById(order.getCustomer());
	        userDto.setUserName(null);
			
//            if(statusDto.getType().equals("CUSTOMER")) {
//            	vendorDaoService.getVendorDetailById(vendor)
//			}
			bookingStatusDto.setUserinfo(userDto);
			bookingStatusDto.setTotalTime(CommonUtils.getSumOfServicesTime(services));
			bookingStatusDtos.add(bookingStatusDto);
			totalIncome = totalIncome + Double.valueOf(totalPrice);
			
//			mapper = new HashMap<>();
//			mapper.put("order_id", order.getOrderId());
//			mapper.put("customer_id", order.getCustomer().getCustomerId());
//			mapper.put("total_price", order.getTotalPrice());
//			mapper2.put("data", mapper);
		}
	
		Map<String, Object> model = new LinkedHashMap<>();
		model.put("statusDetails", "Total "+bookings.size()+" offline unpaid bookings found");
		model.put("success", true);
        model.put("bookings", bookingStatusDtos);
	    model.put("totalIncome", String.valueOf(totalIncome));
		
		return ok(model);
	}
	@PostMapping("vendor/createCoupon/{vendorId}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<CouponDto> createCouponByVendor(@Valid @RequestBody CouponDto couponDto, @PathVariable("vendorId") Integer vendorId) throws ParseException, java.text.ParseException{
		Vendor vendor = vendorDaoService.findVendorById(vendorId);
		if(vendor == null) {
			throw new ResourceNotFoundException(" Invalid order id");
		}
		
		
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
		
		
		
		
		CouponDto savedCouponDto = couponDaoService.saveCoupon(couponDto,vendor);
		
		savedCouponDto.setStatusDetail(messageSource.getMessage("coupon.created", null, Locale.US));
		savedCouponDto.setSuccess(true);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedCouponDto);
	}
	
	@GetMapping("admin/getAllCoupons/{vendorId}")
	public ResponseEntity<Map<String, Object>> getAllCoupons(@PathVariable("vendorId") Integer vendorId) throws FileNotFoundException, DocumentException{
		Vendor vendor = vendorDaoService.findVendorById(vendorId);
		if(vendor == null) {
			throw new ResourceNotFoundException(" Invalid order id");
		}
	
		List<CouponDto> couponDtos = couponDaoService.getAllCoupons(vendor);
		
		Map<String, Object> model = new HashMap<>();
		
		model.put("coupons", couponDtos);
		model.put("statusDetail", couponDtos.isEmpty() ? "No active coupon found"
				: couponDtos.size() + " " + "Coupons found");
		model.put("success",couponDtos.isEmpty() ? false:true);
		
		return ok(model);
	}
}



