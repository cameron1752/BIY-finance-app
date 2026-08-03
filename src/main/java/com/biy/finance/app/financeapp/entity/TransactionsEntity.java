package com.biy.finance.app.financeapp.entity;

import com.biy.finance.app.financeapp.model.Transaction;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Audited;

import java.time.LocalDate;

@Entity
@Table(name = "transactions", schema = "test")
@Data
@NoArgsConstructor
@IdClass(Transaction.class)
public class TransactionsEntity {
    @Id
    @Column(name = "account_id")
    private String accountId;

    @Id
    @Column(name = "transaction_id")
    private String id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "category")
    private String category;

    @Column(name = "description")
    private String description;

    @Column(name = "amount")
    private Double amount; // Maps to double precision

    @Column(name = "pending")
    private Boolean pending;
}
