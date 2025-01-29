package com.fcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fcc.domain.MasterGender;

@Repository
public interface MasterGenderRepo  extends JpaRepository<MasterGender, Integer>{

}
