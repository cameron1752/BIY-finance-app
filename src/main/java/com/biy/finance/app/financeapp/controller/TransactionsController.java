package com.biy.finance.app.financeapp.controller;

import com.biy.finance.app.financeapp.model.Transaction;
import com.biy.finance.app.financeapp.service.TransactionsService;
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
    public ResponseEntity getTransactions(@RequestHeader(value = "id", required = false) Integer id,
                                          @RequestHeader(value = "traceId", required = true) String traceId){

        if (isNull(id)){
            log.info("Getting all transactions");
            return ResponseEntity.ok(transactionsService.getAllTransactions());
        } else {
            log.info("Getting transaction with ID {}", id);
            return ResponseEntity.ok(transactionsService.getTransaction(id));
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
                                            @RequestHeader(value = "id", required = true) Integer id){
        log.info("Removing transaction with ID of {}", id);
        return ResponseEntity.ok(transactionsService.deleteTransaction(id));
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PatchMapping
    public ResponseEntity editTransaction(@RequestHeader(value = "traceId", required = true) String traceId,
                                   @RequestBody Transaction transaction){
        log.info("Editing bill {}", transaction.getId());
        return ResponseEntity.ok(transactionsService.editTransaction(transaction));
    }

}
