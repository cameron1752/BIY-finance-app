package com.biy.finance.app.financeapp.repository;

import com.biy.finance.app.financeapp.entity.TransactionsEntity;
import com.biy.finance.app.financeapp.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionsEntity, Transaction> {

    // Query by just one part of the composite key
    List<TransactionsEntity> findByAccountId(String accountId, String type);

    @Query(value = "SELECT t FROM TransactionsEntity t " +
            "WHERE t.accountId = :account_id AND " +
            "t.type = :transaction_type")
    List<TransactionsEntity> fetchAllByType(@Param("account_id") String accountId,
                                         @Param("transaction_type") String type);

    @Query(value = "SELECT t FROM TransactionsEntity t " +
            "WHERE t.accountId = :account_id AND " +
            "t.id = :transaction_id ")
    List<TransactionsEntity> fetchByType(@Param("account_id") String accountId,
                                         @Param("transaction_id") String id);

    // Query by a non-key column
    List<Transaction> findByPending(Boolean pending, String type);
}
