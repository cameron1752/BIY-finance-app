package com.biy.finance.app.financeapp.controller;

import com.biy.finance.app.financeapp.model.Transaction;
import com.biy.finance.app.financeapp.service.TransactionsService;
import com.biy.finance.app.financeapp.util.Constants;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Objects.isNull;

@Slf4j
@Controller
@RequestMapping("/v1/bills")
public class BillsController {
    @Autowired
    TransactionsService transactionsService;

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping
    public ResponseEntity getBills(@RequestHeader(value = "id", required = false) String id,
                                    @RequestHeader(value = "traceId", required = true) String traceId,
                                   @RequestHeader(value = "accountId", required = true) String accountId){

        if (isNull(id)){
            log.info("Getting all {} for account {}", Constants.BILL, accountId);
            return ResponseEntity.ok(transactionsService.getAllTransactions(accountId, Constants.BILL));
        } else {
            log.info("Getting transaction with ID {} for account {}", id, accountId);
            return ResponseEntity.ok(transactionsService.getTransaction(accountId, id));
        }
    }
    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping
    public ResponseEntity addBill(@RequestHeader(value = "traceId", required = true) String traceId,
                                  @Valid @RequestBody List<Transaction> bills){

        log.info("Adding bill {}", bills);
        return ResponseEntity.ok(transactionsService.addTransaction(bills));

    }
    @CrossOrigin(origins = "http://localhost:5173")
    @DeleteMapping
    public ResponseEntity deleteBill(@RequestHeader(value = "traceId", required = true) String traceId,
                                        @RequestHeader(value = "id", required = true) String id,
                                        @RequestHeader(value = "accountId", required = true) String accountId){
        log.info("Removing bill with ID of {}", id);
        return ResponseEntity.ok(transactionsService.deleteTransaction(accountId, id));
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PatchMapping
    public ResponseEntity editBill(@RequestHeader(value = "traceId", required = true) String traceId,
                                   @Valid @RequestBody List<Transaction> bills){
        log.info("Editing bill {}", bills);
        return ResponseEntity.ok(transactionsService.editTransaction(bills));
    }

}
