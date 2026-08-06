package com.biy.finance.app.financeapp.service;

import com.biy.finance.app.financeapp.data.DataService;
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

    public List<Transaction> getAllTransactions(String accountId, String type){
        return dataService.getAllTransactions(accountId, type);
    }

    public List<Transaction> getTransaction(String accountId, String id){
        return dataService.getTransaction(accountId, id);
    }

    public List<Transaction> addTransaction(Transaction transaction) {
        return dataService.addTransaction(transaction);
    }

    public List<Transaction> deleteTransaction(String accountId, String id) {
        return dataService.deleteTransaction(accountId, id);
    }

    public List<Transaction> editTransaction(Transaction transaction) {
        return dataService.editTransaction(transaction);
    }
}
