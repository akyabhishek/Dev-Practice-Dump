
package com.fcc.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fcc.domain.OpeningClosingTime;
import com.fcc.domain.Vendor;
import com.fcc.domain.VendorServices;
import com.fcc.model.OpeningClosingTimeDto;
import com.fcc.model.ServicesDto;
import com.fcc.repository.VendorServicesRepo;

@Component
public class CommonUtils {

	public static Double MAX_NEARBY_PROPERTY_DISTANCE = 5.0;
	public static Double MAX_NEARBY_REALTOR_DISTANCE = 5.0;
	private static final DateFormat dateFormat = new SimpleDateFormat(Constants.DATETIME__FORMAT_TRIALEXP);

	@Autowired
	VendorServicesRepo vendorServicesRepo;

	@Value("${server.dir}")
	private String uploadDir;
//	public static Set<String> roles = new HashSet<String>(
//			Arrays.asList(ROLE.ADMIN.toString(), ROLE.USER.toString(), ROLE.STUDENT.toString()));

	public static Date formatDate(String strDate, String format) {
		if (null != strDate && !strDate.isEmpty()) {
			try {
				return new SimpleDateFormat(format).parse(strDate);
			} catch (ParseException ex) {
				// logger.debug("Error while parsing date: " + strDate, ex);
				System.out.println("Error while parsing date: " + strDate + ex.getMessage());
			}
		}
		return null;
	}

	public static String formatDate(Date date, String format) {
		if (null != date) {
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat(format);
				String sqlDate = dateFormat.format(date);
				return sqlDate;
			} catch (Exception ex) {
				// logger.debug("Error while parsing date: " + date, ex);
				System.out.println("Error while parsing date: " + date + ex.getMessage());
			}
		}
		return null;
	}

	public static Date zeroTime(final Date date) {
		return CommonUtils.setTime(date, 0, 0, 0, 0);
	}

	public static Date setTime(final Date date, final int hourOfDay, final int minute, final int second, final int ms) {
		final GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.set(Calendar.HOUR_OF_DAY, hourOfDay);
		gc.set(Calendar.MINUTE, minute);
		gc.set(Calendar.SECOND, second);
		gc.set(Calendar.MILLISECOND, ms);
		return gc.getTime();
	}

	public static Date getCouponExpiryDate() {

		Date currentDate = new Date();

		// convert date to calendar
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);

		// Number of Days to add
		cal.add(Calendar.DAY_OF_MONTH, 5);
		Date currentDatePlusThree = cal.getTime();

		return currentDatePlusThree;

	}

	public static boolean isPendingFortwoHr(Date createdDate) {
		Date currentDateTime = new Date();
		long currentTime = currentDateTime.getTime();
		long createdTime = createdDate.getTime();
		long duration = currentTime - createdTime;
		long diffHrs = TimeUnit.MILLISECONDS.toHours(duration);
		if (diffHrs >= 2) {
			return true;
		}
		return false;

	}

	public static boolean isCouponActive(Date couponCreatedDate) {
		LocalDate now = LocalDate.now();
		LocalDate couponDate = convertToLocalDateViaInstant(couponCreatedDate);
		boolean flag = false;
		if (couponDate.isAfter(now) || couponDate.isEqual(now)) {
			flag = true;
		}

		return flag;
	}

	public static boolean isCouponInRange(String originalPrice, String minPrice) {
		Double orgPrice = Double.valueOf(originalPrice);
		Double mPrice = Double.valueOf(minPrice);
		if (orgPrice >= mPrice)
			return true;
		return false;
	}

	public static long getTimeDiffInMin(Date oldDate) {

		Date currentDate = new Date();
		long currentTime = currentDate.getTime();
		long oldTime = oldDate.getTime();
		long diff = currentTime - oldTime;
		long diffMinutes = diff / (60 * 1000) % 60;
		return diffMinutes;
	}

	public static String[] getAuthCredentials(String encryptedAuthId) throws Exception {
		try {
			return EncryptionUtil.decrypt(encryptedAuthId).trim().split(",");
		} catch (Exception ex) {
//			logger.error("Error while decrypting authId: " + encryptedAuthId, ex);
			throw new Exception("Invalid credentials");
		}
	}

	public static String setAuthCredentials(String userId, String deviceId, String role, String timeStamp)
			throws Exception {
		String authHeader = userId + "," + deviceId + "," + role + "," + timeStamp;
		try {
			return EncryptionUtil.encrypt(authHeader);
		} catch (Exception ex) {
			throw new Exception("Cannot encrypt the auth header");
		}

	}

	public static Boolean isURLFoundInList(String reqUrl, List<String> urlList) {
		Boolean found = false;
		for (String url : urlList) {
			if (reqUrl.contains(url)) {
				found = true;
				break;
			}
		}
		return found;
	}

	public static String getRandDigitsOtp() {
		Random random = new Random();
		String otp = String.format("%04d", random.nextInt(10000));
		return otp;
	}

	public String saveImages(MultipartFile file, String fileName) throws IOException {
		String imageName = "";
		if (!file.isEmpty()) {
			byte[] bytes = file.getBytes();
			imageName = fileName + "."
					+ file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);

			// String fileLocation = new File(Constants.imageFolder).getAbsolutePath() +"/"+
			// imageName.trim();
			String fileLocation = new File(uploadDir).getAbsolutePath() + "/" + imageName.trim();
			// String fileLocation = new File(imageName.trim()).getAbsolutePath();
			// File uploadfolder = new ClassPathResource("static/images").getFile();
			System.out.println("File location " + fileLocation);

			Path path = Paths.get(fileLocation);
			// Path path = Paths.get("/static/images/" + imageName.trim());
			// File imgFile = new File("/static/images/" + imageName.trim());
			// Resource resource = new ClassPathResource("static/images/");

			// Path path = Paths.get(resource.getURL().getPath());
			Files.write(path, bytes);

		}

		return imageName;
	}

	public boolean deleteFile(String fileName) {
		File file = new File(uploadDir + "/" + fileName);

		boolean flag = file.delete();

		return flag;
	}

	public static String updateImages(MultipartFile file, String fileName) throws IOException {
		String imageName = "";
		if (!file.isEmpty()) {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(Constants.imageFolder + fileName);
			Files.write(path, bytes);

		}

		return imageName;
	}

	public static int getCurrentDayValue(String weekDay) {
		int value = 0;
		if (weekDay.equalsIgnoreCase("SUNDAY")) {
			value = 1;
		}
		if (weekDay.equalsIgnoreCase("MONDAY")) {
			value = 2;
		}
		if (weekDay.equalsIgnoreCase("TUESDAY")) {
			value = 3;
		}
		if (weekDay.equalsIgnoreCase("WEDNESDAY")) {
			value = 4;
		}
		if (weekDay.equalsIgnoreCase("THURSDAY")) {
			value = 5;
		}
		if (weekDay.equalsIgnoreCase("FRIDAY")) {
			value = 6;
		}
		if (weekDay.equalsIgnoreCase("SATURDAY")) {
			value = 7;
		}

		return value;

	}

	public static String getDayName(int weekDay) {
		String dayOfWeek = "";
		if (weekDay == 1) {
			dayOfWeek = "Sunday";
		}
		if (weekDay == 2) {
			dayOfWeek = "Monday";
		}
		if (weekDay == 3) {
			dayOfWeek = "Tuesday";
		}
		if (weekDay == 4) {
			dayOfWeek = "Wednesday";
		}
		if (weekDay == 5) {
			dayOfWeek = "Thursday";
		}
		if (weekDay == 6) {
			dayOfWeek = "Friday";
		}
		if (weekDay == 7) {
			dayOfWeek = "Saturday";
		}

		return dayOfWeek;

	}

	public static boolean isTimeQuarterHrs(String minutes) {
		List<String> quarterHrs = new ArrayList<String>();
		quarterHrs.add("00");
		quarterHrs.add("15");
		quarterHrs.add("30");
		quarterHrs.add("45");

		boolean flag = false;
		for (String s : quarterHrs) {
			if (s.equals(minutes)) {
				flag = true;
				break;
			}
		}

		return flag;
	}

	public static LocalDateTime getNearestHourQuarter(LocalDateTime datetime) {

		int minutes = datetime.getMinute();
		int mod = minutes % 15;
		LocalDateTime newDatetime;
		newDatetime = datetime.plusMinutes(15 - mod);
//        if (mod < 8) {
//            newDatetime = datetime.minusMinutes(mod);
//        } else {
//            newDatetime = datetime.plusMinutes(15 - mod);
//        }

		newDatetime = newDatetime.truncatedTo(ChronoUnit.MINUTES);

		return newDatetime;
	}

	public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
		return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static LocalDate convertToLocalDateViaMilisecond(Date dateToConvert) {
		return Instant.ofEpochMilli(dateToConvert.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
		return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	public static LocalDateTime convertToLocalDateTimeViaMilisecond(Date dateToConvert) {
		return Instant.ofEpochMilli(dateToConvert.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	public Map<Integer, String> slotMap() {
		Map<Integer, String> slotMapper = new HashMap<Integer, String>();
		slotMapper.put(1, "9:00");
		slotMapper.put(2, "9:15");
		slotMapper.put(3, "9:30");
		slotMapper.put(4, "9:45");
		slotMapper.put(5, "10:00");
		slotMapper.put(6, "10:15");
		slotMapper.put(7, "10:30");
		slotMapper.put(8, "10:45");
		slotMapper.put(9, "11:00");
		slotMapper.put(10, "11:15");
		slotMapper.put(11, "11:30");
		slotMapper.put(12, "11:45");
		slotMapper.put(13, "12:00");
		slotMapper.put(14, "12:15");
		slotMapper.put(15, "12:30");
		slotMapper.put(16, "12:45");
		slotMapper.put(17, "13:00");
		slotMapper.put(18, "13:15");
		slotMapper.put(19, "13:30");
		slotMapper.put(20, "13:45");
		slotMapper.put(21, "14:00");
		slotMapper.put(22, "14:15");
		slotMapper.put(23, "14:30");
		slotMapper.put(24, "14:45");
		slotMapper.put(25, "15:00");
		slotMapper.put(26, "15:15");
		slotMapper.put(27, "15:30");
		slotMapper.put(28, "15:45");
		slotMapper.put(30, "16:00");
		slotMapper.put(31, "16:15");
		slotMapper.put(32, "16:30");
		slotMapper.put(33, "16:45");
		slotMapper.put(34, "17:00");
		slotMapper.put(35, "17:15");
		slotMapper.put(36, "17:30");
		slotMapper.put(37, "17:45");
		slotMapper.put(38, "18:00");
		slotMapper.put(39, "18:15");
		slotMapper.put(40, "18:30");
		slotMapper.put(41, "18:45");
		slotMapper.put(42, "19:00");
		slotMapper.put(43, "19:15");
		slotMapper.put(44, "19:30");
		slotMapper.put(45, "19:45");
		slotMapper.put(46, "20:00");

		return slotMapper;
	}

	public static List<String> originalTimeSlots() {
		List<String> timeSlots = new ArrayList<String>();
		timeSlots.add("09:00");
		timeSlots.add("09:15");
		timeSlots.add("09:30");
		timeSlots.add("09:45");
		timeSlots.add("10:00");
		timeSlots.add("10:15");
		timeSlots.add("10:30");
		timeSlots.add("10:45");
		timeSlots.add("11:00");
		timeSlots.add("11:15");
		timeSlots.add("11:30");
		timeSlots.add("11:45");
		timeSlots.add("12:00");
		timeSlots.add("12:15");
		timeSlots.add("12:30");
		timeSlots.add("12:45");
		timeSlots.add("13:00");
		timeSlots.add("13:15");
		timeSlots.add("13:30");
		timeSlots.add("13:45");
		timeSlots.add("14:00");
		timeSlots.add("14:15");
		timeSlots.add("14:30");
		timeSlots.add("14:45");
		timeSlots.add("15:00");
		timeSlots.add("15:15");
		timeSlots.add("15:30");
		timeSlots.add("15:45");
		timeSlots.add("16:00");
		timeSlots.add("16:15");
		timeSlots.add("16:30");
		timeSlots.add("16:45");
		timeSlots.add("17:00");
		timeSlots.add("17:15");
		timeSlots.add("17:30");
		timeSlots.add("17:45");
		timeSlots.add("18:00");
		timeSlots.add("18:15");
		timeSlots.add("18:30");
		timeSlots.add("18:45");
		timeSlots.add("19:00");
		timeSlots.add("19:15");
		timeSlots.add("19:30");
		timeSlots.add("19:45");
		timeSlots.add("20:00");

		return timeSlots;

	}

	public List<String> getSlotsAfterSelectedSlot(String slot) {
		return null;
		// get the key of current selected slot and add all slots in the list after that
		// key

	}

	public static Map<String, Integer> countFrequencies(List<String> list) {
		// hashmap to store the frequency of element
		Map<String, Integer> hm = new HashMap<String, Integer>();

		for (String i : list) {
			Integer j = hm.get(i);
			hm.put(i, (j == null) ? 1 : j + 1);
		}

		// displaying the occurrence of elements in the arraylist
//	        for (Map.Entry<String, Integer> val : hm.entrySet()) { 
//	            System.out.println("Element " + val.getKey() + " "
//	                               + "occurs"
//	                               + ": " + val.getValue() + " times"); 
//	        } 

		return hm;
	}

	public static String[] getHourMin(String slot) {
		String[] splitArr = slot.split(":");
		return splitArr;
	}

	private static String trimLeadingZeroes(String inputStringWithZeroes) {
		final Integer trimZeroes = Integer.parseInt(inputStringWithZeroes);
		return trimZeroes.toString();
	}

	public static LocalTime[] getTimeSlotsArray(String openTime, String closingTime) {
		String[] openArray = getHourMin(openTime);
		String[] closeArray = getHourMin(closingTime);

		LocalTime startTime = LocalTime.of(Integer.valueOf(trimLeadingZeroes(openArray[0])),
				Integer.valueOf(openArray[1]));
		LocalTime endTime = LocalTime.of(Integer.valueOf(closeArray[0]), Integer.valueOf(closeArray[1]));

		if (Integer.valueOf(trimLeadingZeroes(openArray[0])) > Integer.valueOf(closeArray[0])) {
			System.out.println("It bigger");
		}

		// long hours = ChronoUnit.HOURS.between(startTime, endTime);
		long min = ChronoUnit.MINUTES.between(startTime, endTime);
		// long a = min/Long.valueOf("15");
		// System.out.println(Math.abs(hours));
		// System.out.println(( Math.abs(hours)*60)/15);
		// final long n =(Math.abs(hours)*60)/15;
		final long n = Math.abs(min) / Long.valueOf("15");
		/* PT15M */
		/* PT-15M */
		// Duration piece =
		// Duration.between(LocalTime.of(Integer.valueOf(trimLeadingZeroes("08")),
		// Integer.valueOf("00")),
		// LocalTime.of(Integer.valueOf(trimLeadingZeroes("09")),
		// Integer.valueOf("00"))).dividedBy(Long.valueOf(n));
		Duration piece = Duration.parse("PT15M");
		LocalTime[] partitionTimes = IntStream.rangeClosed(0, Integer.valueOf(String.valueOf(n)))
				.mapToObj(i -> startTime.plus(piece.abs().multipliedBy(i))).toArray(LocalTime[]::new);

//	    List<LocalTime> partitionTimes = new ArrayList<LocalTime>();
//	    for(int i = 0 ; i<n ;i++) {
//	    	startTime = startTime.plus(piece.abs().multipliedBy(i));
//	    	partitionTimes.add(startTime);
//	    }
		// System.out.println(partitionTimes);

		System.out.println(Arrays.toString(partitionTimes));
		return partitionTimes;
	}

	public static List<String> getSlotsByTime(String openTime, String closingTime) {
		LocalTime[] localTimeArray = getTimeSlotsArray(openTime, closingTime);
		List<String> slotList = new ArrayList<String>();
		for (LocalTime localtime : localTimeArray) {
			slotList.add(localtime.toString());
		}

		return slotList;
	}

	public static String[] getOpenCloseTime(List<OpeningClosingTimeDto> timingsList, String dayOfWeek) {

		String[] timingArray = new String[2];

		for (OpeningClosingTimeDto timing : timingsList) {
			if (timing.getWeekDay().equalsIgnoreCase(dayOfWeek)) {
				timingArray[0] = timing.getOpenTime();
				timingArray[1] = timing.getCloseTime();
			}
		}

		return timingArray;

	}

	public static String getSumOfServicesTime(List<ServicesDto> services) {
		int sumOfTime = 0;
		for (ServicesDto serviceDto : services) {
			sumOfTime = sumOfTime + Integer.valueOf(serviceDto.getTimeInMin());
		}
		return LocalTime.MIN.plus(Duration.ofMinutes(sumOfTime)).toString();
	}

	public List<OpeningClosingTimeDto> getSortedTimingsList(List<OpeningClosingTime> timings) {
		List<OpeningClosingTimeDto> timingDtos = new ArrayList<OpeningClosingTimeDto>();
		OpeningClosingTimeDto timingDto;
		for (OpeningClosingTime timing : timings) {
			timingDto = new OpeningClosingTimeDto();
			timingDto.setWeekDay(timing.getWeekDay());
			timingDto.setVendorId(timing.getVendor().getvendorId());
			timingDto.setStatus(timing.getStatus());
			timingDto.setOpenTime(timing.getOpenTime());
			timingDto.setOpClId(String.valueOf(timing.getOpClId()));
			timingDto.setCloseTime(timing.getCloseTime());
			if (timing.getWeekDay().equals("Sunday")) {

				timingDto.setRank(1);
			}
			if (timing.getWeekDay().equals("Monday")) {
				timingDto.setRank(2);
			}
			if (timing.getWeekDay().equals("Tuesday")) {
				timingDto.setRank(3);
			}
			if (timing.getWeekDay().equals("Wednesday")) {
				timingDto.setRank(4);

			}
			if (timing.getWeekDay().equals("Thursday")) {
				timingDto.setRank(5);
			}
			if (timing.getWeekDay().equals("Friday")) {
				timingDto.setRank(6);
			}
			if (timing.getWeekDay().equals("Saturday")) {
				timingDto.setRank(7);
			}

			timingDtos.add(timingDto);
		}

		Collections.sort(timingDtos, new SortTimings());
		return timingDtos;
	}

	@SuppressWarnings("rawtypes")
	public List<String> getDeleteServiceIds(JSONArray services, Vendor vendor) {
		List<String> serviceIdsReqeust = new ArrayList<String>();
		List<String> serviceIdsDb = new ArrayList<String>();
		List<String> serviceIdsDelete = new ArrayList<String>();
		Iterator i = services.iterator();
		while (i.hasNext()) {
			JSONObject service = (JSONObject) i.next();
			serviceIdsReqeust.add((String) service.get("serviceId"));

		}

		List<VendorServices> vendorServices = vendorServicesRepo.findByVendor(vendor);
		for (VendorServices service : vendorServices) {
			serviceIdsDb.add(String.valueOf(service.getServiceId()));
		}

		for (String deleteService : serviceIdsDb) {
			if (!serviceIdsReqeust.contains(deleteService)) {
				serviceIdsDelete.add(deleteService);
			}
		}

		return serviceIdsDelete;
	}

	public boolean isLastDay(Date date) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		c.add(Calendar.DAY_OF_MONTH, 1);
		return c.get(Calendar.MONTH) != now.get(Calendar.MONTH);
	}

	public int getLastDay(Date date) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		c.add(Calendar.DAY_OF_MONTH, 1);
		return c.get(Calendar.MONTH);
	}

	public Date getPreviousMonthDate(Date date) {
		// final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DATE, -1);

		Date preMonthDate = cal.getTime();
		// return format.format(preMonthDate);
		return preMonthDate;
	}

	public Date getFirstLastDayOfMonth(Date date, boolean isFirst) {
		Calendar cal = Calendar.getInstance();
		Date dateRepresentation = null;
		if (isFirst) {
			cal.set(Calendar.YEAR, date.getYear());
			cal.set(Calendar.MONTH, date.getMonth());
			cal.set(Calendar.DAY_OF_MONTH, 1);
			dateRepresentation = cal.getTime();
		} else {
			cal.setTime(date);

			cal.add(Calendar.MONTH, 1);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.add(Calendar.DATE, -1);
			dateRepresentation = cal.getTime();
		}

		return dateRepresentation;
	}

	public static String hashCal(String type, String str) {
		byte[] hashseq = str.getBytes();
		StringBuffer hexString = new StringBuffer();
		try {
			MessageDigest algorithm = MessageDigest.getInstance(type);
			
			algorithm.reset();
			algorithm.update(hashseq);
			byte messageDigest[] = algorithm.digest();

			for (int i = 0; i < messageDigest.length; i++) {
				String hex = Integer.toHexString(0xFF & messageDigest[i]);
				if (hex.length() == 1)
					hexString.append("0");
				hexString.append(hex);
			}

		} catch (NoSuchAlgorithmException nsae) {
		}

		return hexString.toString();
	}

	
	
	public static String theMonth(int month){
	    String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	    return monthNames[month];
	}
	
	
	
	public int getMonthByDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH);
		return month;
	}
	
	public int getYearByDate(Date date) {
		
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			return calendar.get(Calendar.YEAR);
	}
	
	public Date getfifthDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		Date dateRepresentation = null;
		cal.set(Calendar.YEAR, date.getYear());
		cal.set(Calendar.MONTH, date.getMonth());
		cal.set(Calendar.DAY_OF_MONTH, 5);
		dateRepresentation = cal.getTime();
		return dateRepresentation;
	}
	
	public boolean isWithinRange(Date testDate, Date startDate, Date endDate) {
	//	   return !(testDate.before(startDate) || testDate.after(endDate));
		  return !(testDate.after(startDate) && testDate.before(endDate));
		}
}

//class Rank implements Comparator<OpeningClosingTimeDto> 
//{ 
//    // Used for sorting in ascending order of 
//    // roll number 
//
//	@Override
//	public int compare(OpeningClosingTimeDto o1, OpeningClosingTimeDto o2) {
//		// TODO Auto-generated method stub
//		return o1.getRank() - o2.getRank();
//	} 
//}
