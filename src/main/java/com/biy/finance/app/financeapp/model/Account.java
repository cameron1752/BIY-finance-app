package com.biy.finance.app.financeapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private String id;
    private Settings settings;
    private List<Transaction> transactions;
    private List<Transaction> bills;
    private List<Transaction> incomes;
}
