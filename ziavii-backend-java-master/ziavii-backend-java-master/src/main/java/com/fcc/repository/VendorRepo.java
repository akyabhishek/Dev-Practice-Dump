package com.fcc.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fcc.domain.Customer;
import com.fcc.domain.Order;
import com.fcc.domain.Vendor;
import com.fcc.model.ServicesDto;

@Repository
public interface VendorRepo extends JpaRepository<Vendor, Integer> {

	Optional<Vendor> findByEmail(String email);
	Optional<Vendor> findByMobile(String mobile);
	Optional<Vendor> findByPassword(String password);
	Optional<Vendor> findByTempMobile(String mobile);
	
	
	@Query("SELECT n FROM Vendor n WHERE n.latitude = ?1 AND n.longitude = ?2")
	List<Vendor> findByLatititudeLongitude(String latitude, String longitude);
	
	List<Vendor> findByNameContainingIgnoreCase(String name);
	
	@Query("Select distinct vn from Vendor as vn JOIN vn.services sr where vn.name like %:keyword% or sr.serviceName like %:keyword% ")
	List<Vendor> getAllServicesList(@Param("keyword")String keyword);
	
	
	
	
	@Query(value = "select count(*) from orders where payment_mode_id = :paymentModeId && vendor_id = :vendorId && status_id = 1", nativeQuery = true)
	Integer paymentCount(@Param("paymentModeId") Integer paymentModeId,@Param("vendorId") Integer vendorId);
	
	
	// select  v.vendor_id from vendor v  join vendor_services vs on v.vendor_id = vs.vendor_id where name  LIKE %:keyword% || service_name like %:keyword%   && (v.shop_type_id = 3 || v.shop_type_id = :shopId) group by v.vendor_id;
	               // SELECT v.vendor_id FROM vendor v join vendor_services vs on v.vendor_id = vs.vendor_id where ((v.name like '%test%' || vs.service_name like '%test%')  &&  v.shop_type_id = 2) || v.shop_type_id = 3 group by v.vendor_id;
	/* Mod on 06062021@Query(value = "SELECT v.vendor_id FROM vendor v join vendor_services vs on v.vendor_id = vs.vendor_id where ((v.name like %:keyword% || vs.service_name like %:keyword%)  &&  v.shop_type_id = :shopId) || v.shop_type_id = 3 group by v.vendor_id", nativeQuery = true)
	List<Integer> vendorIds(@Param("keyword") String keyword,@Param("shopId") Integer shopId); */
	
	
	
	@Query(value = "SELECT v.vendor_id FROM vendor v join vendor_services vs on v.vendor_id = vs.vendor_id where (v.name like %:keyword% || vs.service_name like %:keyword%) group by v.vendor_id", nativeQuery = true)
	List<Integer> vendorIds(@Param("keyword") String keyword);
	
	@Query(value = "SELECT * FROM vendor WHERE MONTH(created_date) < MONTH(CURDATE())", nativeQuery = true)
	List<Vendor> getVendorsCreatedPastMonths();
	
	
	//@Query(value = "select * from vendor v where lower(v.name) like CONCAT('%', :someSymbol, '%') %:searchTerm% || (v.vendor_id = :searchTerm || v.mobile = :searchTerm)", nativeQuery = true)
	@Query(value = "select * from vendor v where lower(v.name) like CONCAT('%', :searchTerm, '%') || (v.vendor_id = :searchTerm || v.mobile like CONCAT(:searchTerm, '%'))", nativeQuery = true)
	List<Vendor> searchVendor(@Param("searchTerm") String searchTerm);
	
	
}
