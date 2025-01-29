package com.fcc.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Component;

import com.fcc.domain.Appointment;
import com.fcc.domain.Customer;
import com.fcc.domain.Order;
import com.fcc.domain.Vendor;
import com.fcc.exception.ApiErrorCode;
import com.fcc.exception.GojoException;
import com.fcc.model.AppointmentDto;
import com.fcc.model.SlotCount;
import com.fcc.repository.AppointmentRepo;

@Component
public class AppointmentDaoService {

	@Autowired
	AppointmentRepo appointmentRepo;
	@Autowired
	VendorDaoService vendorDaoService;
	@Autowired
	CustomerDaoService customerDaoService;
	@Autowired
	PaymentModeDaoService paymentModeService;
	@Autowired
	BookingStatusDaoService bookingStatusDaoService;
	
	public List<AppointmentDto> appointmentListByVendor(Vendor vendor){
		List<Appointment> appointmentList = appointmentRepo.findByVendor(vendor);
	    AppointmentDto appointmentDto = null;
	    List<AppointmentDto> appointmentDtos = new ArrayList<AppointmentDto>();
	    
	    
	    
	    if(!(appointmentList.size() < 1)) {
	    	for(Appointment appointment:appointmentList) {
		    	appointmentDto = new AppointmentDto();
		    	appointmentDto.setAppointId(appointment.getAppointId());
		    	appointmentDto.setCustomer(appointment.getCustomer());
		    	appointmentDto.setVendor(appointment.getVendor());
		    	appointmentDto.setAppointmentDate(appointment.getAppointmentDate());
		    	//appointmentDto.setService(appointment.getService());
		    	appointmentDto.setSlot(appointment.getSlot());
		    	appointmentDto.setSlotStatus(appointment.getSlotStatus());
		    	appointmentDtos.add(appointmentDto);
		    }
	    }
	    
	    
	    return appointmentDtos;
	}
	
	public void getSlotCount(String startTimeStamp, String endTimeStamp, Integer vendorId) throws ParseException {
		
		List<SlotCount> slotCount = appointmentRepo.getSlotCount(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTimeStamp), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTimeStamp), vendorId);
		System.out.println(slotCount);
	}
	
public String bookSlots(String selectedDate, String slot, String customerId, String vendorId, Order order) throws ParseException {
		
		System.out.println("selectedDate " +selectedDate);
		System.out.println("slot " +slot);
		System.out.println("customerId " +customerId);
		System.out.println("vendorId " +vendorId);
		
		
		
		
		if(selectedDate == null || selectedDate.isEmpty()) {
			throw new GojoException(ApiErrorCode.INVALID_INPUT+" Selected date is misisng");
		}
		if(slot == null || slot.isEmpty()) {
			throw new GojoException(ApiErrorCode.INVALID_INPUT+" slot is misisng");
		}
		if(customerId == null || customerId.isEmpty()) {
			throw new GojoException(ApiErrorCode.INVALID_INPUT+" Customer id date is misisng");
		}
		if(vendorId == null || vendorId.isEmpty()) {
			throw new GojoException(ApiErrorCode.INVALID_INPUT+" Vendor id is misisng");
		}
		Date bookedSelectedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(selectedDate);
		/*Check select Slot or slots are already booked while processing the order */
		
	    Vendor vendor = vendorDaoService.findVendorById(Integer.valueOf(vendorId));
	    
	    Customer customer = customerDaoService.findUserById(Integer.valueOf(customerId));
	    Appointment appointment = new Appointment();
	    appointment.setAppointmentDate(bookedSelectedDate);
	    appointment.setSlot(slot);
	    appointment.setCustomer(customer);
	    appointment.setVendor(vendor);
	    appointment.setSlotStatus("BLOCKED");
	    appointment.setOrder(order);
	    appointment.setCreatedDate(new Date());
	    appointmentRepo.save(appointment);
	    
	    
		
	
		return "Success";
		
	}

     public List<AppointmentDto> getSlotsByOrder(Order order){
    	 List<AppointmentDto> appointmentDtos = new ArrayList<AppointmentDto>();
    	 List<Appointment> appointments = appointmentRepo.findByOrder(order);
    	 AppointmentDto appointmentDto;
    	 List<String> sortedTimeList = new ArrayList<String>();
    	 for(Appointment appointment:appointments) {
    		 sortedTimeList.add(appointment.getSlot());
  
    	 }
    	 
    	 
    	 
    	 Collections.sort(sortedTimeList, new Comparator<String>() {

    		    @Override
    		    public int compare(String o1, String o2) {
    		        try {
    		            return new SimpleDateFormat("HH:mm").parse(o1).compareTo(new SimpleDateFormat("HH:mm").parse(o2));
    		        } catch (ParseException e) {
    		            return 0;
    		        }
    		        }
    		    });
    	 
    	 
    	 
    	 for(String s: sortedTimeList) {
    	  appointmentDto = new AppointmentDto();
 		  appointmentDto.setSlot(s);
 		  appointmentDtos.add(appointmentDto);
    	 }
    	 
    	 return appointmentDtos;
     } 

//     public boolean confirmBooking() {
//    	 
//     }

}
