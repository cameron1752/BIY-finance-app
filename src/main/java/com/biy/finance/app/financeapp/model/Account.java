package com.biy.finance.app.financeapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Account {
    private int id;
    private Settings settings;
    private List<Transaction> transactions;
    private List<Transaction> bills;
    private List<Transaction> incomes;
}
