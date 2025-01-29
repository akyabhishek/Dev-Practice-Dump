package com.fcc.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class OpeningClosingTimeDto {

	private String opClId;
	private Integer vendorId;
	private String  openTime;
	private String  closeTime;
	private String  weekDay;
	private String  status;
	private Integer rank;
	
	
	public OpeningClosingTimeDto(String opClId, String openTime, String closeTime, String weekDay, String status) {
		super();
		this.opClId = opClId;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.weekDay = weekDay;
		this.status = status;
	}
	

	public OpeningClosingTimeDto(String opClId, Integer vendorId, String openTime, String closeTime, String weekDay,
			String status) {
		super();
		this.opClId = opClId;
		this.vendorId = vendorId;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.weekDay = weekDay;
		this.status = status;
	}





	public OpeningClosingTimeDto() {
		super();
		// TODO Auto-generated constructor stub
	}



	public String getOpClId() {
		return opClId;
	}
	public void setOpClId(String opClId) {
		this.opClId = opClId;
	}
	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
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

    
	
	public Integer getRank() {
		return rank;
	}


	public void setRank(Integer rank) {
		this.rank = rank;
	}


	@Override
	public String toString() {
		return "OpeningClosingTimeDto [opClId=" + opClId + ", vendorId=" + vendorId + ", openTime=" + openTime
				+ ", closeTime=" + closeTime + ", weekDay=" + weekDay + ", status=" + status + "]";
	}


	
	
	
}
