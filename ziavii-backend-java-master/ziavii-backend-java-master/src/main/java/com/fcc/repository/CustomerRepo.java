package com.fcc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fcc.domain.Customer;
import com.fcc.domain.Vendor;


@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {

	Optional<Customer> findByEmail(String email);
	Optional<Customer> findByMobile(String mobile);
	Optional<Customer> findByTempMobile(String mobile);
	
	@Query(value = "select * from customer c where lower(c.name) like CONCAT('%', :searchTerm, '%') || (c.customer_id = :searchTerm || c.mobile like CONCAT(:searchTerm, '%'))", nativeQuery = true)
	List<Customer> searchCustomer(@Param("searchTerm") String searchTerm);
}
