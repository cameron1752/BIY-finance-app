package com.biy.finance.app.financeapp.service;

import com.biy.finance.app.financeapp.data.DataService;
import com.biy.finance.app.financeapp.model.Account;
import com.biy.finance.app.financeapp.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TransactionsService {

    @Autowired
    DataService dataService;

    @Autowired
    AccountsService accountsService;

    public List<Transaction> getAllTransactions(String type){
        Account account = accountsService.getCurrentAccount();
        return dataService.getAllTransactions(account.getId(), type);
    }

    public List<Transaction> getTransaction(String id){
        Account account = accountsService.getCurrentAccount();
        return dataService.getTransaction(account.getId(), id);
    }

    public List<Transaction> addTransaction(List<Transaction> transactions) {
        Account account = accountsService.getCurrentAccount();
        return dataService.addTransaction(transactions, account.getId());
    }

    public List<Transaction> deleteTransaction(String id) {
        Account account = accountsService.getCurrentAccount();
        return dataService.deleteTransaction(account.getId(), id);
    }

    public List<Transaction> editTransaction(List<Transaction> transactions) {
        Account account = accountsService.getCurrentAccount();
        return dataService.editTransaction(transactions, account.getId());
    }
}
