package com.biy.finance.app.financeapp.controller;

import com.biy.finance.app.financeapp.model.Transaction;
import com.biy.finance.app.financeapp.service.AccountsService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/v1/accounts")
public class AccountsController {

    @Autowired
    AccountsService accountsService;

    @GetMapping
    public ResponseEntity getAccount(@RequestHeader(value = "traceId") String traceId,
                                     @RequestHeader(value = "accountId") String accountId){

        return ResponseEntity.ok(accountsService.getCurrentAccount());
    }
}
