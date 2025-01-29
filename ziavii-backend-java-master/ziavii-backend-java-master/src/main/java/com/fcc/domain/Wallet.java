package com.fcc.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "vendor_id")
    private int vendor_id;

    @Column(name = "balance")
    private int balance;

    @Column(name = "is_active")
    private Boolean is_active;

    @Column(name = "wallet_type")
    private String wallet_type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getvendor_id() {
        return vendor_id;
    }

    public void setvendor_id(int vendor_id) {
        this.vendor_id = vendor_id;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }

    public String getWallet_type() {
        return wallet_type;
    }

    public void setWallet_type(String wallet_type) {
        this.wallet_type = wallet_type;
    }




}