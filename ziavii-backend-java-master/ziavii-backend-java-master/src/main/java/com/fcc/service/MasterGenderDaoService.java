package com.fcc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fcc.domain.MasterGender;
import com.fcc.repository.MasterGenderRepo;

@Service
public class MasterGenderDaoService {

	@Autowired
	MasterGenderRepo masterGenderRepo;
	
	public MasterGender getGenderById(Integer genderId) {
		Optional<MasterGender> gender = masterGenderRepo.findById(genderId);
		if(gender.isPresent()) {
			return gender.get();
		}
		return null;
	}
}
