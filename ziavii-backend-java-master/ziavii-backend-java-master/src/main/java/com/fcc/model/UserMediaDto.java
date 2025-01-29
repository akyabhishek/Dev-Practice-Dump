package com.fcc.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@JsonInclude(Include.NON_NULL)
public class UserMediaDto {

	@JsonProperty(access = Access.WRITE_ONLY)
	private Integer userMediaId;
	private Integer imageId;
	
	public Integer getImageId() {
		return imageId;
	}



	public void setImageId(Integer imageId) {
		this.imageId = imageId;
	}



	public Integer getUserMediaId() {
		return userMediaId;
	}



	public void setUserMediaId(Integer userMediaId) {
		this.userMediaId = userMediaId;
	}



	private String imageName;
	private String imageUrl;
	
	

	public UserMediaDto() {
		super();
		// TODO Auto-generated constructor stub
	}



	public UserMediaDto(String imageName, String imageUrl) {
		super();
		this.imageName = imageName;
		this.imageUrl = imageUrl;
	}




	public String getImageName() {
		return imageName;
	}



	public void setImageName(String imageName) {
		this.imageName = imageName;
	}



	public String getImageUrl() {
		return imageUrl;
	}



	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
}
