package com.fcc.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fcc.domain.Order;
import com.fcc.domain.ServicesBooked;
import com.fcc.domain.Vendor;
import com.fcc.domain.VendorServices;
import com.fcc.exception.ApiErrorCode;
import com.fcc.exception.GojoException;
import com.fcc.exception.ResourceNotFoundException;
import com.fcc.model.ServicesDto;
import com.fcc.repository.ServicesBookedRepo;
import com.fcc.repository.VendorServicesRepo;

@Component
@Transactional
public class VendorServicesDaoService {

	@Autowired
	VendorServicesRepo vendorServicesRepo;
	@Autowired
	VendorDaoService vendorDaoService;
	@Autowired
	ServicesBookedRepo servicesBookedRepo;
	public ServicesDto addServices(ServicesDto servicesDto) {
//		if(servicesDto.getVendorId() == null) {
//			throw new GojoException(ApiErrorCode.INVALID_INPUT+" Vendor Id missing");
//		}
//		if(servicesDto.getServiceName() == null || servicesDto.getServiceName().isEmpty()) {
//			throw new GojoException(ApiErrorCode.INVALID_INPUT+" Service name is missing");
//		}
//		if(servicesDto.getTimeInMin() == null || servicesDto.getTimeInMin().isEmpty()) {
//			throw new GojoException(ApiErrorCode.INVALID_INPUT+" Service Time is missing");
//		}
//		if(servicesDto.getServicePrice() == null || servicesDto.getServicePrice().isEmpty()) {
//			throw new GojoException(ApiErrorCode.INVALID_INPUT+" Service price is missing");
//		}
		
		Vendor vendor = vendorDaoService.findVendorById(servicesDto.getVendorId());
		if(vendor == null) {
			throw new GojoException(" Vendor doesnot exist");
		}
		
		
		VendorServices vendorServices = new VendorServices();
		vendorServices.setVendor(vendor);
		if(!servicesDto.getServiceId().isEmpty()) {
			vendorServices.setServiceId(Integer.valueOf(servicesDto.getServiceId()));
			vendorServices.setLastUpdatedDate(new Date());
		}else {
			vendorServices.setCreateAt(new Date());
		}
		vendorServices.setServicePrice(servicesDto.getServicePrice());
		vendorServices.setServiceName(servicesDto.getServiceName());
		vendorServices.setTimeInMin(servicesDto.getTimeInMin());
		
		vendorServices = vendorServicesRepo.save(vendorServices);
		servicesDto.setServiceId(String.valueOf(vendorServices.getServiceId()));
		
		return servicesDto;
		
	}
	
	public List<ServicesDto> getServicesByVendor(Vendor vendor){
		List<VendorServices> services = vendorServicesRepo.findByVendor(vendor);
		
		 /*if(services == null || services.isEmpty()) {
			throw new GojoException(" No services found for the given vendor id");
		} */
		
		List<ServicesDto> servicesDtos = new ArrayList<ServicesDto>();
		ServicesDto servicesDto = null;
		for(VendorServices vendorServices: services) {
			servicesDto = new ServicesDto();
			servicesDto.setServiceName(vendorServices.getServiceName());
			servicesDto.setServiceId(String.valueOf(vendorServices.getServiceId()));
			servicesDto.setServicePrice(vendorServices.getServicePrice());
			servicesDto.setTimeInMin(vendorServices.getTimeInMin());
			servicesDtos.add(servicesDto);
		}
		
		return servicesDtos;
	}
	
	
	public VendorServices findServiceById(Integer serviceId) {
		if(!vendorServicesRepo.findById(serviceId).isPresent()) {
			throw new GojoException(" No services found");
		}
		
		return vendorServicesRepo.findById(serviceId).get();
	}
	
	public ServicesBooked saveBookings(long serviceId, Order order, String appointmentDate) throws ParseException {
		
		Date bookedSelectedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(appointmentDate);
		
		ServicesBooked servicesBooked = new ServicesBooked();
		servicesBooked.setAppointmentDate(bookedSelectedDate);
		servicesBooked.setCreatedDate(new Date());
		servicesBooked.setOrder(order);
		servicesBooked.setVendorServices(findServiceById(Integer.valueOf(String.valueOf(serviceId))));
		ServicesBooked createdBookings = servicesBookedRepo.save(servicesBooked);
		
		return createdBookings;
	}
	
	public long deleteServicesByVendorId(Vendor vendor) {
		return vendorServicesRepo.deleteByVendor(vendor);
	}
	
	
	public long deleteBookedServicesByService(VendorServices service) {
		return servicesBookedRepo.deleteByVendorServices(service);
	}
	
	public long deleteByService(Integer serviceId) {
		return vendorServicesRepo.deleteByServiceId(serviceId);
	}
	public List<ServicesDto> getServicesByOrder(Order order){
		List<ServicesBooked> servicesBooked = servicesBookedRepo.findByOrder(order);
		List<ServicesDto> serviceDtos = new ArrayList<ServicesDto>();
//		if(servicesBooked.size() < 1) {
//			throw new ResourceNotFoundException("No services found under this order. Kindly contact admin");
//		}
		ServicesDto serviceDto;
		for(ServicesBooked serviceBooked:servicesBooked) {
			serviceDto = new ServicesDto();
			serviceDto.setServiceId(String.valueOf(serviceBooked.getVendorServices().getServiceId()));
			serviceDto.setServiceName(serviceBooked.getVendorServices().getServiceName());
			serviceDto.setServicePrice(serviceBooked.getVendorServices().getServicePrice());
			serviceDto.setTimeInMin(serviceBooked.getVendorServices().getTimeInMin());
			serviceDtos.add(serviceDto);
		}
		
		return serviceDtos;
	}
	
	
}
