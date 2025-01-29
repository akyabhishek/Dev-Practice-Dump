package com.fcc.service;

import org.springframework.stereotype.Component;

import com.fcc.exception.ApiErrorCode;
import com.fcc.exception.GojoException;
import com.fcc.model.VendorDto;

@Component
public class SearchDaoService {
	

	public VendorDto getVendorsByKeyword(String keyword) {
		if(keyword == null) {
			throw new GojoException(ApiErrorCode.INVALID_INPUT+" Please provide search keyword");
		}
		
		return null;
		
	}
}
