package com.fcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fcc.domain.MasterBookingStatus;
@Repository
public interface MasterBookingStatusRepo extends JpaRepository<MasterBookingStatus, Integer> {

}
