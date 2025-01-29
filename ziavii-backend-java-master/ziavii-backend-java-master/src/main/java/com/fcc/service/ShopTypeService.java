package com.fcc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fcc.domain.ShopType;
import com.fcc.repository.ShopTypeRepo;

@Component
public class ShopTypeService {

	@Autowired
	ShopTypeRepo shopTypeRepo; 
	
	public ShopType getShopTypeById(Integer id) {
		return shopTypeRepo.findById(id).get();
	}
}
