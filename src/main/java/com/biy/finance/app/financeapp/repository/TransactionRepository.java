package com.biy.finance.app.financeapp.repository;

import com.biy.finance.app.financeapp.entity.TransactionsEntity;
import com.biy.finance.app.financeapp.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionsEntity, Transaction> {

    // Query by just one part of the composite key
    List<TransactionsEntity> findByAccountId(String accountId);

    // Query by both parts of the composite key
    List<TransactionsEntity> findByAccountIdAndId(String accountId, String id);

    // Query by a non-key column
    List<Transaction> findByPending(Boolean pending);
}
