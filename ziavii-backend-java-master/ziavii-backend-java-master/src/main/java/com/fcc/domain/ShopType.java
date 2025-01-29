package com.fcc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="shop_type")
public class ShopType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "shop_type_id", nullable = false)
	private Integer shopTypeId;
	@Column(name = "type", nullable = false)
	private String type;
	
	public Integer getShopTypeId() {
		return shopTypeId;
	}
	public void setShopTypeId(Integer shopTypeId) {
		this.shopTypeId = shopTypeId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
