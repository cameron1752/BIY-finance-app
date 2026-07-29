package com.biy.finance.app.financeapp.model;

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
}
