package com.fcc.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ServicesDto extends BaseDto {

	private String serviceId;
	private Integer vendorId;
	private String serviceName;
	private String servicePrice;
	private String timeInMin;
	private Boolean status;
	
	public ServicesDto(String serviceId, Integer vendorId, String serviceName, String servicePrice, String timeInMin,
			Boolean status) {
		super();
		this.serviceId = serviceId;
		this.vendorId = vendorId;
		this.serviceName = serviceName;
		this.servicePrice = servicePrice;
		this.timeInMin = timeInMin;
		this.status = status;
	}
	public String getServiceId() {
		return serviceId;
	}
	public ServicesDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ServicesDto(Boolean success, String errorCode, String errorDesc) {
		super(success, errorCode, errorDesc);
		// TODO Auto-generated constructor stub
	}
	public ServicesDto(Integer createdById, String createdBy, Integer lastUpdatedById, String lastUpdatedBy,
			Date createdOn, Date lastUpdatedOn) {
		super(createdById, createdBy, lastUpdatedById, lastUpdatedBy, createdOn, lastUpdatedOn);
		// TODO Auto-generated constructor stub
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public String getServicePrice() {
		return servicePrice;
	}
	public void setServicePrice(String servicePrice) {
		this.servicePrice = servicePrice;
	}
	public String getTimeInMin() {
		return timeInMin;
	}
	public void setTimeInMin(String timeInMin) {
		this.timeInMin = timeInMin;
	}
	public Boolean isStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "ServicesDto [serviceId=" + serviceId + ", vendorId=" + vendorId + ", serviceName=" + serviceName
				+ ", servicePrice=" + servicePrice + ", timeInMin=" + timeInMin + ", status=" + status + "]";
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	
	
}
