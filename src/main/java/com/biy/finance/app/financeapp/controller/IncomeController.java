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
@RequestMapping("/v1/incomes")
public class IncomeController {
    @Autowired
    TransactionsService transactionsService;

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping
    public ResponseEntity getIncomes(@RequestHeader(value = "id", required = false) String id,
                                   @RequestHeader(value = "traceId", required = true) String traceId,
                                     @RequestHeader(value = "accountId", required = true) String accountId){

        if (isNull(id)){
            log.info("Getting all Incomes");
            return ResponseEntity.ok(transactionsService.getAllTransactions(accountId, Constants.INCOME));
        } else {
            log.info("Getting Income with ID {}", id);
            return ResponseEntity.ok(transactionsService.getTransaction(accountId, id));
        }
    }
    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping
    public ResponseEntity addIncome(@RequestHeader(value = "traceId", required = true) String traceId,
                                    @Valid @RequestBody List<Transaction> incomes){

        log.info("Adding Income {}", incomes);
        return ResponseEntity.ok(transactionsService.addTransaction(incomes));

    }
    @CrossOrigin(origins = "http://localhost:5173")
    @DeleteMapping
    public ResponseEntity deleteIncome(@RequestHeader(value = "traceId", required = true) String traceId,
                                        @RequestHeader(value = "id", required = true) String id,
                                        @RequestHeader(value = "accountId", required = true) String accountId){
        log.info("Removing Income with ID of {}", id);
        return ResponseEntity.ok(transactionsService.deleteTransaction(accountId, id));
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PatchMapping
    public ResponseEntity editIncome(@RequestHeader(value = "traceId", required = true) String traceId,
                                     @Valid @RequestBody List<Transaction> incomes){
        log.info("Editing income {}", incomes);
        return ResponseEntity.ok(transactionsService.editTransaction(incomes));
    }

}
