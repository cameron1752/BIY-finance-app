package com.biy.finance.app.financeapp.controller;

import com.biy.finance.app.financeapp.model.Account;
import com.biy.finance.app.financeapp.model.Transaction;
import com.biy.finance.app.financeapp.service.AccountsService;
import com.biy.finance.app.financeapp.service.TransactionsService;
import com.biy.finance.app.financeapp.util.Constants;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Objects.isNull;

@Slf4j
@Controller
@RequestMapping("/v1/transactions")
public class TransactionsController {
    @Autowired
    TransactionsService transactionsService;
    @Autowired
    AccountsService accountsService;

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping
    public ResponseEntity getTransactions(@RequestHeader(value = "id", required = false) String id,
                                          @RequestHeader(value = "traceId", required = true) String traceId
                                            ){
        if (isNull(id)){
            log.info("Getting all {}", Constants.TRANSACTION);
            return ResponseEntity.ok(transactionsService.getAllTransactions(Constants.TRANSACTION));
        } else {
            log.info("Getting transaction with ID {}", id);
            return ResponseEntity.ok(transactionsService.getTransaction(id));
        }
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping
    public ResponseEntity addTransaction(@RequestHeader(value = "traceId", required = true) String traceId,
                                         @Valid @RequestBody List<Transaction> transactions){

        log.info("Adding transactions {}", transactions);
        return ResponseEntity.ok(transactionsService.addTransaction(transactions));

    }

    @DeleteMapping
    public ResponseEntity deleteTransaction(@RequestHeader(value = "traceId", required = true) String traceId,
                                            @RequestHeader(value = "id", required = true) String id){
        log.info("Removing transaction with ID of {}", id);
        return ResponseEntity.ok(transactionsService.deleteTransaction(id));
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PatchMapping
    public ResponseEntity editTransaction(@RequestHeader(value = "traceId", required = true) String traceId,
                                          @Valid @RequestBody List<Transaction> transactions){
        log.info("Editing transactions {}", transactions);
        return ResponseEntity.ok(transactionsService.editTransaction(transactions));
    }

}
