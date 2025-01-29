package com.fcc.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fcc.domain.MasterBookingStatus;
import com.fcc.domain.Order;
import com.fcc.domain.PaymentMode;
import com.fcc.domain.Vendor;

@Repository
public interface OrdersRepo extends JpaRepository<Order, Integer> {

	Order findByVendorAndOrderId(Vendor vendor, Integer orderId);
	List<Order> findByStatus(MasterBookingStatus status);
	List<Order> findByStatusAndCreatedDate(MasterBookingStatus status,Date date);
	
	 @Query(value = "SELECT order_id,appointment_date,booking_duration,created_date,last_updated_date,order_date,total_price,total_services_booked,transaction_id,customer_id,payment_mode_id,status_id,vendor_id,fcc_pay_id FROM orders where (date(created_date) >= :createdDate || date(appointment_date) >= :appointmentDate) && status_id =:status && vendor_id = :vendorId order by appointment_date desc", nativeQuery = true)
	List<Order> getOrdersByStatusForVendor(@Param("createdDate") Date createdDate,@Param("status") Integer status,@Param("vendorId") Integer vendorId, @Param("appointmentDate") Date appointmentDate); 
	
	/* @Query(value = "SELECT order_id,appointment_date,booking_duration,created_date,last_updated_date,order_date,total_price,total_services_booked,transaction_id,customer_id,payment_mode_id,status_id,vendor_id,fcc_pay_id FROM orders where (date(appointment_date) >= :appointmentDate) && status_id =:status && vendor_id = :vendorId order by appointment_date", nativeQuery = true)
	List<Order> getOrdersByStatusForVendor(@Param("status") Integer status,@Param("vendorId") Integer vendorId, @Param("appointmentDate") Date appointmentDate); */
	
	@Query(value = "SELECT order_id,appointment_date,booking_duration,created_date,last_updated_date,order_date,total_price,total_services_booked,transaction_id,customer_id,payment_mode_id,status_id,vendor_id,fcc_pay_id FROM orders where (date(created_date) = :createdDate || date(appointment_date) = :appointmentDate) && status_id =:status && vendor_id = :vendorId", nativeQuery = true)
	List<Order> getOrdersByDateandStatusForVendor(@Param("createdDate") Date createdDate,@Param("status") Integer status,@Param("vendorId") Integer vendorId, @Param("appointmentDate") Date appointmentDate);
	
	
	/* @Query(value = "SELECT order_id,appointment_date,booking_duration,created_date,last_updated_date,order_date,total_price,total_services_booked,transaction_id,customer_id,payment_mode_id,status_id,vendor_id,fcc_pay_id FROM orders where (date(appointment_date) = :appointmentDate) && status_id =:status && vendor_id = :vendorId", nativeQuery = true)
	List<Order> getOrdersByDateandStatusForVendor(@Param("status") Integer status,@Param("vendorId") Integer vendorId, @Param("appointmentDate") Date appointmentDate); */
	
	
	@Query(value = "SELECT order_id,appointment_date,booking_duration,created_date,last_updated_date,order_date,total_price,total_services_booked,transaction_id,customer_id,payment_mode_id,status_id,vendor_id,fcc_pay_id FROM orders where (date(created_date) >= :createdDate || date(appointment_date) >= :appointmentDate) && status_id =:status && customer_id = :customerId order by appointment_date desc", nativeQuery = true)
	List<Order> getOrdersByStatusForCust(@Param("createdDate") Date createdDate,@Param("status") Integer status,@Param("customerId") Integer customerId, @Param("appointmentDate") Date appointmentDate);
	
	@Query(value = "SELECT order_id,appointment_date,booking_duration,created_date,last_updated_date,order_date,total_price,total_services_booked,transaction_id,customer_id,payment_mode_id,status_id,vendor_id,fcc_pay_id FROM orders where (date(created_date) = :createdDate || date(appointment_date) = :appointmentDate) && status_id =:status && customer_id = :customerId", nativeQuery = true)
	List<Order> getOrdersByDateandStatusForCust(@Param("createdDate") Date createdDate,@Param("status") Integer status,@Param("customerId") Integer customerId, @Param("appointmentDate") Date appointmentDate);
	
	//@Query(value="select * from orders where vendor_id = 2 and date(created_date) between :startDate and :endDate and payment_mode_id = 2 and status_id = 1",nativeQuery = true)
	@Query(value="select * from orders where  date(created_date) between :startDate and :endDate and payment_mode_id = 2 and status_id = 1",nativeQuery = true)
	List<Order> getOfflineOrdersMonth(@Param("startDate") Date startDate,@Param("endDate") Date endDate);
	
	
	@Query(value="select sum(total_price) from orders where vendor_id = :vendorId and  date(created_date) between :startDate and :endDate and payment_mode_id = 2 and status_id = 1",nativeQuery = true)
	Double getSumOfEarningsMonth(@Param("startDate") Date startDate,@Param("endDate") Date endDate, @Param("vendorId") Integer vendorId);
	
	@Query(value="select * from orders where vendor_id = :vendorId and  date(created_date) between :startDate and :endDate and payment_mode_id = 2 and status_id = 1",nativeQuery = true)
	List<Order> getTotalConfirmedOfflineOrdersMonthByVendor(@Param("startDate") Date startDate,@Param("endDate") Date endDate, @Param("vendorId") Integer vendorId);
	
	@Query(value="select * from orders where fcc_pay_id in :fccPayIds and status_id = 1 and payment_mode_id = 2 order by appointment_date desc ",nativeQuery = true)
	List<Order> getTotalConfirmedOfflineOrdersMonthByFccPayId(List<String> fccPayIds);
	
	/*
	 * New implementation for orders between two dates
	 * */
	//@Query(value="select * from orders where created_date between = :startDate and :endDate and status_id = :status and vendor_id = :vendorId",nativeQuery = true)
	@Query(value="select * from orders where created_date between :startDate and :endDate and status_id = :status",nativeQuery = true)
	List<Order> getOrdersByDateRange(@Param("startDate") Date startDate,@Param("endDate") Date endDate, @Param("status") Integer status);
	
}
