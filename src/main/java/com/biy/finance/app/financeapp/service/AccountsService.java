package com.biy.finance.app.financeapp.service;

import com.biy.finance.app.financeapp.data.DataService;
import com.biy.finance.app.financeapp.model.Account;
import com.biy.finance.app.financeapp.model.Settings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AccountsService {
    @Autowired
    DataService dataService;

    public Account getAccount(String id){
        log.info("Fetching account with id: {}", id);
        return dataService.getAccounts(id);
    }
}
