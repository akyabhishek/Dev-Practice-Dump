package com.fcc.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationPid;
import org.springframework.stereotype.Component;

import com.fcc.domain.DeviceMetaData;
import com.fcc.exception.ApiErrorCode;
import com.fcc.exception.GojoException;
import com.fcc.model.CommonDto;
import com.fcc.repository.DeviceMetaDataRepo;

@Component
public class DeviceMetaDataService {

	@Autowired
	DeviceMetaDataRepo deviceMetaDataRepo;

	public void saveLoginDetails(CommonDto commonDto, Integer userId) {

		DeviceMetaData deviceData = null;
		//DeviceMetaData deviceData = deviceMetaDataRepo.findByUserTypeAndUserId(commonDto.getType(), userId).get();
		Optional<DeviceMetaData> optionalDeviceData = deviceMetaDataRepo.findByUserTypeAndUserId(commonDto.getType(), userId);
		if(optionalDeviceData.isPresent()) {
			deviceData = optionalDeviceData.get();
		}
		
		if (deviceData == null) {
			deviceData = new DeviceMetaData();
			deviceData.setUserId(userId);
			deviceData.setUserType(commonDto.getType());
			deviceData.setDeviceToken(commonDto.getDeviceToken());
			deviceData.setDeviceType(commonDto.getDeviceType());
			deviceData.setLoginDateTime(new Date());
			deviceData.setCreatedDate(new Date());
		} else {
			deviceData.setDeviceToken(commonDto.getDeviceToken());
			deviceData.setDeviceType(commonDto.getDeviceType());
			deviceData.setLoginDateTime(new Date());
		}

		deviceMetaDataRepo.save(deviceData);

//		LoginRecord loginRecord = new LoginRecord();
//		switch (filterFlag) {
//		case "EMAIL":
//			loginRecord.setEmail(commonDto.getUserName());
//
//			break;
//		case "MOBILE":
//			loginRecord.setMobile(commonDto.getMobile());
//
//			break;
//		}
//
//		loginRecord.setDeviceToken(commonDto.getDeviceToken());
//		loginRecord.setDeviceType(commonDto.getDeviceType());
//		loginRecord.setPassword(commonDto.getPassword());
//		loginRecord.setLoginDateTime(new Date());
//		loginRecordRepo.save(loginRecord);
//		System.out.println("Saved login info");

		
	}
	
	
	public String getUserDeviceToken(Integer userId, String userType) {
		Optional<DeviceMetaData> deviceData = deviceMetaDataRepo.findByUserTypeAndUserId(userType, userId);
		if(deviceData.isPresent()) {
			return deviceData.get().getDeviceToken();
		}
		return null;
//		if(deviceData == null) {
//			throw new GojoException(" Erro occured in getting device Id");
//		}
		
		
	}
}
