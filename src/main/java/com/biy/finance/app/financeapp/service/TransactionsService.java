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

    public List<Transaction> getAllTransactions(String accountId){
        return dataService.getAllTransactions(accountId);
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

    public List<Transaction> getAllBills(String accountId){
        return dataService.getAllBills(accountId);
    }

    public List<Transaction> getBill(String accountId, String id){
        return dataService.getBill(accountId, id);
    }

    public List<Transaction> addBill(Transaction transaction){
        return dataService.addBill(transaction);
    }

    public List<Transaction> deleteBill(String accountId, String id){
        return dataService.deleteBill(accountId, id);
    }

    public List<Transaction> editBill(Transaction bill){
        return dataService.editBill(bill);
    }

    public List<Transaction> getAllIncomes(){
        return dataService.getAllIncomes();
    }

    public List<Transaction> getIncome(String id){
        return dataService.getIncome(id);
    }

    public List<Transaction> addIncome(Transaction transaction){
        return dataService.addIncome(transaction);
    }

    public List<Transaction> deleteIncome(String id){
        return dataService.deleteIncome(id);
    }

    public List<Transaction> editIncome(Transaction income){
        return dataService.editIncome(income);
    }
}
