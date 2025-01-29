package com.fcc.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="login_record")
public class LoginRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "login_record_id", nullable = false)
	private Integer loginRecordId;
	@Column(name = "email", nullable = true)
	private String email;
	@Column(name = "mobile", nullable = true)
	private String mobile;
	@Column(name = "password", nullable = false)
	private String password;
	@Column(name = "device_type", nullable = false)
	private String deviceType;
	@Column(name = "device_token", nullable = false)
	private String deviceToken;
	@Column(name = "login_date_time", nullable = false)
	private Date loginDateTime;
	
	public Date getLoginDateTime() {
		return loginDateTime;
	}
	public void setLoginDateTime(Date loginDateTime) {
		this.loginDateTime = loginDateTime;
	}
	public Integer getLoginRecordId() {
		return loginRecordId;
	}
	public void setLoginRecordId(Integer loginRecordId) {
		this.loginRecordId = loginRecordId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	
}
