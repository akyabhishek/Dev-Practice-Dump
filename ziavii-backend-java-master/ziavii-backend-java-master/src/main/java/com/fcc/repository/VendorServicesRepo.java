package com.fcc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fcc.domain.Vendor;
import com.fcc.domain.VendorServices;

@Repository
public interface VendorServicesRepo extends JpaRepository<VendorServices, Integer> {

//	@Query("SELECT n FROM VendorServices n WHERE n.latitude = ?1 AND n.longitude = ?2")
//	List<Vendor> findByLatititudeLongitude(String latitude, String longitude);
	List<VendorServices> findByVendor(Vendor vendor);
	long deleteByVendor(Vendor vendor);
	List<VendorServices> findByServiceIdAndVendor(Integer serviceId, Vendor vendor);
	long deleteByServiceId(Integer serviceId);
}
