
package com.fcc.model;

import java.util.List;

import com.fcc.domain.Vendor;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@JsonInclude(Include.NON_NULL)
public class CommonDto extends BaseDto {

	private Integer userId;
	private String userName;
	private String name;
	private String gender;
	private List<UserMediaDto> userImages;
	private List<ServicesDto> services;
	private List<OpeningClosingTimeDto> timings;
	private String mobile;
	private String latitude;
	private String longitude;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	private String oldPassword;
	private String type;
	private Boolean isOtpVerified;
	private String registerStep;
	private String address;
	private String serviceName;
	private String keyword;
	private String currentDate;
	private String tempEmail;
	private String accountStatus;
	private String shopType;
	private Integer shopTypeId;
	private String Image;
	private String seats;
	private String requestType;
	private boolean isBookingAllowed = true;
	private String transactionIds;
	private String totalAmount;
	private String onlineTransactionId;
	private String paymentStatus;
	private String searchTerm;
	private boolean isPaymentSuccess;
	
	
	public String getRequestType() {
		return requestType;
	}


	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}


	public String getSeats() {
		return seats;
	}


	public void setSeats(String seats) {
		this.seats = seats;
	}


	public String getAccountStatus() {
		return accountStatus;
	}


	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}


	public String getTempEmail() {
		return tempEmail;
	}


	public void setTempEmail(String tempEmail) {
		this.tempEmail = tempEmail;
	}


	private List<String> images;
	public List<String> getImages() {
		return images;
	}


	public void setImages(List<String> images) {
		this.images = images;
	}


	public String getCurrentDate() {
		return currentDate;
	}


	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}
	public Boolean getIsPaymentSuccess() {
		return isPaymentSuccess;
	}

	public void setIsPaymentSuccess(Boolean isPaymentSuccess) {
		this.isPaymentSuccess = isPaymentSuccess;
	}

	

	
	private UserMediaDto userImage;
	


	public UserMediaDto getUserImage() {
		return userImage;
	}


	public void setUserImage(UserMediaDto userImage) {
		this.userImage = userImage;
	}


	public String getKeyword() {
		return keyword;
	}


	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}


	public String getServiceName() {
		return serviceName;
	}


	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public Boolean getIsOtpVerified() {
		return isOtpVerified;
	}


	public void setIsOtpVerified(Boolean isOtpVerified) {
		this.isOtpVerified = isOtpVerified;
	}


	public String getRegisterStep() {
		return registerStep;
	}


	public void setRegisterStep(String registerStep) {
		this.registerStep = registerStep;
	}


	public String getDeviceType() {
		return deviceType;
	}


	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}


	public String getDeviceToken() {
		return deviceToken;
	}


	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public CommonDto(Integer userId, String userName, String name, String gender, List<UserMediaDto> userImages,
			List<ServicesDto> services, List<OpeningClosingTimeDto> timings, String mobile, String latitude,
			String longitude, String password, String oldPassword, String type, Boolean isOtpVerified,
			Boolean isPaymentSuccess, String registerStep, String address, String serviceName, String keyword,
			String currentDate, String tempEmail, String accountStatus, String shopType, Integer shopTypeId,
			String image, String seats, String requestType, List<String> images, UserMediaDto userImage,
			String oneTimePassword, String deviceType, String deviceToken, List<Vendor> vendors) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.name = name;
		this.gender = gender;
		this.userImages = userImages;
		this.services = services;
		this.timings = timings;
		this.mobile = mobile;
		this.latitude = latitude;
		this.longitude = longitude;
		this.password = password;
		this.oldPassword = oldPassword;
		this.type = type;
		this.isOtpVerified = isOtpVerified;
		this.isPaymentSuccess = isPaymentSuccess;
		this.registerStep = registerStep;
		this.address = address;
		this.serviceName = serviceName;
		this.keyword = keyword;
		this.currentDate = currentDate;
		this.tempEmail = tempEmail;
		this.accountStatus = accountStatus;
		this.shopType = shopType;
		this.shopTypeId = shopTypeId;
		Image = image;
		this.seats = seats;
		this.requestType = requestType;
		this.images = images;
		this.userImage = userImage;
		this.oneTimePassword = oneTimePassword;
		this.deviceType = deviceType;
		this.deviceToken = deviceToken;
		this.vendors = vendors;
	}



	private String oneTimePassword;
	private String deviceType;
	private String deviceToken;
	
	private List<Vendor> vendors;
	
	

	public CommonDto(String userName,String mobile, String latitude, String longitude, String password, String type) {
		super();
		this.userName = userName;
		this.mobile = mobile;
		this.latitude = latitude;
		this.longitude = longitude;
		this.password = password;
		this.type = type;
	}
	
	
	public CommonDto(Integer userId, String userName, String mobile, String latitude, String longitude, String password,
			String type, String oneTimePassword,
			List<Vendor> vendors) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.mobile = mobile;
		this.latitude = latitude;
		this.longitude = longitude;
		this.password = password;
		this.type = type;
		this.oneTimePassword = oneTimePassword;
		this.vendors = vendors;
	}


	public CommonDto(String userName, String mobile, String latitude, String longitude, String password, String type, List<Vendor> vendors) {
		super();
		this.userName = userName;
		this.mobile = mobile;
		this.latitude = latitude;
		this.longitude = longitude;
		this.password = password;
		this.type = type;
		this.vendors = vendors;
	}
	
	

	
	public CommonDto(Integer userId, String userName, String mobile, String latitude, String longitude, String password,
			String type, Boolean isOtpVerified,
			String registerStep, String oneTimePassword, String deviceType, String deviceToken, List<Vendor> vendors) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.mobile = mobile;
		this.latitude = latitude;
		this.longitude = longitude;
		this.password = password;
		this.type = type;
		this.isOtpVerified = isOtpVerified;
		this.registerStep = registerStep;
		this.oneTimePassword = oneTimePassword;
		this.deviceType = deviceType;
		this.deviceToken = deviceToken;
		this.vendors = vendors;
	}


	public Integer getUserId() {
		return userId;
	}


	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	
	public List<Vendor> getVendors() {
		return vendors;
	}
	public void setVendors(List<Vendor> vendors) {
		this.vendors = vendors;
	}
	
	
	public CommonDto() {
		super();
		
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	public List<UserMediaDto> getUserImages() {
		return userImages;
	}

	public List<ServicesDto> getServices() {
		return services;
	}


	public void setServices(List<ServicesDto> services) {
		this.services = services;
	}

	public void setUserImages(List<UserMediaDto> userImages) {
		this.userImages = userImages;
	}
	
	
	public List<OpeningClosingTimeDto> getTimings() {
		return timings;
	}


	public void setTimings(List<OpeningClosingTimeDto> timings) {
		this.timings = timings;
	}


	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getOldPassword() {
		return oldPassword;
	}


	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOneTimePassword() {
		return oneTimePassword;
	}


	public void setOneTimePassword(String oneTimePassword) {
		this.oneTimePassword = oneTimePassword;
	}

    
	 
	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getShopType() {
		return shopType;
	}


	public void setShopType(String shopType) {
		this.shopType = shopType;
	}


	public Integer getShopTypeId() {
		return shopTypeId;
	}


	public void setShopTypeId(Integer shopTypeId) {
		this.shopTypeId = shopTypeId;
	}


	public String getImage() {
		return Image;
	}


	public void setImage(String image) {
		Image = image;
	}

	

	public String getTransactionIds() {
		return transactionIds;
	}


	public void setTransactionIds(String transactionIds) {
		this.transactionIds = transactionIds;
	}


	public String getTotalAmount() {
		return totalAmount;
	}


	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}



	public String getOnlineTransactionId() {
		return onlineTransactionId;
	}


	public void setOnlineTransactionId(String onlineTransactionId) {
		this.onlineTransactionId = onlineTransactionId;
	}


	public String getPaymentStatus() {
		return paymentStatus;
	}


	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}


	@Override
	public String toString() {
		return "CommonDto [userId=" + userId + ", userName=" + userName + ", name=" + name + ", gender=" + gender
				+ ", userImages=" + userImages + ", services=" + services + ", timings=" + timings + ", mobile="
				+ mobile + ", latitude=" + latitude + ", longitude=" + longitude + ", password=" + password
				+ ", oldPassword=" + oldPassword + ", type=" + type + ", isOtpVerified=" + isOtpVerified
				+ ", isPaymentSuccess=" + isPaymentSuccess + ", registerStep=" + registerStep + ", address=" + address
				+ ", serviceName=" + serviceName + ", keyword=" + keyword + ", currentDate=" + currentDate
				+ ", tempEmail=" + tempEmail + ", accountStatus=" + accountStatus + ", shopType=" + shopType
				+ ", shopTypeId=" + shopTypeId + ", Image=" + Image + ", seats=" + seats + ", requestType="
				+ requestType + ", images=" + images + ", userImage=" + userImage + ", oneTimePassword="
				+ oneTimePassword + ", deviceType=" + deviceType + ", deviceToken=" + deviceToken + ", vendors="
				+ vendors + "]";
	}




	public boolean isBookingAllowed() {
		return isBookingAllowed;
	}


	public void setBookingAllowed(boolean isBookingAllowed) {
		this.isBookingAllowed = isBookingAllowed;
	}


	public String getSearchTerm() {
		return searchTerm;
	}


	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}

	
	
	
}
