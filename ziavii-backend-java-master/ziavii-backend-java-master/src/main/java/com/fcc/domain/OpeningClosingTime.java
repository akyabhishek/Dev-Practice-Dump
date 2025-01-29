package com.fcc.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="opening_closing_time")
public class OpeningClosingTime {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer opClId;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "vendor_id", nullable = false)
	private Vendor vendor;
	private String  openTime;
	private String  closeTime;
	private String  weekDay;
	private String  status;
	private Date createdAt;
	private Date lastUpdatedAt;
	
	public OpeningClosingTime(Integer opClId, String openTime, String closeTime, String weekDay, String status,
			Date createdAt, Date lastUpdatedAt) {
		super();
		this.opClId = opClId;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.weekDay = weekDay;
		this.status = status;
		this.createdAt = createdAt;
		this.lastUpdatedAt = lastUpdatedAt;
	}
	
	
	
	
	public OpeningClosingTime(Integer opClId, Vendor vendor, String openTime, String closeTime, String weekDay,
			String status, Date createdAt, Date lastUpdatedAt) {
		super();
		this.opClId = opClId;
		this.vendor = vendor;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.weekDay = weekDay;
		this.status = status;
		this.createdAt = createdAt;
		this.lastUpdatedAt = lastUpdatedAt;
	}




	public OpeningClosingTime() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Integer getOpClId() {
		return opClId;
	}
	public void setOpClId(Integer opClId) {
		this.opClId = opClId;
	}
	
	public Vendor getVendor() {
		return vendor;
	}


	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public String getOpenTime() {
		return openTime;
	}
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
	public String getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}
	public String getWeekDay() {
		return weekDay;
	}
	public void setWeekDay(String weekDay) {
		this.weekDay = weekDay;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getLastUpdatedAt() {
		return lastUpdatedAt;
	}
	public void setLastUpdatedAt(Date lastUpdatedAt) {
		this.lastUpdatedAt = lastUpdatedAt;
	}




	@Override
	public String toString() {
		return "OpeningClosingTime [opClId=" + opClId + ", vendor=" + vendor + ", openTime=" + openTime + ", closeTime="
				+ closeTime + ", weekDay=" + weekDay + ", status=" + status + ", createdAt=" + createdAt
				+ ", lastUpdatedAt=" + lastUpdatedAt + "]";
	}


	
	
	
}
