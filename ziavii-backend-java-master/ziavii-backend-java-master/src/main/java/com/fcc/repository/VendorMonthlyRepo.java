package com.fcc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fcc.domain.Vendor;
import com.fcc.domain.VendorMonthly;

@Repository
public interface VendorMonthlyRepo extends JpaRepository<VendorMonthly, Long> {

/*	@Query(value = "select * from vendor_monthly where vendor_id = :vendorId and month = :month and fin_year = :fin_year", nativeQuery = true)
	VendorMonthly getMonthlyTrnFccByVendor(@Param("vendorId") Integer vendorId,@Param("month") String month,@Param("fin_year") String fin_year); */
	
	@Query(value = "select * from vendor_monthly where vendor_id = :vendorId and date(date_of_month) < now()", nativeQuery = true)
	List<VendorMonthly> getMonthlyTrnFccByVendor(@Param("vendorId") Integer vendorId);
	
	
	/* @Query(value = "select * from vendor_monthly where is_paid = 0 and total_amount_vendor != 0 and vendor_id = :vendorId", nativeQuery = true)
	List<VendorMonthly> getMonthlyUnPaidTransactions(@Param("vendorId") Integer vendorId); */
	
	@Query(value = "select * from vendor_monthly where is_paid = 0 and total_amount_vendor != 0 and vendor_id = :vendorId and is_pay_blocked = 0", nativeQuery = true)
	List<VendorMonthly> getMonthlyUnPaidTransactions(@Param("vendorId") Integer vendorId);
	
	VendorMonthly findByTransactionId(String transactionId);
	
	@Query(value = "select * from vendor_monthly where is_paid = 0 and total_amount_vendor != 0 and vendor_id = :vendorId and date(date_of_month) < now()", nativeQuery = true)
	List<VendorMonthly> getAllUnPaidTransByVendor(@Param("vendorId") Integer vendorId);
	
	/* To get the monthly record by vendo, month and finyear*/
    @Query(value = "select * from vendor_monthly where vendor_id = :vendorId and month = :month and fin_year = :year", nativeQuery = true)
	VendorMonthly getMonthlyByVendorMonthYear(@Param("vendorId") Integer vendorId,@Param("month") String month,@Param("year") String year);
    
    
    
    
	
}
