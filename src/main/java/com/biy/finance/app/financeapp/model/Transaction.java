package com.biy.finance.app.financeapp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Transaction {
    private int id;
    private String date;
    private String category;
    private String description;
    private double amount;
    private boolean pending;

    @JsonCreator
    public Transaction(
            @JsonProperty("date") String date,
            @JsonProperty("category") String category,
            @JsonProperty("description") String description,
            @JsonProperty("amount") double amount,
            @JsonProperty("pending") boolean pending
    ) {
        this.date = date;
        this.category = category;
        this.description = description;
        this.amount = amount;
        this.pending = pending;
        this.id = generateId();
    }
    // todo: need to make it unique and to support more than 100 transactions
    private int generateId(){
        return (int) ((100 * Math.random()) * (100 * Math.random()));
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

        return this;
    }
}
