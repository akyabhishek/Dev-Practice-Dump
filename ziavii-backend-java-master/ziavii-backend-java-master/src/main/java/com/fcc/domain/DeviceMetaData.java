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
@Table(name="device_meta_data")
public class DeviceMetaData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "login_record_id", nullable = false)
	private Integer loginRecordId;
	@Column(name = "user_id", nullable = false)
    private Integer userId;
	@Column(name = "user_type", nullable = false)
    private String userType;
	@Column(name = "device_type", nullable = false)
	private String deviceType;
	@Column(name = "device_token", nullable = false)
	private String deviceToken;
	@Column(name = "login_date_time", nullable = false)
	private Date loginDateTime;
	@Column(name = "created_date", nullable = false)
	private Date createdDate;
	public Integer getLoginRecordId() {
		return loginRecordId;
	}
	public void setLoginRecordId(Integer loginRecordId) {
		this.loginRecordId = loginRecordId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
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
	public Date getLoginDateTime() {
		return loginDateTime;
	}
	public void setLoginDateTime(Date loginDateTime) {
		this.loginDateTime = loginDateTime;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	
}
