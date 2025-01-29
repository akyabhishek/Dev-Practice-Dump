package com.fcc.model;

public class VendorConfirmCancelModel {

	private Integer vendorId;
	private Integer orderId;
	private String cancelConfirmFlag;
	
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public String getCancelConfirmFlag() {
		return cancelConfirmFlag;
	}
	public void setCancelConfirmFlag(String cancelConfirmFlag) {
		this.cancelConfirmFlag = cancelConfirmFlag;
	}
	
	
	
}
