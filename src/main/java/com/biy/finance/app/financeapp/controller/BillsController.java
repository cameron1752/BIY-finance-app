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
@RequestMapping("/v1/bills")
public class BillsController {
    @Autowired
    TransactionsService transactionsService;

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping
    public ResponseEntity getBills(@RequestHeader(value = "id", required = false) Integer id,
                                    @RequestHeader(value = "traceId", required = true) String traceId){

        if (isNull(id)){
            log.info("Getting all bills");
            return ResponseEntity.ok(transactionsService.getAllBills());
        } else {
            log.info("Getting bill with ID {}", id);
            return ResponseEntity.ok(transactionsService.getBill(id));
        }
    }
    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping
    public ResponseEntity addBill(@RequestHeader(value = "traceId", required = true) String traceId,
                                         @RequestBody Transaction bill){

        log.info("Adding bill {}", bill);
        return ResponseEntity.ok(transactionsService.addBill(bill));

    }
    @CrossOrigin(origins = "http://localhost:5173")
    @DeleteMapping
    public ResponseEntity deleteBill(@RequestHeader(value = "traceId", required = true) String traceId,
                                            @RequestHeader(value = "id", required = true) Integer id){
        log.info("Removing bill with ID of {}", id);
        return ResponseEntity.ok(transactionsService.deleteBill(id));
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PatchMapping
    public ResponseEntity editBill(@RequestHeader(value = "traceId", required = true) String traceId,
                                   @RequestBody Transaction bill){
        log.info("Editing bill {}", bill.getId());
        return ResponseEntity.ok(transactionsService.editBill(bill));
    }

}
