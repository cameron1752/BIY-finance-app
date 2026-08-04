package com.biy.finance.app.financeapp.data;

import com.biy.finance.app.financeapp.entity.TransactionsEntity;
import com.biy.finance.app.financeapp.model.Account;
import com.biy.finance.app.financeapp.model.Transaction;
import com.biy.finance.app.financeapp.repository.TransactionRepository;
import com.biy.finance.app.financeapp.service.SettingsService;
import com.biy.finance.app.financeapp.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class DataService {
    Account account = new Account();

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    SettingsService settingsService;


    public Account getAccounts(String id){
        return account;
    }

    // in the future this will connect to APIs or database or something cool
    public List<Transaction> getAllTransactions(String accountId, String type){

        List<TransactionsEntity> entities
                = transactionRepository.fetchAllByType(accountId, type);

        return entities.stream()
                .map(Transaction::new)
                .toList();
    }

    public List<Transaction> getTransaction(String accountId, String id){
        List<TransactionsEntity> entities =
                transactionRepository.fetchByType(accountId, id);

        return entities.stream()
                .map(Transaction::new)
                .toList();
    }

    public List<Transaction> addTransaction(Transaction transaction) {

        transactionRepository.save(transaction.toEntity(transaction.getType()));
        return getAllTransactions(transaction.getAccountId(), transaction.getType());
    }

    public List<Transaction> deleteTransaction(String accountId, String id) {
        List<Transaction> foundTransaction = getTransaction(accountId, id);
        transactionRepository.deleteById(foundTransaction.getFirst());
        return getAllTransactions(accountId, foundTransaction.getFirst().getType());
    }

    public List<Transaction> editTransaction(Transaction transaction) {
        log.info("Editing transaction {}", transaction.getId());
        List<Transaction> foundTransactions = getTransaction(transaction.getAccountId(), transaction.getId());

        log.info("Removing old transaction {}", foundTransactions.getFirst());
        // remove old un-updated bill
        deleteTransaction(foundTransactions.getFirst().getAccountId(), foundTransactions.getFirst().getId());

        log.info("Adding new transaction {}", transaction);
        // add new, updated bill
        addTransaction(foundTransactions.getFirst().updateFrom(transaction));

        return getAllTransactions(foundTransactions.getFirst().getAccountId(), foundTransactions.getFirst().getType());
    }
}
