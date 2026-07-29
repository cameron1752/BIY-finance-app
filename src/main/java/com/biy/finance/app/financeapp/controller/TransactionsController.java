package com.biy.finance.app.financeapp.controller;

import com.biy.finance.app.financeapp.service.TransactionsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import static java.util.Objects.isNull;

@Slf4j
@Controller
@RequestMapping("/v1/transactions")
public class TransactionsController {

    @Autowired
    TransactionsService transactionsService;

    @GetMapping
    public ResponseEntity getTransactions(@RequestHeader(value = "id", required = false) Integer id){
        if (isNull(id)){
            log.info("Getting all transactions");
            return ResponseEntity.ok(transactionsService.getAllTransactions());
        } else {
            log.info("Getting transaction with ID {}", id);
            return ResponseEntity.ok(transactionsService.getTransaction(id));
        }
    }

}
