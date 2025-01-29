package com.fcc.model;

public class BookingDto {

	private String vendorId;
	private String vendorName;
	private String customerId;
	private String customerName;
	private String status;
	private String orderDate;
	private String appointmentDate;
	private String startDate;
	private String endDate;
	private String amount;
	private String paymentType;
	private String transactionId;
	private String totalServicesBooked;
	
	
	
	public BookingDto() {
		super();
		
	}
	
	public BookingDto(String vendorId, String vendorName, String customerId, String customerName, String status, String orderDate, String appointmentDate,
			String amount, String paymentType, String transactionId,
			String totalServicesBooked) {
		super();
		this.vendorId = vendorId;
		this.vendorName = vendorName;
		this.customerId = customerId;
		this.customerName = customerName;
		this.status = status;
		this.orderDate = orderDate;
		this.appointmentDate = appointmentDate;
		this.amount = amount;
		this.paymentType = paymentType;
		this.transactionId = transactionId;
		this.totalServicesBooked = totalServicesBooked;
	}
	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getTotalServicesBooked() {
		return totalServicesBooked;
	}
	public void setTotalServicesBooked(String totalServicesBooked) {
		this.totalServicesBooked = totalServicesBooked;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	
}
