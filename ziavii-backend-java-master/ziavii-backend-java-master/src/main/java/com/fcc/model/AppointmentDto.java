package com.fcc.model;

import java.util.Date;

import com.fcc.domain.Customer;
import com.fcc.domain.Vendor;
import com.fcc.domain.VendorServices;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class AppointmentDto {

	
	private Integer appointId;
	private Vendor vendor;
	private Customer customer;
	private VendorServices service;
	private String slot;
	private String slotStatus;
	private Date appointmentDate;
	
	public Integer getAppointId() {
		return appointId;
	}
	public void setAppointId(Integer appointId) {
		this.appointId = appointId;
	}
	public Vendor getVendor() {
		return vendor;
	}
	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public VendorServices getService() {
		return service;
	}
	public void setService(VendorServices service) {
		this.service = service;
	}
	public String getSlot() {
		return slot;
	}
	public void setSlot(String slot) {
		this.slot = slot;
	}
	public Date getAppointmentDate() {
		return appointmentDate;
	}
	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
	public String getSlotStatus() {
		return slotStatus;
	}
	public void setSlotStatus(String slotStatus) {
		this.slotStatus = slotStatus;
	}
	
	
}
