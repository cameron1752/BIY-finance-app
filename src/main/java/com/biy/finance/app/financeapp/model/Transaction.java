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
        return (int) (100 * Math.random());
    }
}
