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
@Table(name="user_media")
public class UserMedia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer mediaId;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "vendor_id", nullable = true)
	private Vendor vendor;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_id", nullable = true)
	private Customer customer;
	private String mediaUrl;
	private String mediaName;
	private String mediaExtension;
	private String mediaType;
	private String mediaSize;
	private Date createdAt;
	private Date lastUpdatedAt;
	private boolean isProfilePic = false;
	private boolean status = true;
	
	
	
	public UserMedia(Integer mediaId, Vendor vendor, Customer customer, String mediaUrl, String mediaName,
			String mediaExtension, String mediaType, String mediaSize, Date createdAt, Date lastUpdatedAt,
			boolean isProfilePic, boolean status) {
		super();
		this.mediaId = mediaId;
		this.vendor = vendor;
		this.customer = customer;
		this.mediaUrl = mediaUrl;
		this.mediaName = mediaName;
		this.mediaExtension = mediaExtension;
		this.mediaType = mediaType;
		this.mediaSize = mediaSize;
		this.createdAt = createdAt;
		this.lastUpdatedAt = lastUpdatedAt;
		this.isProfilePic = isProfilePic;
		this.status = status;
	}
	
	
	
	public UserMedia() {
		super();
		
	}



	public Integer getMediaId() {
		return mediaId;
	}
	public void setMediaId(Integer mediaId) {
		this.mediaId = mediaId;
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
	public String getMediaUrl() {
		return mediaUrl;
	}
	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}
	public String getMediaName() {
		return mediaName;
	}
	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}
	public String getMediaExtension() {
		return mediaExtension;
	}
	public void setMediaExtension(String mediaExtension) {
		this.mediaExtension = mediaExtension;
	}
	public String getMediaType() {
		return mediaType;
	}
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	public String getMediaSize() {
		return mediaSize;
	}
	public void setMediaSize(String mediaSize) {
		this.mediaSize = mediaSize;
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
	public boolean isProfilePic() {
		return isProfilePic;
	}
	public void setProfilePic(boolean isProfilePic) {
		this.isProfilePic = isProfilePic;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
}
