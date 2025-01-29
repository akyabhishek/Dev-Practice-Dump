package com.fcc.model;

import java.util.Arrays;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
//@JsonIgnoreProperties(value={ "customerId"}, allowGetters= true, allowSetters = false)
@JsonInclude(Include.NON_NULL)
public class CustomerDto extends BaseDto{

	private Integer customerId;
	private String name;
	private Integer genderId;
	private String mobile;
	private String email;
	private String latitude;
	private String password;
	private String longitude;
	private String type;
	private String updateFlag;
	
	public CustomerDto(Integer customerId, String name, String mobile, String email, 
			String latitude, String longitude, String password, String type) {
		super();
		this.customerId = customerId;
		this.name = name;
		this.mobile = mobile;
		this.email = email;
		this.latitude = latitude;
		this.password = password;
		this.longitude = longitude;
		this.type = type;
	}
	public CustomerDto(String name, String mobile, String email, 
			String latitude, String longitude, String password, String type) {
		super();
		this.name = name;
		this.mobile = mobile;
		this.email = email;
		this.latitude = latitude;
		this.password = password;
		this.longitude = longitude;
		this.type = type;
	}
	public CustomerDto(String name, String mobile, String email,
			 String password, String type) {
		super();
		this.name = name;
		this.mobile = mobile;
		this.email = email;
		this.password = password;
		this.type = type;
	}
	
	public CustomerDto() {
		super();
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
	
	
	public Integer getGenderId() {
		return genderId;
	}
	public void setGenderId(Integer genderId) {
		this.genderId = genderId;
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
	
	
	public String getUpdateFlag() {
		return updateFlag;
	}
	public void setUpdateFlag(String updateFlag) {
		this.updateFlag = updateFlag;
	}
	@Override
	public String toString() {
		return "CustomerDto [customerId=" + customerId + ", name=" + name + ", mobile=" + mobile + ", email=" + email
				+ ", latitude=" + latitude + ", password=" + password + ", longitude=" + longitude + ", type=" + type
				+ "]";
	}

	
	

	
	
}
