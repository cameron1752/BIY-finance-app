package com.biy.finance.app.financeapp.model;

import com.biy.finance.app.financeapp.entity.TransactionsEntity;
import com.biy.finance.app.financeapp.util.ValidTransaction;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@ValidTransaction
public class Transaction {
    private long accountId;
    private String id;
    private LocalDate date;
    private String category;
    private String description;
    private double amount;
    private boolean pending;
    private String type;

    public Transaction(TransactionsEntity transactionEntity){
        this.accountId = transactionEntity.getAccountId();
        this.id = transactionEntity.getId();
        this.date = transactionEntity.getDate();
        this.category = transactionEntity.getCategory();
        this.description = transactionEntity.getDescription();
        this.amount = transactionEntity.getAmount();
        this.pending = transactionEntity.getPending();
        this.type = transactionEntity.getType();
    }

    @JsonCreator
    public Transaction(
            @JsonProperty("id") String id,
            @JsonProperty("accountId") long accountId,
            @JsonProperty("date") LocalDate date,
            @JsonProperty("category") String category,
            @JsonProperty("description") String description,
            @JsonProperty("amount") double amount,
            @JsonProperty("pending") boolean pending,
            @JsonProperty("type") String type
    ) {
        this.date = date;
        this.category = category;
        this.description = description;
        this.amount = amount;
        this.pending = pending;
        this.id = id;
        this.accountId = accountId;
        this.type = type;
    }
    // todo: need to make it unique and to support more than 100 transactions
    private String generateId(){
        return Integer.toString((int) ((100 * Math.random()) * (100 * Math.random())));
    }

    public Transaction updateFrom(Transaction other) {
        if (other.date != null && !other.date.equals(this.date)) {
            this.date = other.date;
        }
        if (other.category != null && !other.category.equals(this.category)) {
            this.category = other.category;
        }
        if (other.description != null && !other.description.equals(this.description)) {
            this.description = other.description;
        }
        if (other.amount != this.amount) {
            this.amount = other.amount;
        }
        if (other.pending != this.pending) {
            this.pending = other.pending;
        }
        if (other.type != null && !other.category.equals(this.type)){
            this.type = other.type;
        }

        this.id = other.id;

        return this;
    }

    public TransactionsEntity toEntity(String type){
        String id;

        if (!StringUtils.hasText(this.id)){
            id = "tx_" + generateId();
        } else {
            id = this.id;
        }

        return new TransactionsEntity(
                this.accountId,
                id,
                this.date,
                this.category,
                this.description,
                this.amount,
                this.pending,
                type
        );
    }
}
