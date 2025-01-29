package com.fcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fcc.domain.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {

    @Query("SELECT w FROM Wallet w WHERE w.vendor_id = ?1")
    Wallet findWalletByvendor_id(int vendor_id);
}