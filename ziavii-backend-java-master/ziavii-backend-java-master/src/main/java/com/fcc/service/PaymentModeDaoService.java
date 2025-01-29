package com.fcc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fcc.domain.PaymentMode;
import com.fcc.repository.PaymentModeRepo;

@Component
public class PaymentModeDaoService {

	@Autowired
	PaymentModeRepo paymentModeRepo;
	
	public PaymentMode getPaymentModeById(Integer id) {
		Optional<PaymentMode> paymentMode = paymentModeRepo.findById(id);
		if(!paymentMode.isPresent()) {
			return null;
		}
		return paymentMode.get();
		
	}
}
