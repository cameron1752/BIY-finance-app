package com.biy.finance.app.financeapp.data;

import com.biy.finance.app.financeapp.entity.TransactionsEntity;
import com.biy.finance.app.financeapp.model.Account;
import com.biy.finance.app.financeapp.model.Transaction;
import com.biy.finance.app.financeapp.repository.TransactionRepository;
import com.biy.finance.app.financeapp.service.AccountsService;
import com.biy.finance.app.financeapp.service.SettingsService;
import com.biy.finance.app.financeapp.util.Constants;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
public class DataService {
    @Autowired
    AccountsService accountsService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    SettingsService settingsService;


    // in the future this will connect to APIs or database or something cool
    public List<Transaction> getAllTransactions(long accountId, String type){

        Account account = accountsService.getCurrentAccount();

        List<TransactionsEntity> entities
                = transactionRepository.fetchAllByType(account.getId(), type);

        return entities.stream()
                .map(Transaction::new)
                .toList();
    }

    public List<Transaction> getTransaction(long accountId, String id){
        List<TransactionsEntity> entities =
                transactionRepository.fetchByType(accountId, id);

        return entities.stream()
                .map(Transaction::new)
                .toList();
    }

    public List<Transaction> addTransaction(List<Transaction> transactions, long accountId) {
        String type = transactions.getFirst().getType();

        for (Transaction transaction : transactions){
            transaction.setAccountId(accountId);
            transactionRepository.save(transaction.toEntity(transaction.getType()));
        }
        return getAllTransactions(accountId, type);
    }

    @Transactional
    public List<Transaction> deleteTransaction(long accountId, String id) {
        List<Transaction> foundTransaction = getTransaction(accountId, id);
        transactionRepository.deleteById(id);
        return getAllTransactions(accountId, foundTransaction.getFirst().getType());
    }

    public List<Transaction> editTransaction(List<Transaction> transactions, long accountId) {
        // list of updated transactions
        List<Transaction> updated = new ArrayList<>();
        String type = transactions.getFirst().getType();

        for (Transaction transaction : transactions){

            transaction.setAccountId(accountId);
            log.info("Editing transaction {}", transaction.getId());
            Transaction foundTransactions = getTransaction(accountId, transaction.getId()).getFirst();

            log.info("Removing old transaction {}", foundTransactions);
            // remove old un-updated bill
            deleteTransaction(accountId, foundTransactions.getId());

            log.info("Adding new transaction {}", transaction);
            updated.add(foundTransactions.updateFrom(transaction));
        }

        // add new, updated bill
        addTransaction(updated, accountId);

        return getAllTransactions(accountId, type);
    }
}
