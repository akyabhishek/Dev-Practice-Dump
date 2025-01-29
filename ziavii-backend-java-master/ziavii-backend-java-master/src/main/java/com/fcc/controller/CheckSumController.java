package com.fcc.controller;

import static org.springframework.http.ResponseEntity.ok;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fcc.exception.InvalidRequestParameterException;
import com.fcc.model.CheckSumDto;
import com.fcc.service.CheckSumService;

@RestController
@RequestMapping("/api/")
public class CheckSumController {
	
	@Autowired
	CheckSumService checkSumService;

	@PostMapping("/calculateCheckSum")
	public ResponseEntity<Map<String, Object>> calculateCheckSum(@Valid @RequestBody CheckSumDto checkSumDto) throws Exception {
		if(checkSumDto.getAmount() == null || checkSumDto.getAmount().isEmpty()) {
			throw new InvalidRequestParameterException(" Amount is needed");
		}
		if(checkSumDto.getVendorId() == null || checkSumDto.getVendorId().isEmpty()) {
			throw new InvalidRequestParameterException(" Vendor id is needed");
		}
		if(checkSumDto.getOrderId() == null || checkSumDto.getOrderId().isEmpty()) {
			throw new InvalidRequestParameterException(" Order id is needed");
		}
		
		String responseToken = checkSumService.getTransactionToken(checkSumDto.getAmount(), checkSumDto.getOrderId(), checkSumDto.getVendorId());
		
		Map<String, Object> model = new LinkedHashMap<>();
		model.put("response", responseToken);
		
		return ok(model);
	}
}
