package com.fcc.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fcc.domain.Customer;
import com.fcc.domain.MasterBookingStatus;
import com.fcc.domain.Order;
import com.fcc.domain.Vendor;
import com.fcc.domain.VendorMonthly;
import com.fcc.exception.ApiErrorCode;
import com.fcc.exception.GojoException;
import com.fcc.exception.ResourceNotFoundException;
import com.fcc.model.BookingStatus;
import com.fcc.repository.AppointmentRepo;
import com.fcc.repository.OrdersRepo;
import com.fcc.repository.VendorMonthlyRepo;
import com.fcc.util.CommonUtils;

@Component
public class OrderDaoService {

	@Autowired
	OrdersRepo orderRepo;
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
	@Autowired
	VendorMonthlyRepo vendorMonthlyRepo;
	@Autowired
	CommonUtils commonUtils;
	
	public Order createOrder(String selectedDate, String customerId, String vendorId, String paymentMode,
			String totalPrice, Integer serviceCount, String transactionId) throws ParseException {

		if (selectedDate == null || selectedDate.isEmpty()) {
			throw new GojoException(ApiErrorCode.INVALID_INPUT + " Selected date is misisng");
		}
		if (customerId == null || customerId.isEmpty()) {
			throw new GojoException(ApiErrorCode.INVALID_INPUT + " Customer id  is misisng");
		}
		if (vendorId == null || vendorId.isEmpty()) {
			throw new GojoException(ApiErrorCode.INVALID_INPUT + " Vendor id is misisng");
		}
		if (paymentMode == null || paymentMode.isEmpty()) {
			throw new GojoException(ApiErrorCode.INVALID_INPUT + " payment mode id is misisng");
		}
		if (!paymentMode.equals("1") && !paymentMode.equals("2")) {
			throw new GojoException(ApiErrorCode.INVALID_INPUT + " paymode can only be 1 or 2");
		}
		if (paymentMode.contentEquals("1")) {
			if (transactionId == null || transactionId.isEmpty()) {
				throw new GojoException(
						ApiErrorCode.INVALID_INPUT + " Transaction id is needed in case of online payment");
			}
		}
		Date bookedSelectedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(selectedDate);
		
		Vendor vendor = vendorDaoService.findVendorById(Integer.valueOf(vendorId));
		Customer customer = customerDaoService.findUserById(Integer.valueOf(customerId));

		Order newOrder = new Order();
		newOrder.setAppointmentDate(bookedSelectedDate);
		newOrder.setOrderDate(new Date());
		newOrder.setCreatedDate(new Date());
		newOrder.setCustomer(customer);
		newOrder.setVendor(vendor);
		newOrder.setPaymentMode(paymentModeService.getPaymentModeById(Integer.valueOf(paymentMode)));
		newOrder.setStatus(bookingStatusDaoService.getBookingStatus(paymentMode.equals("1") ? 1 : 3));
		newOrder.setTotalPrice(Double.valueOf(totalPrice));
		newOrder.setTotalServicesBooked(serviceCount);
		newOrder.setTransactionId(transactionId);

		/* Put an entry in Vendor monthly with required data.
		 * and get the generated transaction id and save this id with the new order,
		 * if the monthly transaction row is already there for the current month then just fetch the object
		 * and fetch transaction id, increment the totalamnt, total booked orders etc.
		 * getMonthlyByVendorMonth whose status is not paid
		 * If the vendor cancels the order, then rollback the data for that particular order from  vendor monthly table.
		 * */
		
		newOrder = saveInVendorMonthly(newOrder,vendor);
		
		Order createdOrder = orderRepo.save(newOrder);

		return createdOrder;

	}

	public Order getOrderById(Integer orderId) {
		return orderRepo.findById(orderId).get();
	}

	public List<Order> getOrdersByStatus(BookingStatus bookingStatus) throws ParseException {
		List<Order> orders = new ArrayList<Order>();
		
		MasterBookingStatus status = bookingStatusDaoService.getBookingStatus(Integer.valueOf(bookingStatus.getStatusId()));
		if (status == null) {
			throw new ResourceNotFoundException("No status available");
		}
		if (bookingStatus.getOrderDate() == null || bookingStatus.getOrderDate().isEmpty()) {
			if(bookingStatus.getType().equals("VENDOR")) {
		/*mod on 27-05-2021	*/	orders = orderRepo.getOrdersByStatusForVendor(CommonUtils.zeroTime(new Date()), Integer.valueOf(bookingStatus.getStatusId()),Integer.valueOf(bookingStatus.getVendorId()),CommonUtils.zeroTime(new Date()));
				//orders = orderRepo.getOrdersByStatusForVendor(Integer.valueOf(bookingStatus.getStatusId()),Integer.valueOf(bookingStatus.getVendorId()),CommonUtils.zeroTime(new Date()));
			}else if(bookingStatus.getType().equals("CUSTOMER")) {
				orders = orderRepo.getOrdersByStatusForCust(CommonUtils.zeroTime(new Date()), Integer.valueOf(bookingStatus.getStatusId()),Integer.valueOf(bookingStatus.getCustomerId()),CommonUtils.zeroTime(new Date()));
			}
			
		} else {

			Date createdDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(bookingStatus.getOrderDate());
			//orders = orderRepo.getOrdersByStatus(createdDate, Integer.valueOf(bookingStatus.getStatusId()),Integer.valueOf(bookingStatus.getVendorId()));
			if(bookingStatus.getType().equals("VENDOR")) {
	 /*mod on 27-05-2021 */		orders = orderRepo.getOrdersByDateandStatusForVendor(createdDate, Integer.valueOf(bookingStatus.getStatusId()),Integer.valueOf(bookingStatus.getVendorId()),createdDate);
				//orders = orderRepo.getOrdersByDateandStatusForVendor(Integer.valueOf(bookingStatus.getStatusId()),Integer.valueOf(bookingStatus.getVendorId()),createdDate);
			}else if(bookingStatus.getType().equals("CUSTOMER")) {
				orders = orderRepo.getOrdersByDateandStatusForCust(createdDate, Integer.valueOf(bookingStatus.getStatusId()),Integer.valueOf(bookingStatus.getCustomerId()),createdDate);
			}
			
		}

		return orders;
	}
	
	
	public Order saveInVendorMonthly(Order order, Vendor vendor) {
		
		Date current = new Date();
		Random rand = new Random();
		Date firstDay = commonUtils.getFirstLastDayOfMonth(commonUtils.getPreviousMonthDate(current), true);
		String year = String.valueOf(commonUtils.getYearByDate(current));
		String month = CommonUtils.theMonth(commonUtils.getMonthByDate(current));
		VendorMonthly monthlyTransaction = vendorMonthlyRepo.getMonthlyByVendorMonthYear(vendor.getvendorId(), month, year);
		if(monthlyTransaction != null) {
			order.setFccPayId(monthlyTransaction.getTransactionId());
		/*	Double totalVendor = Double.valueOf(monthlyTransaction.getTotalAmountVendor());
			Integer bookingCount = Integer.parseInt(monthlyTransaction.getTotalConfirmedOrders());
			Double transactionAmnt = Double.valueOf(monthlyTransaction.getTransactionAmnt()); */
			
			
			//monthlyTransaction.setTotalAmountVendor(String.valueOf(totalVendor+order.getTotalPrice()));
			//monthlyTransaction.setTotalAmountVendor("0");
			//monthlyTransaction.setTransactionAmnt(String.valueOf(transactionAmnt+Double.valueOf("10.0")));
			//monthlyTransaction.setTransactionAmnt("0");
			//monthlyTransaction.setTotalConfirmedOrders(String.valueOf(bookingCount+1));
			monthlyTransaction.setLastUpdatedDate(new Date());
			vendorMonthlyRepo.save(monthlyTransaction);
		}else {
			//create an entry in a database for this month.
			monthlyTransaction = new VendorMonthly();
			monthlyTransaction.setVendor(vendor);
			monthlyTransaction.setIsPaid(0);
			monthlyTransaction.setTransactionId(CommonUtils
					.hashCal("SHA-256", Integer.toString(rand.nextInt()) + (System.currentTimeMillis() / 1000L))
					.substring(0, 20));
			//monthlyTransaction.setTransactionAmnt("10.0");
			monthlyTransaction.setTransactionAmnt("0");
			//monthlyTransaction.setTotalConfirmedOrders("1");
			monthlyTransaction.setTotalConfirmedOrders("0");
			//monthlyTransaction.setTotalAmountVendor(String.valueOf(order.getTotalPrice()));
			monthlyTransaction.setTotalAmountVendor("0");
			monthlyTransaction.setMonth(month);
			monthlyTransaction.setFinYaar(year);
			monthlyTransaction.setChargeFactor("10.0");
			monthlyTransaction.setCreatedDate(new Date());
			monthlyTransaction.setDateOfMonth(new Date());
			//monthlyTransaction.setIsPayBlocked(1);
			VendorMonthly savedMonthly = vendorMonthlyRepo.save(monthlyTransaction);
			
			order.setFccPayId(savedMonthly.getTransactionId());
			
			
		}
		
		return order;
	} 
}
