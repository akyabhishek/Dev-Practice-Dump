package com.fcc.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="customer")
public class Customer {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_id", nullable = false)
	private Integer customerId;
	@Column(name = "name", nullable = false)
	private String name;
//	@Column(name = "gender", nullable = false)
//	private String gender;
	@Column(name = "mobile", nullable = false)
	private String mobile;
	@Column(name = "email", nullable = false)
	private String email;
	@Column(name = "latiude", nullable = true)
	private String latitude;
	@Column(name = "longitude", nullable = true)
	private String longitude;
	@Column(name = "password", nullable = false)
	private String password;
	@Column(name = "type", nullable = false)
	private String type;
	@Column(name = "one_time_pass", nullable = true)
	private String oneTimePass;
	@Column(name = "otp_created_time", nullable = true)
	private Date OtpCreatedTime;
	@Column(name = "otp_verified", nullable = false)
	private boolean otpVerified = false;
	@Column(name = "is_update", nullable = true)
	private boolean isUpdate = false;
	@Column(name = "temp_mobile", nullable = true)
	private String tempMobile;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "gender_id", nullable = false)
	private MasterGender gender;
	@Column(name = "is_rated", nullable = false)
	private Integer isRated = 0;
	
//	@ManyToMany(fetch = FetchType.LAZY,
//            cascade = {
//                CascadeType.PERSIST,
//                CascadeType.MERGE
//            },
//            mappedBy = "customers")
//    private Set<Vendor> vendors = new HashSet<>();
	

	public Integer getIsRated() {
		return isRated;
	}


	public void setIsRated(Integer isRated) {
		this.isRated = isRated;
	}


	public MasterGender getGender() {
		return gender;
	}


	public void setGender(MasterGender gender) {
		this.gender = gender;
	}


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





	@Column(name = "created_date", nullable = true)
	private Date createdDate;
	@Column(name = "last_updated_date", nullable = true)
	private Date lastUpdatedDate;
	
	public Customer(Integer customerId, String name,  String mobile, String email,
			String latitude, String longitude,String password, String type, Date createdDate, Date lastUpdatedDate) {
		super();
		this.customerId = customerId;
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
	
	
	public Customer(Integer customerId, String name, String mobile, String email, String latitude,
			String longitude, String password, String type, String oneTimePass, Date otpCreatedTime, Date createdDate,
			Date lastUpdatedDate) {
		super();
		this.customerId = customerId;
		this.name = name;
		this.mobile = mobile;
		this.email = email;
		this.latitude = latitude;
		this.longitude = longitude;
		this.password = password;
		this.type = type;
		this.oneTimePass = oneTimePass;
		OtpCreatedTime = otpCreatedTime;
		this.createdDate = createdDate;
		this.lastUpdatedDate = lastUpdatedDate;
	}


	public Customer() {
		super();
		
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
	
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
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
	public boolean isOtpVerified() {
		return otpVerified;
	}


	public void setOtpVerified(boolean otpVerified) {
		this.otpVerified = otpVerified;
	}


	public Customer(Integer customerId, String name, String mobile, String email, String latitude,
			String longitude, String password, String type, String oneTimePass, Date otpCreatedTime,
			boolean otpVerified, Date createdDate, Date lastUpdatedDate) {
		super();
		this.customerId = customerId;
		this.name = name;
		this.mobile = mobile;
		this.email = email;
		this.latitude = latitude;
		this.longitude = longitude;
		this.password = password;
		this.type = type;
		this.oneTimePass = oneTimePass;
		OtpCreatedTime = otpCreatedTime;
		this.otpVerified = otpVerified;
		this.createdDate = createdDate;
		this.lastUpdatedDate = lastUpdatedDate;
	}


	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", name=" + name + ", mobile=" + mobile + ", email=" + email
				+ ", latitude=" + latitude + ", longitude=" + longitude + ", password=" + password + ", type=" + type
				+ ", oneTimePass=" + oneTimePass + ", OtpCreatedTime=" + OtpCreatedTime + ", otpVerified=" + otpVerified
				+ ", createdDate=" + createdDate + ", lastUpdatedDate=" + lastUpdatedDate + "]";
	}


	
	


	


	


	
	

}
