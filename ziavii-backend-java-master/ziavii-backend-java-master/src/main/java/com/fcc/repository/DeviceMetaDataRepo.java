package com.fcc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fcc.domain.DeviceMetaData;
@Repository
public interface DeviceMetaDataRepo extends JpaRepository<DeviceMetaData, Integer>{

	Optional<DeviceMetaData> findByUserTypeAndUserId(String userType, Integer userId);
}
