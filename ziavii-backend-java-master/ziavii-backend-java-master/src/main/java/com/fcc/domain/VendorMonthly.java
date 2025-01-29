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
@Table(name="vendor_monthly")
public class VendorMonthly {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "month", nullable = false)
	private String month;
	@Column(name = "date_of_month", nullable = false)
	private Date dateOfMonth;
	@Column(name = "fin_year", nullable = false)
	private String finYaar;
	@Column(name = "pay_month", nullable = true)
	private Date payMonth;
	@Column(name = "pay_release_date", nullable = true)
	private Date payReleaseDate;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "vendor_id", nullable = false)
	private Vendor vendor;
	@Column(name = "total_amount_vendor", nullable = false)
	private String totalAmountVendor;
	@Column(name = "charge_factor", nullable = false)
	private String chargeFactor;
	@Column(name = "transaction_Amnt", nullable = true)
	private String transactionAmnt;
	@Column(name = "total_confirmed_orders", nullable = false)
	private String totalConfirmedOrders;
//	@Column(name = "total_offline", nullable = false)
//    private String totalOffline;
//	@Column(name = "total_Online", nullable = false)
//	private String totalOnline;
	@Column(name = "created_date", nullable = false)
	private Date createdDate;
	@Column(name = "last_updated_date", nullable = true)
	private Date lastUpdatedDate;
	@Column(name = "is_paid", nullable = false)
	private Integer isPaid;
	@Column(name = "transaction_id", nullable = false)
	private String transactionId;
	@Column(name = "online_transaction_id", nullable = false)
	private String onlineTransactionId;
	@Column(name = "is_pay_blocked", nullable = false)
	private Integer isPayBlocked = 1;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	
	
	public Date getDateOfMonth() {
		return dateOfMonth;
	}
	public void setDateOfMonth(Date dateOfMonth) {
		this.dateOfMonth = dateOfMonth;
	}
	public Date getPayMonth() {
		return payMonth;
	}
	public void setPayMonth(Date payMonth) {
		this.payMonth = payMonth;
	}
	public Date getPayReleaseDate() {
		return payReleaseDate;
	}
	public void setPayReleaseDate(Date payReleaseDate) {
		this.payReleaseDate = payReleaseDate;
	}
	public Vendor getVendor() {
		return vendor;
	}
	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
	public String getChargeFactor() {
		return chargeFactor;
	}
	public void setChargeFactor(String chargeFactor) {
		this.chargeFactor = chargeFactor;
	}
	public String getTransactionAmnt() {
		return transactionAmnt;
	}
	public void setTransactionAmnt(String transactionAmnt) {
		this.transactionAmnt = transactionAmnt;
	}
	public String getTotalConfirmedOrders() {
		return totalConfirmedOrders;
	}
	public void setTotalConfirmedOrders(String totalConfirmedOrders) {
		this.totalConfirmedOrders = totalConfirmedOrders;
	}
//	public String getTotalOffline() {
//		return totalOffline;
//	}
//	public void setTotalOffline(String totalOffline) {
//		this.totalOffline = totalOffline;
//	}
//	public String getTotalOnline() {
//		return totalOnline;
//	}
//	public void setTotalOnline(String totalOnline) {
//		this.totalOnline = totalOnline;
//	}
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
	public Integer getIsPaid() {
		return isPaid;
	}
	public void setIsPaid(Integer isPaid) {
		this.isPaid = isPaid;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getFinYaar() {
		return finYaar;
	}
	public void setFinYaar(String finYaar) {
		this.finYaar = finYaar;
	}
	public String getTotalAmountVendor() {
		return totalAmountVendor;
	}
	public void setTotalAmountVendor(String totalAmountVendor) {
		this.totalAmountVendor = totalAmountVendor;
	}
	public String getOnlineTransactionId() {
		return onlineTransactionId;
	}
	public void setOnlineTransactionId(String onlineTransactionId) {
		this.onlineTransactionId = onlineTransactionId;
	}
	public Integer getIsPayBlocked() {
		return isPayBlocked;
	}
	public void setIsPayBlocked(Integer isPayBlocked) {
		this.isPayBlocked = isPayBlocked;
	}
	
	
}
