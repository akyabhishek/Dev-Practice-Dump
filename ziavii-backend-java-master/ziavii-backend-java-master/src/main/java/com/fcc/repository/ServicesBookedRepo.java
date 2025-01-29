package com.fcc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fcc.domain.Order;
import com.fcc.domain.ServicesBooked;
import com.fcc.domain.VendorServices;

@Repository
public interface ServicesBookedRepo extends JpaRepository<ServicesBooked, Integer>{

	List<ServicesBooked> findByOrder(Order order);
	long deleteByVendorServices(VendorServices service);
}
