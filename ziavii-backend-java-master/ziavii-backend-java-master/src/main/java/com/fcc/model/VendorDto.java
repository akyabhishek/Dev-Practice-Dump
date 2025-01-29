package com.fcc.model;


import java.util.List;



import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class VendorDto extends BaseDto {

	private Integer vendorId;
	private String name;
	private String mobile;
	private String email;
	private String password;
	private String address;
	private String distance;
	private String averageRating;
	private String image;
	private Integer isUpdateFlag;
	private Integer shopTypeId;
	private String shopType;
	
	
	
	
	public Integer getShopTypeId() {
		return shopTypeId;
	}
	public void setShopTypeId(Integer shopTypeId) {
		this.shopTypeId = shopTypeId;
	}
	public String getShopType() {
		return shopType;
	}
	public void setShopType(String shopType) {
		this.shopType = shopType;
	}
	public Integer getIsUpdateFlag() {
		return isUpdateFlag;
	}
	public void setIsUpdateFlag(Integer isUpdateFlag) {
		this.isUpdateFlag = isUpdateFlag;
	}
	private List<String> images;
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public List<String> getImages() {
		return images;
	}
	public void setImages(List<String> images) {
		this.images = images;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getAverageRating() {
		return averageRating;
	}
	public void setAverageRating(String averageRating) {
		this.averageRating = averageRating;
	}
	private UserMediaDto vendorImages;
	public UserMediaDto getVendorImages() {
		return vendorImages;
	}
	public void setVendorImages(UserMediaDto vendorImages) {
		this.vendorImages = vendorImages;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	private String latitude;
	private String longitude;
	private String type;
	public String getSeats() {
		return seats;
	}
	public void setSeats(String seats) {
		this.seats = seats;
	}
	private String seats;
	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public VendorDto(Integer vendorId, String name, String mobile, String email, String password, String latitude, String longitude, String type, String seats) {
		super();
		this.vendorId = vendorId;
		this.name = name;
		this.mobile = mobile;
		this.email = email;
		this.password = password;
		this.latitude = latitude;
		this.longitude = longitude;
		this.type = type;
		this.seats = seats;
	}
	public VendorDto() {
		super();
		
	}
	
	@Override
	public String toString() {
		return "VendorDto [vendorId=" + vendorId + ", name=" + name + ", mobile=" + mobile + ", email=" + email
				+ ", password=" + password + ", latitude=" + latitude + ", longitude=" + longitude + ", type=" + type
				+ ", seats=" + seats + "]";
	}
	
	
	
	
	
	
	
	
	
}
