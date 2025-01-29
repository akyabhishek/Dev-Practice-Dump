package com.fcc.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.fcc.domain.Appointment;
import com.fcc.domain.DeviceMetaData;
import com.fcc.domain.Order;
import com.fcc.domain.ShopType;
import com.fcc.domain.UserMedia;
import com.fcc.domain.Vendor;
import com.fcc.domain.VendorMonthly;
import com.fcc.exception.ApiErrorCode;
import com.fcc.exception.GojoException;
import com.fcc.exception.ResourceNotFoundException;
import com.fcc.model.AppointmentDto;
import com.fcc.model.BookingDto;
import com.fcc.model.CommonDto;
import com.fcc.model.OpeningClosingTimeDto;
import com.fcc.model.ServicesDto;
import com.fcc.model.TimeSlotDto;
import com.fcc.model.UserMediaDto;
import com.fcc.model.VendorDto;
import com.fcc.repository.AppointmentRepo;
import com.fcc.repository.DeviceMetaDataRepo;
import com.fcc.repository.MasterBookingStatusRepo;
import com.fcc.repository.OrdersRepo;
import com.fcc.repository.UserMediaRepo;
import com.fcc.repository.VendorMonthlyRepo;
import com.fcc.repository.VendorRepo;
import com.fcc.util.CommonUtils;
import com.fcc.util.Constants;
import com.fcc.util.DistanceCalculator;

@Component
public class VendorDaoService {

	@Autowired
	VendorRepo vendorRepo;
	@Autowired
	DeviceMetaDataRepo deviceMetaDataRepo;
	@Autowired
	UserMediaDaoService userMediaDaoService;
	@Autowired
	UserMediaRepo userMediaRepo;
	@Autowired
	OpenCloseTimeDaoService openCloseTimeDaoService;
	@Autowired
	VendorServicesDaoService vendorServicesDaoService;
	@Autowired
	AppointmentDaoService appointmentDaoService;
	@Autowired
	VendorRatingDaoServices vendorRatingDaoServices;
	@Autowired
	ShopTypeService shopTypeService;
	@Autowired
	AppointmentRepo appointRepo;
	@Autowired
	OrdersRepo orderRepo;
	@Autowired
	MasterBookingStatusRepo bookingStatuRepo;
	@Value("${server.contextPath}")
	private String contextPath;
	@Autowired
	CommonUtils commonUtils;
	@Autowired
	OrderDaoService orderDaoService;
	@Autowired
	OrdersRepo ordersRepo;
	@Autowired
	VendorMonthlyRepo vendorMonthlyRepo;

	public VendorDto save(VendorDto vendorDto, MultipartFile[] images) throws Exception {
		Vendor vendor = new Vendor();
		ShopType shopType = shopTypeService.getShopTypeById(vendorDto.getShopTypeId());
		if (shopType == null) {
			throw new Exception("No shop type is found");
		}
		try {
			vendor.setName(vendorDto.getName());
			vendor.setEmail(vendorDto.getEmail());
			vendor.setMobile(vendorDto.getMobile());
			vendor.setLatitude(vendorDto.getLatitude());
			vendor.setLongitude(vendorDto.getLongitude());
			// save images in folder and path in db
			vendor.setType(vendorDto.getType());
			vendor.setSeats(vendorDto.getSeats());
			vendor.setPassword(vendorDto.getPassword());
			vendor.setAddress(vendorDto.getAddress());
			vendor.setRegisterStep("1");
			vendor.setShopType(shopType);
			vendor.setCreatedDate(new Date());
			vendor.setIsBookingAllowed(1);
			vendor = vendorRepo.save(vendor);
			vendorDto.setVendorId(vendor.getvendorId());
			int i = 1;
			for (MultipartFile image : images) {
				// inputText.replaceAll("\\s+","-");
				String imageName = commonUtils.saveImages(image,
						vendorDto.getName().replaceAll("\\s+", "-") + "-" + "Picture" + String.valueOf(i));
				userMediaDaoService.saveImagesPathAndName(vendorDto.getVendorId(), vendorDto.getType(), imageName);
				// save name and url in usermedia table
				// store image in folder
				i++;
			}
			
			

		}

		catch (Exception e) {
			throw new Exception("Error Occured while saving User data: " + e.getMessage());
		}
		return vendorDto;

	}

	public VendorDto updateImages(Vendor vendor, MultipartFile[] images) throws IOException {
		List<UserMedia> vendorImages = userMediaRepo.findByVendor(vendor);
		UserMediaDto userMediaDto;
		int i = vendorImages.size() + 1;
		VendorDto vendorDto = new VendorDto();
		List<String> imageList = new ArrayList<String>();

		for (MultipartFile image : images) {
			// inputText.replaceAll("\\s+","-");
			String imageName = commonUtils.saveImages(image,
					vendor.getName().replaceAll("\\s+", "-") + "-" + "Picture" + String.valueOf(i));
			userMediaDto = userMediaDaoService.saveImagesPathAndName(vendor.getvendorId(), vendor.getType(), imageName);
			imageList.add(contextPath + userMediaDto.getImageUrl());
			// save name and url in usermedia table
			// store image in folder
			i++;
		}

//		for (MultipartFile image : images) {
//
//			String imageName = commonUtils.saveImages(image,
//					vendor.getName() + "-" + "Picture" + String.valueOf(i));
//			userMediaDto = userMediaDaoService.saveImagesPathAndName(vendor.getvendorId(), vendor.getType(), imageName);
//			imageList.add(contextPath+userMediaDto.getImageUrl());
//		
//			// save name and url in usermedia table
//			// store image in folder
//			i++;
//		}

		vendorDto.setVendorId(vendor.getvendorId());
		vendorDto.setName(vendor.getName());
		vendorDto.setImages(imageList);

		return vendorDto;
	}

	public Vendor findVendorByEmail(String email) {
		Optional<Vendor> vendor = vendorRepo.findByEmail(email);
		if (vendor.isPresent()) {
			return vendor.get();
		}
		return null;
	}

	public Vendor findVendorByTempEmail(String mobile) {
		Optional<Vendor> vendor = vendorRepo.findByTempMobile(mobile);
		if (vendor.isPresent()) {
			return vendor.get();
		}
		return null;
	}

	public Vendor findVendorByMobile(String mobile) {
		Optional<Vendor> vendor = vendorRepo.findByMobile(mobile);
		if (vendor.isPresent()) {
			return vendor.get();
		}
		return null;
	}

	public Vendor findVendorById(Integer userId) {
		Optional<Vendor> vendor = vendorRepo.findById(userId);
		if (vendor.isPresent()) {
			return vendor.get();
		}
		return null;
	}

	public List<Vendor> getVendorsByLatLong(String lati, String Longi) {
		List<Vendor> vendorsList = vendorRepo.findByLatititudeLongitude(lati, Longi);

//		return vendorRepo.findByLatititudeLongitude(lati, Longi)
//				.stream()
//				.collect(Collectors.toCollection());
//				
		return vendorsList;
	}

	public CommonDto getUserInfo(CommonDto commonDto, String filterFlag, boolean isEmailUpdated) {

		Vendor vendor = null;

		//UserMediaDto userMediaDto;
		List<UserMediaDto> userMediaDtos = new ArrayList<UserMediaDto>();

		if (filterFlag.contentEquals("EMAIL")) {
			if (isEmailUpdated == false) {
				vendor = findVendorByEmail(commonDto.getUserName());
			} else {
				vendor = findVendorByTempEmail(commonDto.getUserName());
			}

		}
		if (filterFlag.contentEquals("MOBILE")) {
			vendor = findVendorByMobile(commonDto.getMobile());
		}
		if (vendor.isOtpVerified() == true && vendor.getRegisterStep().equals("2")
				|| (vendor.isOtpVerified() == false && vendor.getTempMobile() != null)) {
			// return array of images url in response
			List<UserMedia> userMedias = userMediaRepo.findByVendor(vendor);
            
			userMedias.forEach(userMedia -> {
				UserMediaDto userMediaDto = new UserMediaDto();
				// userMediaDto.setImageName(userMedia.getMediaName()
				userMediaDto.setUserMediaId(userMedia.getMediaId());
				userMediaDto.setImageUrl(contextPath + userMedia.getMediaUrl());
				userMediaDtos.add(userMediaDto);
			});
			
			/*userMedias.stream().map(userMedia ->{
				 UserMediaDto userMediaDto = new UserMediaDto();
				// userMediaDto.setImageName(userMedia.getMediaName()
				userMediaDto.setUserMediaId(userMedia.getMediaId());
				userMediaDto.setImageUrl(contextPath + userMedia.getMediaUrl());
				userMediaDtos.add(userMediaDto);
				return userMediaDtos;
			}).collect(Collectors.toList());  */
			
			
			/* mod on 05-06-2021 replaced by foreach for (UserMedia userMedia : userMedias) {
				userMediaDto = new UserMediaDto();
				// userMediaDto.setImageName(userMedia.getMediaName()
				userMediaDto.setUserMediaId(userMedia.getMediaId());
				userMediaDto.setImageUrl(contextPath + userMedia.getMediaUrl());
				userMediaDtos.add(userMediaDto);
			} */

			commonDto.setUserId(vendor.getvendorId());
			commonDto.setName(vendor.getName());
			commonDto.setUserImages(userMediaDtos);
			commonDto.setMobile(vendor.getMobile());
			commonDto.setUserName(vendor.getEmail());
			commonDto.setRegisterStep(vendor.getRegisterStep());
			commonDto.setShopType(vendor.getShopType().getType());
			commonDto.setIsOtpVerified(vendor.isOtpVerified());
			commonDto.setBookingAllowed(vendor.getIsBookingAllowed() == 0?false:true);
		} else {

			commonDto.setUserId(vendor.getvendorId());
			commonDto.setIsOtpVerified(vendor.isOtpVerified());
			commonDto.setRegisterStep(vendor.getRegisterStep());
			commonDto.setBookingAllowed(vendor.getIsBookingAllowed() == 0?false:true);
//			if(vendor.isOtpVerified() == false) {
//				commonDto.setOtpVerified(isOtpVerified);
//			}
//			
//			if(vendor.getRegisterStep().equals("1")) {
//				// commonDto.setRegister = 1
//			}
//			
//			if(vendor.isOtpVerified() == false && vendor.getRegisterStep().equals("1")) {
//				// commonDto.setotp = false
//				// commonDto.setotp = 1
//			}
		}

		return commonDto;
	}

	public void persistOtpInDb(Integer vendorId, String otp) {
		Optional<Vendor> vendor = vendorRepo.findById(vendorId);
		vendor.get().setOneTimePass(otp);
		vendor.get().setOtpCreatedTime(new Date());
		vendorRepo.save(vendor.get());
	}

	public void saveLoginDetails(CommonDto commonDto, Integer userId) {

		DeviceMetaData deviceData = deviceMetaDataRepo.findByUserTypeAndUserId(commonDto.getType(), userId).get();
		if (deviceData == null) {
			deviceData = new DeviceMetaData();
			deviceData.setUserId(userId);
			deviceData.setUserType(commonDto.getType());
			deviceData.setDeviceToken(commonDto.getDeviceToken());
			deviceData.setDeviceType(commonDto.getDeviceType());
			deviceData.setLoginDateTime(new Date());
			deviceData.setCreatedDate(new Date());
		} else {
			deviceData.setDeviceToken(commonDto.getDeviceToken());
			deviceData.setDeviceType(commonDto.getDeviceType());
			deviceData.setLoginDateTime(new Date());
		}

		deviceMetaDataRepo.save(deviceData);

//		LoginRecord loginRecord = new LoginRecord();
//		switch (filterFlag) {
//		case "EMAIL":
//			loginRecord.setEmail(commonDto.getUserName());
//
//			break;
//		case "MOBILE":
//			loginRecord.setMobile(commonDto.getMobile());
//
//			break;
//		}
//
//		loginRecord.setDeviceToken(commonDto.getDeviceToken());
//		loginRecord.setDeviceType(commonDto.getDeviceType());
//		loginRecord.setPassword(commonDto.getPassword());
//		loginRecord.setLoginDateTime(new Date());
//		loginRecordRepo.save(loginRecord);
//		System.out.println("Saved login info");

	}

	public Vendor update(Vendor vendor) {

		return vendorRepo.save(vendor);
	}

	public List<CommonDto> getVendorListByName(String name) {

		CommonDto vendorDto;
		List<CommonDto> vendorDtoList = new ArrayList<CommonDto>();
		List<Vendor> vendorList = vendorRepo.getAllServicesList(name);
		List<UserMediaDto> vendorImages = null;
		for (Vendor vendor : vendorList) {
			vendorImages = new ArrayList<UserMediaDto>();
			vendorImages = userMediaDaoService.getImagesbyUserId(vendor.getvendorId(), vendor.getType());
			vendorDto = new CommonDto();
			vendorDto.setUserId(vendor.getvendorId());
			vendorDto.setName(vendor.getName());
			vendorDto.setMobile(vendor.getMobile());
			vendorDto.setUserImages(vendorImages); // set images
			vendorDto.setAddress(vendor.getAddress());
			vendorDto.setLatitude(vendor.getLatitude());
			vendorDto.setLongitude(vendor.getLongitude());
			vendorDtoList.add(vendorDto);
		}
		return vendorDtoList;
	}

	public List<VendorDto> getNearByVendorList(CommonDto searchDto, boolean isSearch) {

		List<VendorDto> nearbyVendorList = new ArrayList<VendorDto>();
		List<UserMediaDto> vendorImages = new ArrayList<UserMediaDto>();
//		List<UserMedia> userMedias = new ArrayList<UserMedia>();
		List<Vendor> vendorList = new ArrayList<Vendor>();
		/* mod on 06062021 List<Integer> vendorIds = vendorRepo.vendorIds(searchDto.getKeyword(), searchDto.getShopTypeId()); */
		List<Integer> vendorIds = vendorRepo.vendorIds(searchDto.getKeyword());
		
		
		vendorIds.forEach(vendorid -> {
			Vendor vendor = findVendorById(vendorid);
			vendorList.add(vendor);
		});
		
		
	/* mod on 05062021 replaced by java 8 foreach	for (Integer vendorid : vendorIds) {
			vendor = findVendorById(vendorid);
			vendorList.add(vendor);
		} */
		
		
//		if(isSearch == true) {
//			List<Integer> vendorIds = vendorRepo.vendorIds(searchDto.getKeyword(), searchDto.getShopTypeId());
//			Vendor vendor;
//			for(Integer vendorid:vendorIds) {
//				vendor = findVendorById(vendorid);
//				vendorList.add(vendor);
//			}
//			
//		}else {
//			try {
//				vendorList = vendorRepo.findAll();
//			}catch(Exception exc) {
//			exc.printStackTrace();
//			}
//		}

		VendorDto vendorDto = new VendorDto();

		for (Vendor vendorObj : vendorList) {
			try {

				Double distance = DistanceCalculator.distance(Double.valueOf(searchDto.getLatitude()),
						Double.valueOf(vendorObj.getLatitude()), Double.valueOf(searchDto.getLongitude()),
						Double.valueOf(vendorObj.getLongitude()));
				if ((vendorObj.getRegisterStep().equals("2") && vendorObj.isOtpVerified() == true) || (vendorObj.getTempMobile()!=null && !vendorObj.getTempMobile().isEmpty())) {

					if (isSearch) {
						vendorDto = new VendorDto();
						vendorDto.setDistance(String.valueOf(distance));
						vendorDto.setVendorId(vendorObj.getvendorId());
						vendorDto.setName(vendorObj.getName());
						vendorDto.setAddress(vendorObj.getAddress());
						vendorDto.setLatitude(vendorObj.getLatitude());
						vendorDto.setLongitude(vendorObj.getLongitude());

						if (userMediaDaoService.getImageByVenodorId(vendorObj) == null
								|| userMediaDaoService.getImageByVenodorId(vendorObj).isEmpty()) {
							vendorDto.setImage("");
						} else {
							vendorDto.setImage(contextPath + userMediaDaoService.getImageByVenodorId(vendorObj));
						}

						vendorDto.setAverageRating(
								String.valueOf(vendorRatingDaoServices.getAvgRatingByVendorId(vendorObj)));
						nearbyVendorList.add(vendorDto);
					} else if (distance <= Constants.MAX_NEARBY_DISTANCE) {
						vendorDto = new VendorDto();
						vendorDto.setDistance(String.valueOf(distance));
						vendorDto.setVendorId(vendorObj.getvendorId());
						vendorDto.setName(vendorObj.getName());
						vendorDto.setAddress(vendorObj.getAddress());
						vendorDto.setLatitude(vendorObj.getLatitude());
						vendorDto.setLongitude(vendorObj.getLongitude());

						if (userMediaDaoService.getImageByVenodorId(vendorObj) == null
								|| userMediaDaoService.getImageByVenodorId(vendorObj).isEmpty()) {
							vendorDto.setImage("");
						} else {
							vendorDto.setImage(contextPath + userMediaDaoService.getImageByVenodorId(vendorObj));
						}

						vendorDto.setAverageRating(
								String.valueOf(vendorRatingDaoServices.getAvgRatingByVendorId(vendorObj)));
						nearbyVendorList.add(vendorDto);
					}

				}
			} catch (Exception ex) {
				ex.printStackTrace();

			}
		}

		return nearbyVendorList;

	}

	public CommonDto getVendorDetailById(Vendor vendor, boolean isVendorById) {
		CommonDto commonDto = new CommonDto();
		// List<UserMediaDto> vendorImages = new ArrayList<UserMediaDto>();
		List<ServicesDto> servicesDto = null;
		List<OpeningClosingTimeDto> timings = null;
		List<String> vendorImages = new ArrayList<String>();
		List<UserMediaDto> vendorImagesAndIds = new ArrayList<UserMediaDto>();
//		List<UserMedia> userMedias = userMediaRepo.findByVendor(vendor);
//		UserMediaDto userMediaDto;
//		for (UserMedia userMedia : userMedias) {
//			userMediaDto = new UserMediaDto();
//			userMediaDto.setImageName(userMedia.getMediaName());
//			userMediaDto.setImageUrl(contextPath + userMedia.getMediaUrl());
//			vendorImages.add(userMediaDto);
//		}
		commonDto.setUserId(vendor.getvendorId());
		commonDto.setType(vendor.getType());
		commonDto.setName(vendor.getName());
		commonDto.setMobile(vendor.getMobile());
		commonDto.setUserName(vendor.getEmail());
		commonDto.setLatitude(vendor.getLatitude());
		commonDto.setLongitude(vendor.getLongitude());
		commonDto.setAddress(vendor.getAddress());
		commonDto.setShopTypeId(vendor.getShopType().getShopTypeId());
		commonDto.setSeats(vendor.getSeats());
		// vendorImages = userMediaDaoService.getImagesbyUserId(vendor.getvendorId(),
		// vendor.getType());
		commonDto.setBookingAllowed(vendor.getIsBookingAllowed() == 0 ? false : true);
		servicesDto = vendorServicesDaoService.getServicesByVendor(vendor);
		timings = openCloseTimeDaoService.getTimingsByVendorId(vendor);
		// commonDto.setUserImage(vendorImages.get(0));
		if (isVendorById) {
			vendorImagesAndIds = userMediaDaoService.getImagesbyUserId(vendor.getvendorId(), "VENDOR");
			commonDto.setUserImages(vendorImagesAndIds);
		} else {
			vendorImages = userMediaDaoService.getImagesByVendor(vendor);
			commonDto.setImages(vendorImages);
		}

		if (vendorImages.size() < 1) {
			commonDto.setImage("");
		} else {
			commonDto.setImage(vendorImages.get(0));
		}

		commonDto.setServices(servicesDto);
		commonDto.setTimings(timings);
		return commonDto;

	}

	public CommonDto getServicesByVendorId(Vendor vendor) {
		CommonDto commonDto = new CommonDto();
		List<OpeningClosingTimeDto> openingClosingTimeDtos = new ArrayList<OpeningClosingTimeDto>();
		openingClosingTimeDtos = openCloseTimeDaoService.getTimingsByVendorId(vendor);
		String weekDay = "";
		for (OpeningClosingTimeDto timings : openingClosingTimeDtos) {
			if (timings.getStatus().equals("CLOSE")) {
				weekDay = timings.getWeekDay();
				break;
			}
		}
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int currentDayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		int offDayOfWeek = CommonUtils.getCurrentDayValue(weekDay);
		List<ServicesDto> serviceDtos = vendorServicesDaoService.getServicesByVendor(vendor);
		commonDto.setUserId(vendor.getvendorId());
		commonDto.setServices(serviceDtos);
		if (currentDayOfWeek == offDayOfWeek) {
			commonDto = null;
		}
		// List<OpenCloseTime> openingClosingArray = new ArrayList<>();
		// Compare status of the stored days with the current logged in day.
		// If status is closed for that particular day provide error message
		// Else provide Services list by Vendor.
		return commonDto;
	}

	public List<ServicesDto> getServicesList(Vendor vendor) {
		CommonDto commonDto = new CommonDto();
		List<OpeningClosingTimeDto> openingClosingTimeDtos = new ArrayList<OpeningClosingTimeDto>();
		openingClosingTimeDtos = openCloseTimeDaoService.getTimingsByVendorId(vendor);
		String weekDay = "";
		for (OpeningClosingTimeDto timings : openingClosingTimeDtos) {
			if (timings.getStatus().equals("CLOSE")) {
				weekDay = timings.getWeekDay();
				break;
			}
		}
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int currentDayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		int offDayOfWeek = CommonUtils.getCurrentDayValue(weekDay);
		List<ServicesDto> serviceDtos = vendorServicesDaoService.getServicesByVendor(vendor);

		return serviceDtos;
	}

	public List<TimeSlotDto> getTimeSlots(Vendor vendor, String selectedDate) throws ParseException {

		LocalDateTime now = LocalDateTime.now();
		Date currentSelectedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(selectedDate);
		LocalDateTime localSelectedDate = CommonUtils.convertToLocalDateTimeViaInstant(currentSelectedDate);
		LocalDate bookingDate = CommonUtils.convertToLocalDateViaInstant(currentSelectedDate);
		if (bookingDate.isBefore(LocalDate.now())) {
			throw new GojoException(ApiErrorCode.NO_SLOTS_EXISTS + " Cannot book at previous date");
		}

		List<AppointmentDto> appointmentDtos = appointmentDaoService.appointmentListByVendor(vendor);
		List<OpeningClosingTimeDto> openingClosingTimeDtos = new ArrayList<OpeningClosingTimeDto>();
		openingClosingTimeDtos = openCloseTimeDaoService.getTimingsByVendorId(vendor);
		// New code
		String weekDay = "";
		String shopStatus = "";

		Calendar c = Calendar.getInstance();
		c.setTime(currentSelectedDate);
		int DayOfWeek = c.get(Calendar.DAY_OF_WEEK);

		for (OpeningClosingTimeDto timings : openingClosingTimeDtos) {
			if (timings.getStatus().equals("CLOSE") && timings.getWeekDay().equals(CommonUtils.getDayName(DayOfWeek))) {
				weekDay = timings.getWeekDay();
				shopStatus = timings.getStatus();
				break;
			}
		}
		int offDayOfWeek = CommonUtils.getCurrentDayValue(weekDay);

		if (DayOfWeek == offDayOfWeek || shopStatus.equals("CLOSE")) {
			throw new GojoException(ApiErrorCode.NO_SLOTS_EXISTS + " Shop is closed ");
		}
		// New code

		List<String> tempBlockedTime = new ArrayList<String>();
		List<String> BlockedTime = new ArrayList<String>();
		List<String> availableTime = new ArrayList<String>();
		Map<String, Integer> slotCount = new HashMap<String, Integer>();

		int seats = Integer.valueOf(vendor.getSeats());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentSelectedDate);
		int currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

		String[] timings = CommonUtils.getOpenCloseTime(openingClosingTimeDtos,
				CommonUtils.getDayName(currentDayOfWeek));
		List<String> originalTimeSlots = CommonUtils.getSlotsByTime(timings[0], timings[1]);

		for (AppointmentDto appointmentDto : appointmentDtos) {
			Date appointDate = appointmentDto.getAppointmentDate();
			LocalDate localAppointDate = CommonUtils.convertToLocalDateViaInstant(appointDate);
			if (appointmentDto.getSlotStatus().equals("BLOCKED")
					&& localSelectedDate.toLocalDate().isEqual(localAppointDate)) {
				tempBlockedTime.add(appointmentDto.getSlot());

			}
		}
		List<TimeSlotDto> timeSlotDtos = new ArrayList<TimeSlotDto>();
		slotCount = CommonUtils.countFrequencies(tempBlockedTime);

		if (now.isBefore(localSelectedDate)) {
			if (tempBlockedTime.size() < 1) {

				TimeSlotDto timeSlotDto = null;
				for (String slot : originalTimeSlots) {
					timeSlotDto = new TimeSlotDto();
					timeSlotDto.setTime(slot);
					timeSlotDto.setIsBlocked(false);
					timeSlotDtos.add(timeSlotDto);
				}

			} else {

				for (Map.Entry<String, Integer> val : slotCount.entrySet()) {
				/*mod on 30-05-2021	if (val.getValue() == seats) { */
					if (val.getValue() >= seats) {
						BlockedTime.add(val.getKey());
					} else {
						availableTime.add(val.getKey());
					}
				}

				TimeSlotDto timeSlotDto = null;
				for (String slot : originalTimeSlots) {
					timeSlotDto = new TimeSlotDto();
					if (BlockedTime.contains(slot)) {
						timeSlotDto.setIsBlocked(true);
					} else {
						timeSlotDto.setIsBlocked(false);
					}

					timeSlotDto.setTime(slot);

					timeSlotDtos.add(timeSlotDto);
				}
			}
		} else {
			if (tempBlockedTime.size() < 1) {

				LocalDate currentDate = LocalDate.now();
				LocalDateTime currentTime = null;

				TimeSlotDto timeSlotDto = null;
				for (String slot : originalTimeSlots) {
					timeSlotDto = new TimeSlotDto();
					String[] temporalSplits = CommonUtils.getHourMin(slot);
					currentTime = currentDate.atTime(Integer.valueOf(temporalSplits[0]),
							Integer.valueOf(temporalSplits[1]));
					if (currentTime.isBefore(LocalDateTime.now())) {
						timeSlotDto.setIsBlocked(true);
					} else {
						timeSlotDto.setIsBlocked(false);
					}

					timeSlotDto.setTime(slot);

					timeSlotDtos.add(timeSlotDto);
				}

			} else {

				LocalDate currentDate = LocalDate.now();
				LocalDateTime currentTime = null;

				for (Map.Entry<String, Integer> val : slotCount.entrySet()) {
					String[] temporalSplits = CommonUtils.getHourMin(val.getKey());

					currentTime = currentDate.atTime(Integer.valueOf(temporalSplits[0]),
							Integer.valueOf(temporalSplits[1]));

					if (val.getValue() == seats || currentTime.isBefore(LocalDateTime.now())) {
						BlockedTime.add(val.getKey());
					} else {
						availableTime.add(val.getKey());
					}
				}

				TimeSlotDto timeSlotDto = null;
				for (String slot : originalTimeSlots) {
					String[] temporalSplits = CommonUtils.getHourMin(slot);
					currentTime = currentDate.atTime(Integer.valueOf(temporalSplits[0]),
							Integer.valueOf(temporalSplits[1]));
					timeSlotDto = new TimeSlotDto();
					if (BlockedTime.contains(slot) || currentTime.isBefore(LocalDateTime.now())) {
						timeSlotDto.setIsBlocked(true);
					} else {
						timeSlotDto.setIsBlocked(false);
					}

					timeSlotDto.setTime(slot);

					timeSlotDtos.add(timeSlotDto);
				}
			}

		}
		return timeSlotDtos;
	}

	public Vendor updatePassword(Vendor vendor) {
		return vendorRepo.save(vendor);
	}
	
	public List<CommonDto> getVendorBySearch(String searchTerm) {
		List<Vendor> vendorList = vendorRepo.searchVendor(searchTerm);
		//getVendorDetailById();
		List<CommonDto> vendorInfo = vendorList.stream()
				.map(vendor -> getAllUnPaidDues(vendor, getVendorDetailById(vendor,true))						
						)
				
				.collect(Collectors.toList());
		
		return vendorInfo;
	}

	public void confirmCancelOrder(Vendor vendor, Order order, String flag) throws Exception {
		
		
		if (orderRepo.findByVendorAndOrderId(vendor, order.getOrderId()) == null) {
			throw new GojoException(
					ApiErrorCode.INVALID_INPUT + " Invalid order id or vendor has no such orders pending");
		}

		order.setStatus(bookingStatuRepo.findById(Integer.valueOf(flag)).get());
		order.setLastUpdatedDate(new Date());
		try {
			orderRepo.save(order);
			if (flag.equals("2")) {
				
				List<Appointment> appointmentList = (List<Appointment>) this.appointRepo.findByOrder(order);
				for (Appointment appointment : appointmentList) {
					appointment.setSlotStatus("RELEASED");
					appointment.setLastUpdatedDate(new Date());
					appointRepo.save(appointment);
				}
				
				VendorMonthly transactionMonthly = vendorMonthlyRepo.findByTransactionId(order.getFccPayId());
				if(transactionMonthly == null) {
					throw new GojoException("Unexpected error");
				}
				
				Double totalAmnt = Double.valueOf(transactionMonthly.getTotalAmountVendor());
				Double newAmnt = Double.valueOf("0.0");
				
				if(totalAmnt > 0) {
					newAmnt = totalAmnt - order.getTotalPrice();
				}
				
				Double transactionAmnt = Double.valueOf(transactionMonthly.getTransactionAmnt());
				Double newTransAmnt = Double.valueOf("0.0");
				
				if(transactionAmnt > 0) {newTransAmnt = transactionAmnt - Double.valueOf("10.0");}
				
				
				Integer bookingCount = Integer.valueOf(transactionMonthly.getTotalConfirmedOrders());
			    Integer newCount = 0;
			    
			    if(bookingCount > 0) {newCount = 	bookingCount - 1;}
			    
			    
				transactionMonthly.setTotalAmountVendor(String.valueOf(newAmnt));
				transactionMonthly.setTransactionAmnt(String.valueOf(newTransAmnt));
				transactionMonthly.setTotalConfirmedOrders(String.valueOf(newCount));
				
				try {
					vendorMonthlyRepo.save(transactionMonthly);	
				}catch(Exception exc) {
					throw new GojoException("Erro in updating transaction");
				}

			}else if(flag.equals("1")) {
				VendorMonthly transactionMonthly = vendorMonthlyRepo.findByTransactionId(order.getFccPayId());
				if(transactionMonthly == null) {
					throw new GojoException("Unexpected error");
				}
				
				Double totalAmnt = Double.valueOf(transactionMonthly.getTotalAmountVendor());
				Double newAmnt = totalAmnt + order.getTotalPrice();
				
				
				Double transactionAmnt = Double.valueOf(transactionMonthly.getTransactionAmnt());
				Double newTransAmnt = transactionAmnt + Double.valueOf("10.0");
				
				Integer bookingCount = Integer.valueOf(transactionMonthly.getTotalConfirmedOrders());
			    Integer newCount = 	bookingCount + 1;
			
				
				transactionMonthly.setTotalAmountVendor(String.valueOf(newAmnt));
				transactionMonthly.setTransactionAmnt(String.valueOf(newTransAmnt));
				transactionMonthly.setTotalConfirmedOrders(String.valueOf(newCount));
				try {
					vendorMonthlyRepo.save(transactionMonthly);	
				}catch(Exception exc) {
					throw new GojoException("Erro in updating transaction");
				}
				
			}
		} catch (Exception ex) {
			throw new Exception("Error in changing order status");
		}
	}

	@Scheduled(fixedRate = 15000L)
	public void scheduleTaskWithFixedRate() {

	}

	public String[] getOfflineOnlinePayCount(final Vendor vendor) {
		//String[] transactIds;
		int onlineCount = this.vendorRepo.paymentCount(Integer.valueOf(1), vendor.getvendorId());
		//int offlineCount = this.vendorRepo.paymentCount(Integer.valueOf(2), vendor.getvendorId());
		int offlineCount = 0;
		//List<VendorMonthly> monthlyTrans = vendorMonthlyRepo.getAllUnPaidTransByVendor(vendor.getvendorId());
//		transactIds = new String[monthlyTrans.size()];
//		
//		int i = 0;
//		for(VendorMonthly transactions : monthlyTrans) {
//			transactIds[i] = transactions.getTransactionId();
//			i++;
//		}
		
		List<Order> bookings = getBookingsByFccPayStatus(vendor.getvendorId());
		offlineCount = bookings.size();
		final String[] paymentCounts = { String.valueOf(onlineCount), String.valueOf(offlineCount) };
		return paymentCounts;
	}

	public CommonDto getVendorDetailsForOtpResponse(final Integer vendorId) {
		final Vendor vendor = this.findVendorById(vendorId);
		final CommonDto vendorInfo = new CommonDto();
		vendorInfo.setUserName(vendor.getEmail());
		vendorInfo.setLatitude(vendor.getLatitude());
		vendorInfo.setLongitude(vendor.getLongitude());
		vendorInfo.setType("VENDOR");
		return this.getUserInfo(vendorInfo, "EMAIL", false);
	}

	public void calculateMonthlyIncome() throws ParseException {

		Date current = new Date();
		Date firstDay = commonUtils.getFirstLastDayOfMonth(commonUtils.getPreviousMonthDate(current), true);
		Date lastDay = commonUtils.getFirstLastDayOfMonth(commonUtils.getPreviousMonthDate(current), false);
		// List<Order> orders = ordersRepo.getOfflineOrdersMonth( firstDay,lastDay);
		List<Integer> processedVendors = new ArrayList<Integer>();
		List<Vendor> vendors = vendorRepo.getVendorsCreatedPastMonths();
		for (Vendor vendor : vendors) {
			List<Order> orders = ordersRepo.getTotalConfirmedOfflineOrdersMonthByVendor(firstDay, lastDay,
					vendor.getvendorId());
			VendorMonthly monthlyTransaction = new VendorMonthly();
			Random rand = new Random();
			if (orders.size() == 0) {
				monthlyTransaction.setTotalAmountVendor("0");
			} else {
				monthlyTransaction.setTotalAmountVendor(
						String.valueOf(ordersRepo.getSumOfEarningsMonth(firstDay, lastDay, vendor.getvendorId())));
			}

			monthlyTransaction.setTransactionId(CommonUtils
					.hashCal("SHA-256", Integer.toString(rand.nextInt()) + (System.currentTimeMillis() / 1000L))
					.substring(0, 20));
			monthlyTransaction.setVendor(vendor);
			// monthlyTransaction.setPayMonth(lastDay.getMonth());
			monthlyTransaction.setTotalConfirmedOrders(String.valueOf(orders.size()));

			monthlyTransaction.setCreatedDate(new Date());
			monthlyTransaction.setIsPaid(0);
			monthlyTransaction.setMonth(CommonUtils.theMonth(commonUtils.getMonthByDate(firstDay)));
			monthlyTransaction.setDateOfMonth(commonUtils.getPreviousMonthDate(current));
			monthlyTransaction
					.setFinYaar(String.valueOf(commonUtils.getYearByDate(commonUtils.getPreviousMonthDate(current))));
			monthlyTransaction.setChargeFactor(String.valueOf("10.0"));
			monthlyTransaction = vendorMonthlyRepo.save(monthlyTransaction);
			for(Order order : orders) {
				order.setFccPayId(monthlyTransaction.getTransactionId());
				orderRepo.save(order);
			}
			
			double transactionAmnt = orders.size() * Double.valueOf("10.0");
			monthlyTransaction.setTransactionAmnt(String.valueOf(transactionAmnt));
			vendorMonthlyRepo.save(monthlyTransaction);
			orders.clear();
		}
		/*
		 * for(Order order: orders) {
		 * 
		 * if(processedVendors.contains(order.getVendor().getvendorId())) { continue; }
		 * else {
		 * 
		 * Double totalAmnt = ordersRepo.getSumOfEarningsMonth(firstDay, lastDay,
		 * order.getVendor().getvendorId()); VendorMonthly monthlyTransaction = new
		 * VendorMonthly(); Random rand = new Random();
		 * 
		 * monthlyTransaction.setTransactionId(CommonUtils.hashCal("SHA-256",
		 * Integer.toString(rand.nextInt()) + (System.currentTimeMillis() /
		 * 1000L)).substring(0, 20)); monthlyTransaction.setVendor(order.getVendor());
		 * //monthlyTransaction.setPayMonth(lastDay.getMonth());
		 * monthlyTransaction.setTotalConfirmedOrders(String.valueOf(ordersRepo.
		 * getTotalConfirmedOfflineOrdersMonthByVendor(firstDay, lastDay,
		 * order.getVendor().getvendorId()).size()));
		 * monthlyTransaction.setTotalAmountVendor(String.valueOf(totalAmnt));
		 * monthlyTransaction.setCreatedDate(new Date());
		 * monthlyTransaction.setIsPaid(0);
		 * monthlyTransaction.setMonth(CommonUtils.theMonth(commonUtils.getMonthByDate(
		 * firstDay)));
		 * monthlyTransaction.setFinYaar(String.valueOf(commonUtils.getYearByDate(
		 * firstDay))); monthlyTransaction.setChargeFactor(String.valueOf("5.0"));
		 * vendorMonthlyRepo.save(monthlyTransaction);
		 * System.out.println("Total amount "
		 * +totalAmnt+" "+" Vendor "+order.getVendor().getvendorId());
		 * processedVendors.add(order.getVendor().getvendorId()); } }
		 * processedVendors.clear();
		 */

	}
	
	
	public void initiateVendorMonthly() {
		Date current = new Date();
		//Date firstDay = commonUtils.getFirstLastDayOfMonth(commonUtils.getPreviousMonthDate(current), true);
		//Date lastDay = commonUtils.getFirstLastDayOfMonth(commonUtils.getPreviousMonthDate(current), false);
		String year = String.valueOf(commonUtils.getYearByDate(commonUtils.getPreviousMonthDate(current)));
		String month = CommonUtils.theMonth(commonUtils.getMonthByDate(current));
		Random rand = new Random();
		List<Vendor> vendors = vendorRepo.findAll();
		
		for (Vendor vendor : vendors) {
			VendorMonthly monthlyTransaction = vendorMonthlyRepo.getMonthlyByVendorMonthYear(vendor.getvendorId(), month, year);
			if(monthlyTransaction == null) {
				monthlyTransaction = new VendorMonthly();
				monthlyTransaction.setVendor(vendor);
				monthlyTransaction.setIsPaid(0);
				monthlyTransaction.setTransactionId(CommonUtils
						.hashCal("SHA-256", Integer.toString(rand.nextInt()) + (System.currentTimeMillis() / 1000L))
						.substring(0, 20));
				monthlyTransaction.setTransactionAmnt("0");
				monthlyTransaction.setTotalConfirmedOrders("0");
				monthlyTransaction.setTotalAmountVendor("0");
				monthlyTransaction.setMonth(month);
				monthlyTransaction.setFinYaar(year);
				monthlyTransaction.setChargeFactor("10.0");
				monthlyTransaction.setCreatedDate(new Date());
				monthlyTransaction.setDateOfMonth(new Date());
				//monthlyTransaction.setIsPayBlocked(1);
				vendorMonthlyRepo.save(monthlyTransaction);
			}
		}
	}

	public boolean modifyBookingAction(Vendor vendor) {
		List<VendorMonthly> monthlyTransactions = new ArrayList<>();
		Boolean flag = true;
		Date current = new Date();

		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();

		cal2.setTime(current);
		
		monthlyTransactions = vendorMonthlyRepo.getMonthlyTrnFccByVendor(vendor.getvendorId());
		if (monthlyTransactions.size() != 0) {
			
			for (VendorMonthly monthlyTransaction : monthlyTransactions) {
				cal1.setTime(monthlyTransaction.getDateOfMonth());
				if(!(cal2.get(Calendar.MONTH) == cal1.get(Calendar.MONTH))) {
					if (!monthlyTransaction.getTotalAmountVendor().equals("0") && 
					    !monthlyTransaction.getTotalAmountVendor().equals("0.0")) {
						/* To check if the payment to Fcc is pending */
						if (monthlyTransaction.getIsPaid() == 0) { 
							/* process to disable booking button */
							flag = false;
							break;
						}
					}
				}
				
			}
		}
		

		return flag;
	}

	public CommonDto getAllUnPaidDues(Vendor vendor, CommonDto commonDto) {
		//Add filter isPayBlocked in getMonthlyUnPaidTransactions
		List<VendorMonthly> monthlyTrans = vendorMonthlyRepo.getMonthlyUnPaidTransactions(vendor.getvendorId());
		StringBuilder trns = new StringBuilder("");
		Double totaAmnt = Double.valueOf("0.0");
		for (VendorMonthly vendorMonthly : monthlyTrans) {
			
	/*		if(!new Date().after(vendorMonthly.getPayReleaseDate())) {
				continue;
			} */
			if (monthlyTrans.size() > 1) {
				trns.append(vendorMonthly.getTransactionId()).append(",");
			} else {
				trns.append(vendorMonthly.getTransactionId());
			}

			totaAmnt = totaAmnt + Double.valueOf(vendorMonthly.getTransactionAmnt());
		}
		String commaseparatedTrnslist = trns.toString();
		commonDto.setTotalAmount(String.valueOf(totaAmnt));
		commonDto.setTransactionIds(commaseparatedTrnslist);
		return commonDto;
	}

	public void updateBookingStatusVendor() {
		List<Vendor> vendors = vendorRepo.findAll();
		boolean status = true;
		for (Vendor vendor : vendors) {
			status = modifyBookingAction(vendor);
			vendor.setIsBookingAllowed(status ? 1 : 0);
			vendor.setLastUpdatedDate(new Date());
			vendorRepo.save(vendor);
		}
	}

	public Boolean isTransactionCompleted(CommonDto commonDto) {
		boolean status = false;
		List<Object> transactionIds = new ArrayList<Object>();
		
		
		if (commonDto.getUserId() == null) {
			throw new GojoException(ApiErrorCode.INVALID_INPUT + " Vendor id is missing");
		}

		Vendor vendor = vendorRepo.findById(commonDto.getUserId()).get();
		if (vendor == null) {
			throw new ResourceNotFoundException(" Invalid Vendor Id");

		}

		if (commonDto.getTransactionIds() == null || commonDto.getTransactionIds().isEmpty()) {
			throw new GojoException(ApiErrorCode.INVALID_INPUT + " Transaction ids missing");
		}

		if (commonDto.getOnlineTransactionId() == null || commonDto.getOnlineTransactionId().isEmpty()) {
			throw new GojoException(ApiErrorCode.INVALID_INPUT + " Online transaction id is missing");
		}

		if (commonDto.getPaymentStatus() == null || commonDto.getPaymentStatus().isEmpty()) {
			throw new GojoException(ApiErrorCode.INVALID_INPUT + " Payment status is missing");
		}

		if (commonDto.getTotalAmount() == null || commonDto.getTotalAmount().isEmpty()) {
			throw new GojoException(ApiErrorCode.INVALID_INPUT + " Total amount is missing");
		}

		if(commonDto.getTransactionIds().split(",").length > 1) {
		    transactionIds = Arrays.asList(commonDto.getTransactionIds().split(","));	
		}else {
			transactionIds.add(commonDto.getTransactionIds());
		}
		
		Integer trans = transactionIds.size();

		VendorMonthly monthlyTransaction;
		for (Object transactionId : transactionIds) {
			monthlyTransaction = vendorMonthlyRepo.findByTransactionId(String.valueOf(transactionId));
			if (!(monthlyTransaction == null)) {
				// process and update the payment status
				monthlyTransaction.setIsPaid(Integer.valueOf(commonDto.getPaymentStatus()));
				monthlyTransaction.setLastUpdatedDate(new Date());
				
				if (Integer.valueOf(commonDto.getPaymentStatus()) == 1) {
					monthlyTransaction.setOnlineTransactionId(commonDto.getOnlineTransactionId());
					
					try {
						vendorMonthlyRepo.save(monthlyTransaction);
						
					}catch(Exception exc) {
						throw new GojoException("Error in updating the payment status");
					}
				}
				// and then update the bookingAllowedStatus
			}
		}
		if (Integer.valueOf(commonDto.getPaymentStatus()) == 1) {
			vendor.setIsBookingAllowed(1);
			vendor.setLastUpdatedDate(new Date());
			try {
				vendorRepo.save(vendor);
				status = true;
			} catch (Exception exc) {
				throw new GojoException("Error in updating the vendor booking status");
			}

		}
		return status;
	}
	
	//Get Orders by FccPayment status
	public List<Order> getBookingsByFccPayStatus(Integer vendorId){
		
		
		List<String> transactIds = vendorMonthlyRepo.getAllUnPaidTransByVendor(vendorId)
				.stream()
				.map(monthlyTran -> monthlyTran.getTransactionId())
				.collect(Collectors.toList());
		
		return orderRepo.getTotalConfirmedOfflineOrdersMonthByFccPayId(transactIds);	
	}
	
	  public CommonDto getVendorDetailsForpayment(final Integer vendorId) {
	        final Vendor vendor = this.findVendorById(vendorId);
	        final CommonDto vendorInfo = new CommonDto();
	        vendorInfo.setUserName(vendor.getEmail());
	        vendorInfo.setLatitude(vendor.getLatitude());
	        vendorInfo.setLongitude(vendor.getLongitude());
	        vendorInfo.setType("VENDOR");
	        return this.getUserInfo(vendorInfo, "EMAIL", false);
	    }
	public void enablePayEOM() {
		List<Vendor> vendorList = vendorRepo.findAll();
		Date current = new Date();
		String year = String.valueOf(commonUtils.getYearByDate(commonUtils.getPreviousMonthDate(current)));
		String month = CommonUtils.theMonth(commonUtils.getMonthByDate(commonUtils.getPreviousMonthDate(current)));
		//VendorMonthly vendorMonthly;
		
		     vendorList
		              .stream()
		              .filter(vendor -> vendorMonthlyRepo.getMonthlyByVendorMonthYear(vendor.getvendorId(), month, year) != null)
		              .forEach(vendor ->{
			           VendorMonthly vendorMonthly = vendorMonthlyRepo.getMonthlyByVendorMonthYear(vendor.getvendorId(), month, year);
			           vendorMonthly.setIsPayBlocked(0);
			           try {
				           vendorMonthlyRepo.save(vendorMonthly);
			           }catch(Exception exc) {
			        	   throw new GojoException("Unable to update the pay status");
			           }
		});
		
		
		
		
		
		
		
		
		
	/* mod on 06062021 replaced by stream api	for(Vendor vendor : vendorList) {
			vendorMonthly = vendorMonthlyRepo.getMonthlyByVendorMonthYear(vendor.getvendorId(), month, year);
			if(vendorMonthly!=null) {
				vendorMonthly.setIsPayBlocked(0);
				try {
					vendorMonthlyRepo.save(vendorMonthly);
				}catch(Exception exc) {
					throw new GojoException("Unable to update the pay status");
				}
			}
			
			
			
		}  */
	}
	
	public List<BookingDto> getBookingsByDateAndVendor(Date startDate, Date endDate, Integer status)
	{
		List<Order> fetchedOrders = orderRepo.getOrdersByDateRange(startDate, endDate, status);
		
		return fetchedOrders.stream().map(order -> new BookingDto(String.valueOf(order.getVendor().getvendorId()), order.getVendor().getName(),
				String.valueOf(order.getCustomer().getCustomerId()),order.getCustomer().getName(), order.getStatus().getStatus(),  CommonUtils.formatDate(order.getOrderDate(), Constants.DATE_FORMAT_ddMMMyyyy),
				CommonUtils.formatDate(order.getAppointmentDate(),Constants.DATE_FORMAT_ddMMMyyyy),String.valueOf(order.getTotalPrice()), String.valueOf(order.getPaymentMode().getPaymentModeId() == 1?"Online":"Offline"),
				order.getTransactionId(),	String.valueOf(order.getTotalServicesBooked())
				
				)).collect(Collectors.toList());
	}
}
