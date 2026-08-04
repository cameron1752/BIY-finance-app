package com.biy.finance.app.financeapp.controller;

import com.biy.finance.app.financeapp.model.Transaction;
import com.biy.finance.app.financeapp.service.TransactionsService;
import com.biy.finance.app.financeapp.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static java.util.Objects.isNull;

@Slf4j
@Controller
@RequestMapping("/v1/transactions")
public class TransactionsController {
    @Autowired
    TransactionsService transactionsService;

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping
    public ResponseEntity getTransactions(@RequestHeader(value = "id", required = false) String id,
                                          @RequestHeader(value = "traceId", required = true) String traceId,
                                          @RequestHeader(value = "accountId", required = true) String accountId){

        if (isNull(id)){
            log.info("Getting all {} for account {}", Constants.TRANSACTION, accountId);
            return ResponseEntity.ok(transactionsService.getAllTransactions(accountId, Constants.TRANSACTION));
        } else {
            log.info("Getting transaction with ID {} for account {}", id, accountId);
            return ResponseEntity.ok(transactionsService.getTransaction(accountId, id));
        }
    }

    @PostMapping
    public ResponseEntity addTransaction(@RequestHeader(value = "traceId", required = true) String traceId,
                                         @RequestBody Transaction transaction){

        log.info("Adding transaction {}", transaction);
        return ResponseEntity.ok(transactionsService.addTransaction(transaction));

    }

    @DeleteMapping
    public ResponseEntity deleteTransaction(@RequestHeader(value = "traceId", required = true) String traceId,
                                            @RequestHeader(value = "id", required = true) String id,
                                            @RequestHeader(value = "accountId", required = true) String accountId){
        log.info("Removing transaction with ID of {}", id);
        return ResponseEntity.ok(transactionsService.deleteTransaction(accountId, id));
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PatchMapping
    public ResponseEntity editTransaction(@RequestHeader(value = "traceId", required = true) String traceId,
                                   @RequestBody Transaction transaction){
        log.info("Editing bill {}", transaction.getId());
        return ResponseEntity.ok(transactionsService.editTransaction(transaction));
    }

}
