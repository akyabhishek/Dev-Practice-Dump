package com.fcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fcc.domain.ShopType;

@Repository
public interface ShopTypeRepo extends JpaRepository<ShopType, Integer> {

}
