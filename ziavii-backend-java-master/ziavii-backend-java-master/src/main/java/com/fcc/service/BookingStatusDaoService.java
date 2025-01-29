package com.fcc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fcc.domain.MasterBookingStatus;
import com.fcc.exception.ApiErrorCode;
import com.fcc.exception.GojoException;
import com.fcc.repository.MasterBookingStatusRepo;

@Component
public class BookingStatusDaoService {

	@Autowired
	MasterBookingStatusRepo bookingStatusRepo;
	
	public String getStatusById(Integer id) {
		Optional<MasterBookingStatus> bookingStatus = bookingStatusRepo.findById(id);
		String status = "";
		if(!bookingStatus.isPresent()) {
			throw new GojoException(" No status found ");
		}
		
		status = bookingStatus.get().getStatus();
		return status;
	}
	
	
	public MasterBookingStatus getBookingStatus(Integer id) {
		Optional<MasterBookingStatus> bookingStatus = bookingStatusRepo.findById(id);
		String status = "";
		if(!bookingStatus.isPresent()) {
			throw new GojoException(" No status found ");
		}
		
		return bookingStatus.get();
	}
	
	
   
}
