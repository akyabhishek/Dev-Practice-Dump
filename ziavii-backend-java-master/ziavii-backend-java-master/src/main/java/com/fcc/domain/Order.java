package com.fcc.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderId;
	private Date orderDate;
	private Date appointmentDate;
	private Date createdDate;
	private Date lastUpdatedDate;
	private String bookingDuration;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "status_id", nullable = false)
	private MasterBookingStatus status;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "payment_mode_id", nullable = false)
	private PaymentMode paymentMode;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "vendor_id", nullable = false)
	private Vendor vendor;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;
	private Double totalPrice;
	private Integer totalServicesBooked;
	private String transactionId;
	private String fccPayId;
	
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public Date getAppointmentDate() {
		return appointmentDate;
	}
	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
	
	
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
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
	public String getBookingDuration() {
		return bookingDuration;
	}
	public void setBookingDuration(String bookingDuration) {
		this.bookingDuration = bookingDuration;
	}
	public MasterBookingStatus getStatus() {
		return status;
	}
	public void setStatus(MasterBookingStatus status) {
		this.status = status;
	}
	public PaymentMode getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
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
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Integer getTotalServicesBooked() {
		return totalServicesBooked;
	}
	public void setTotalServicesBooked(Integer totalServicesBooked) {
		this.totalServicesBooked = totalServicesBooked;
	}
	public String getFccPayId() {
		return fccPayId;
	}
	public void setFccPayId(String fccPayId) {
		this.fccPayId = fccPayId;
	}
	
	
}
