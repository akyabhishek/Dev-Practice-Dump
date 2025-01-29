package com.fcc.service;

import static org.springframework.http.ResponseEntity.ok;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fcc.exception.InvalidRequestParameterException;
import com.fcc.model.CommonDto;

@Service
public class AdminDaoService {
	
	@Autowired
	VendorDaoService vendorDaoService;
	@Autowired
	CustomerDaoService customerDaoService;
    
 
	/*
	 * Query to get customer based on id, name and mobile
	 * */
	//select * from customer where customer_id = 'Asad' or mobile = 'Asad' or name = 'Asad';
	
}
