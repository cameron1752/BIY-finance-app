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
@RequestMapping("/v1/income")
public class IncomeController {
    @Autowired
    TransactionsService transactionsService;

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping
    public ResponseEntity getIncomes(@RequestHeader(value = "id", required = false) String id,
                                   @RequestHeader(value = "traceId", required = true) String traceId){

        if (isNull(id)){
            log.info("Getting all Incomes");
            return ResponseEntity.ok(transactionsService.getAllIncomes());
        } else {
            log.info("Getting Income with ID {}", id);
            return ResponseEntity.ok(transactionsService.getIncome(id));
        }
    }
    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping
    public ResponseEntity addIncome(@RequestHeader(value = "traceId", required = true) String traceId,
                                  @RequestBody Transaction Income){

        log.info("Adding Income {}", Income);
        return ResponseEntity.ok(transactionsService.addIncome(Income));

    }
    @CrossOrigin(origins = "http://localhost:5173")
    @DeleteMapping
    public ResponseEntity deleteIncome(@RequestHeader(value = "traceId", required = true) String traceId,
                                     @RequestHeader(value = "id", required = true) String id){
        log.info("Removing Income with ID of {}", id);
        return ResponseEntity.ok(transactionsService.deleteIncome(id));
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PatchMapping
    public ResponseEntity editIncome(@RequestHeader(value = "traceId", required = true) String traceId,
                                   @RequestBody Transaction Income){
        log.info("Editing Income {}", Income.getId());
        return ResponseEntity.ok(transactionsService.editIncome(Income));
    }

}
