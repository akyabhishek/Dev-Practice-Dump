package com.fcc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fcc.domain.OpeningClosingTime;
import com.fcc.domain.Vendor;
import com.fcc.exception.ApiErrorCode;
import com.fcc.exception.GojoException;
import com.fcc.model.OpeningClosingTimeDto;
import com.fcc.repository.OpClRepo;
import com.fcc.util.CommonUtils;

import io.swagger.annotations.Api;

@Component
@Transactional
public class OpenCloseTimeDaoService {

	@Autowired
	OpClRepo openCloseRepo;
	@Autowired
	VendorDaoService vendorDaoService;
	@Autowired
	CommonUtils commonUtils;
	
	public OpeningClosingTimeDto addTimes(OpeningClosingTimeDto opClTimeDto) {
//		if(opClTimeDto.getVendorId() == null) {
//			throw new GojoException(ApiErrorCode.INVALID_INPUT +" Vendor Id is missing");
//			
//		}
//		if(opClTimeDto.getStatus() == null || opClTimeDto.getStatus().isEmpty()) {
//			throw new GojoException(ApiErrorCode.INVALID_INPUT +" Status is missing");
//		}
//		if(!opClTimeDto.getStatus().equals("CLOSE")) {
//			if(opClTimeDto.getOpenTime() == null || opClTimeDto.getOpenTime().isEmpty()) {
//				throw new GojoException(ApiErrorCode.INVALID_INPUT +" Open time is missing");
//			}
//			if(opClTimeDto.getCloseTime() == null || opClTimeDto.getCloseTime().isEmpty()) {
//				throw new GojoException(ApiErrorCode.INVALID_INPUT +" Close time is missing");
//			}
//		}
//		
//		if(opClTimeDto.getWeekDay() == null || opClTimeDto.getWeekDay().isEmpty()) {
//			throw new GojoException(ApiErrorCode.INVALID_INPUT +" Weekday is missing");
//		}
		
		Vendor vendor = vendorDaoService.findVendorById(opClTimeDto.getVendorId());
		if(vendor == null) {
			throw new GojoException(" Vendor doesnot exist");
		}
		OpeningClosingTime openCloseTime = new OpeningClosingTime();
		if(!opClTimeDto.getOpClId().isEmpty()) {
			openCloseTime.setOpClId(Integer.valueOf(opClTimeDto.getOpClId()));
			openCloseTime.setLastUpdatedAt(new Date());
		}else {
			openCloseTime.setCreatedAt(new Date());
		}
		openCloseTime.setVendor(vendor);
		openCloseTime.setOpenTime(opClTimeDto.getOpenTime());
		openCloseTime.setCloseTime(opClTimeDto.getCloseTime());
		openCloseTime.setWeekDay(opClTimeDto.getWeekDay());
		openCloseTime.setStatus(opClTimeDto.getStatus());
		if(opClTimeDto.getStatus().equals("CLOSE")) {
			openCloseTime.setOpenTime("");
			openCloseTime.setCloseTime("");
		}
		
		
		openCloseTime = openCloseRepo.save(openCloseTime);
		opClTimeDto.setOpClId(String.valueOf(openCloseTime.getOpClId()));
		
		return opClTimeDto;
	}
	
	
	public List<OpeningClosingTimeDto> getTimingsByVendorId(Vendor vendor) {
		List<OpeningClosingTime> timingList = new ArrayList<OpeningClosingTime>();
		timingList = openCloseRepo.findByVendor(vendor);
		OpeningClosingTimeDto openinClosingTimeDto = null;
		List<OpeningClosingTimeDto> timingListDtos = new ArrayList<>();
		
		timingListDtos = commonUtils.getSortedTimingsList(timingList);
		
//		for(OpeningClosingTime openingClosingTime: timingList) {
//			openinClosingTimeDto = new OpeningClosingTimeDto();
//			openinClosingTimeDto.setOpClId(String.valueOf(openingClosingTime.getOpClId()));
//			openinClosingTimeDto.setOpenTime(openingClosingTime.getOpenTime());
//			openinClosingTimeDto.setCloseTime(openingClosingTime.getCloseTime());
//			openinClosingTimeDto.setStatus(openingClosingTime.getStatus());
//			openinClosingTimeDto.setWeekDay(openingClosingTime.getWeekDay());
//			timingListDtos.add(openinClosingTimeDto);
//		}
		
		return timingListDtos;
	}
	
	public long deleteServicesByVendorId(Vendor vendor) {
		return openCloseRepo.deleteByVendor(vendor);
	}
}
