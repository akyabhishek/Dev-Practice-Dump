package com.fcc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fcc.domain.OpeningClosingTime;
import com.fcc.domain.Vendor;

@Repository
public interface OpClRepo extends JpaRepository<OpeningClosingTime, Integer> {

	List<OpeningClosingTime> findByVendor(Vendor vendor);
	long deleteByVendor(Vendor vendor);
}
