package com.fcc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.fcc.domain.Transaction;
import com.fcc.domain.Wallet;
import com.fcc.exception.TransactionBadRequest;
import com.fcc.model.AddBalanceDetails;
import com.fcc.repository.TransactionRepository;
import com.fcc.repository.WalletRepository;

import java.io.FileWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

@RestController
@RequestMapping("/api/")
public class TransactionResource {

    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private TransactionRepository trepository;

    @Autowired
   // private UserService userService;

    private static final String TOPIC = "test";
    private static final Logger logger = LoggerFactory.getLogger(WalletResource.class);


	
    @GetMapping("/getBal/{id}")
    int getBal(@PathVariable int id) throws Exception {

        Wallet wallet = walletRepository.findWalletByvendor_id(id);

        if(wallet==null) throw new Exception("Wallet Not Found");
        else {
            return wallet.getBalance();
        }
    }
    

   

}