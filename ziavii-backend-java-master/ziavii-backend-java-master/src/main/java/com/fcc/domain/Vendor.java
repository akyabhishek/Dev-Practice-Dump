package com.fcc.domain;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name="vendor")
public class Vendor {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vendor_id", nullable = false)
	private Integer vendorId;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "mobile", nullable = false)
	private String mobile;
	
	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "latiude", nullable = false)
	private String latitude;
	
	@Column(name = "longitude", nullable = false)
	private String longitude;
	
	@Column(name = "password", nullable = false)
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	
	@Column(name = "address", nullable = false)
	private String address;
	
	@Column(name = "is_update", nullable = true)
	private boolean isUpdate = false;
	
	@Column(name = "temp_mobile", nullable = true)
	private String tempMobile;
	
	@Column(name = "is_booking_allowed", nullable = true)
	private int isBookingAllowed = 1;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "shop_type_id", nullable = false)
	private ShopType shopType;
	
	public boolean isUpdate() {
		return isUpdate;
	}
	
	public void setUpdate(boolean isUpdate) {
		this.isUpdate = isUpdate;
	}

	

	
	public String getTempMobile() {
		return tempMobile;
	}

	public void setTempMobile(String tempMobile) {
		this.tempMobile = tempMobile;
	}




	@OneToMany(targetEntity = VendorServices.class, cascade = CascadeType.ALL)
	@JoinColumn(name="vendor_id")
	private List<VendorServices> services;
	
	
//	@ManyToMany(fetch = FetchType.LAZY,
//	            cascade = {
//	                CascadeType.PERSIST,
//	                CascadeType.MERGE
//	            })
//    @JoinTable(name = "vendors_customers",
//	            joinColumns = { @JoinColumn(name = "vendor_id") },
//	            inverseJoinColumns = { @JoinColumn(name = "customer_id") })
//	private Set<Customer> customers = new HashSet<>();
	 
	 
//
//	public Set<Customer> getCustomers() {
//		return customers;
//	}
//
//	public void setCustomers(Set<Customer> customers) {
//		this.customers = customers;
//	}

	public List<VendorServices> getServices() {
		return services;
	}

	public void setServices(List<VendorServices> services) {
		this.services = services;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "type", nullable = false)
	private String type;
	@Column(name = "seats", nullable = false)
	private String seats;
	@Column(name = "one_time_pass", nullable = true)
	private String oneTimePass;
	@Column(name = "otp_created_time", nullable = true)
	private Date OtpCreatedTime;
	@Column(name = "otp_verified", nullable = false)
	private boolean otpVerified = false;
	@Column(name = "is_payment_success", nullable = false)
	private boolean isPaymentSuccess = false;
	
	
	public boolean isPaymentSuccess() {
		return isPaymentSuccess;
	}

	public void setPaymentSuccess(boolean isPaymentSuccess) {
		this.isPaymentSuccess = isPaymentSuccess;
	}

	
	
	@Column(name = "register_step", nullable = true)
	private String registerStep;
	public String getRegisterStep() {
		return registerStep;
	}

	public void setRegisterStep(String registerStep) {
		this.registerStep = registerStep;
	}

	@Column(name = "created_date", nullable = true)
	private Date createdDate;
	
	public Vendor(Integer vendorId, String name, String mobile, String email, String latitude, String longitude,
			String password, String address, boolean isUpdate, boolean isPaymentSuccess, String tempMobile,
			ShopType shopType, List<VendorServices> services, String type, String seats, String oneTimePass,
			Date otpCreatedTime, boolean otpVerified, String registerStep, Date createdDate, Date lastUpdatedDate) {
		super();
		this.vendorId = vendorId;
		this.name = name;
		this.mobile = mobile;
		this.email = email;
		this.latitude = latitude;
		this.longitude = longitude;
		this.password = password;
		this.address = address;
		this.isUpdate = isUpdate;
		this.isPaymentSuccess = isPaymentSuccess;
		this.tempMobile = tempMobile;
		this.shopType = shopType;
		this.services = services;
		this.type = type;
		this.seats = seats;
		this.oneTimePass = oneTimePass;
		OtpCreatedTime = otpCreatedTime;
		this.otpVerified = otpVerified;
		this.registerStep = registerStep;
		this.createdDate = createdDate;
		this.lastUpdatedDate = lastUpdatedDate;
	}


	public Vendor(Integer vendorId, String name, String mobile, String email, String latitude, String longitude, String password, String type,
			String seats, String oneTimePass, Date otpCreatedTime, boolean otpVerified, Date createdDate,
			Date lastUpdatedDate) {
		super();
		this.vendorId = vendorId;
		this.name = name;
		this.mobile = mobile;
		this.email = email;
		this.latitude = latitude;
		this.longitude = longitude;
		this.password = password;
		this.type = type;
		this.seats = seats;
		this.oneTimePass = oneTimePass;
		OtpCreatedTime = otpCreatedTime;
		this.otpVerified = otpVerified;
		this.createdDate = createdDate;
		this.lastUpdatedDate = lastUpdatedDate;
	}

	

	public boolean isOtpVerified() {
		return otpVerified;
	}

	public void setOtpVerified(boolean otpVerified) {
		this.otpVerified = otpVerified;
	}

	@Column(name = "last_updated_date", nullable = true)
	private Date lastUpdatedDate;
	
	public Vendor() {
		super();
		
	}

	public Vendor(Integer vendorId, String name, String mobile, String email,  String latitude, String password, String longitude, String type, Date createdDate,
			Date lastUpdatedDate) {
		super();
		this.vendorId = vendorId;
		this.name = name;
		this.mobile = mobile;
		this.email = email;
		this.latitude = latitude;
		this.longitude = longitude;
		this.password = password;
		this.type = type;
		this.createdDate = createdDate;
		this.lastUpdatedDate = lastUpdatedDate;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public Integer getvendorId() {
		return vendorId;
	}
	public void setvendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getSeats() {
		return seats;
	}

	public void setSeats(String seats) {
		this.seats = seats;
	}
	public String getOneTimePass() {
		return oneTimePass;
	}

	public void setOneTimePass(String oneTimePass) {
		this.oneTimePass = oneTimePass;
	}

	public Date getOtpCreatedTime() {
		return OtpCreatedTime;
	}

	public void setOtpCreatedTime(Date otpCreatedTime) {
		OtpCreatedTime = otpCreatedTime;
	}
	

	public ShopType getShopType() {
		return shopType;
	}

	public void setShopType(ShopType shopType) {
		this.shopType = shopType;
	}

	
	public int getIsBookingAllowed() {
		return isBookingAllowed;
	}

	public void setIsBookingAllowed(int isBookingAllowed) {
		this.isBookingAllowed = isBookingAllowed;
	}

	public Vendor(Integer vendorId, String name, String mobile, String email, String latitude, String longitude, String password, String type,
			String seats, String oneTimePass, Date otpCreatedTime, boolean otpVerified, String registerStep,
			Date createdDate, Date lastUpdatedDate) {
		super();
		this.vendorId = vendorId;
		this.name = name;
		this.mobile = mobile;
		this.email = email;
		this.latitude = latitude;
		this.longitude = longitude;
		this.password = password;
		this.type = type;
		this.seats = seats;
		this.oneTimePass = oneTimePass;
		OtpCreatedTime = otpCreatedTime;
		this.otpVerified = otpVerified;
		this.registerStep = registerStep;
		this.createdDate = createdDate;
		this.lastUpdatedDate = lastUpdatedDate;
	}


	@Override
	public String toString() {
		return "Vendor [vendorId=" + vendorId + ", name=" + name + ", mobile=" + mobile + ", email=" + email
				+ ", latitude=" + latitude + ", longitude=" + longitude + ", password=" + password + ", address="
				+ address + ", isUpdate=" + isUpdate + ", isPaymentSuccess=" + isPaymentSuccess + ", tempMobile="
				+ tempMobile + ", shopType=" + shopType + ", services=" + services + ", type=" + type + ", seats="
				+ seats + ", oneTimePass=" + oneTimePass + ", OtpCreatedTime=" + OtpCreatedTime + ", otpVerified="
				+ otpVerified + ", registerStep=" + registerStep + ", createdDate=" + createdDate + ", lastUpdatedDate="
				+ lastUpdatedDate + "]";
	}

	



	
	
	
	

	

	
	
}
