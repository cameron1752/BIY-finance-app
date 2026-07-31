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

    public List<Transaction> getAllTransactions(){
        return dataService.getAllTransactions();
    }

    public List<Transaction> getTransaction(int id){
        return dataService.getTransaction(id);
    }

    public List<Transaction> addTransaction(Transaction transaction) {
        return dataService.addTransaction(transaction);
    }

    public List<Transaction> deleteTransaction(Integer id) {
        return dataService.deleteTransaction(id);
    }
}
