package com.fcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fcc.domain.LoginRecord;

@Repository
public interface LoginRecordRepo extends JpaRepository<LoginRecord, Integer> {

}
