package com.fcc.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class StatusDetailsDto {

	private String orderId;
	private CommonDto userinfo;
	private List<ServicesDto> services;
	private String totalPrice;
	private String totalTime;
	private String orderDate;
	private String appointmentDate;
	private List<AppointmentDto> slots;
	public List<AppointmentDto> getSlots() {
		return slots;
	}
	public void setSlots(List<AppointmentDto> slots) {
		this.slots = slots;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public CommonDto getUserinfo() {
		return userinfo;
	}
	public void setUserinfo(CommonDto userinfo) {
		this.userinfo = userinfo;
	}
	public List<ServicesDto> getServices() {
		return services;
	}
	public void setServices(List<ServicesDto> services) {
		this.services = services;
	}
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getAppointmentDate() {
		return appointmentDate;
	}
	public void setAppointmentDate(String appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
	
	
	
}
