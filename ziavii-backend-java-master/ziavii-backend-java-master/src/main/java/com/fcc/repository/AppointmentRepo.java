package com.fcc.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fcc.domain.Appointment;
import com.fcc.domain.Order;
import com.fcc.domain.Vendor;
import com.fcc.model.SlotCount;


@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Integer> {

	List<Appointment> findByVendor(Vendor vendor);
	
	@Query("select slot, count(*) from Appointment ap where ap.appointmentDate between ?1 and  ?2 AND ap.vendor = ?3")
	List<SlotCount> getSlotCount(Date startTimeStamp,Date endTimeStamp,Integer vendorId);
	
	@Query(value = "select * from appointment where status = 'PENDING'", nativeQuery = true)
	List<Appointment> bookings();
	
	List<Appointment> findByOrder(Order order);
	
	@Query(value = "select count(*) from appointment where slot = :slot and appointment_date = :appointmentDate and slot_status = 'BLOCKED';", nativeQuery = true)
	Integer getSlotCountByDateAndStatus(@Param("slot") String slot, @Param("appointmentDate") Date appointmentDate);
}
