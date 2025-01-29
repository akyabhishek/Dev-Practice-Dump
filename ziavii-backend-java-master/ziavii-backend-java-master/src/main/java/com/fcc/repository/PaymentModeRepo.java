package com.fcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fcc.domain.PaymentMode;

@Repository
public interface PaymentModeRepo extends JpaRepository<PaymentMode, Integer> {

}
