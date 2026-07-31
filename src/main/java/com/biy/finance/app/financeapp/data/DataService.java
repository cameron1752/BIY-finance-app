package com.biy.finance.app.financeapp.data;

import com.biy.finance.app.financeapp.model.Transaction;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DataService {
    List<Transaction> transactions = new ArrayList<>();

    @PostConstruct
    public void init(){
        fillTransactions();
    }

    // in the future this will connect to APIs or database or something cool
    public List<Transaction> getAllTransactions(){
        return transactions;
    }

    public List<Transaction> getTransaction(int id){
        Optional<Transaction> found = transactions.stream().filter(
                t -> t.getId() == (id)).findFirst();

        return found.stream().toList();
    }

    // temp data
    private void fillTransactions(){
        transactions.add(new Transaction(1,"2024-01-05", "Groceries", "Whole Foods Market", 84.32, false));
        transactions.add(new Transaction(2,"2024-01-07", "Eating Out", "Chipotle", 12.75, false));
        transactions.add(new Transaction(3,"2024-01-10", "Bills", "Comcast Internet", 79.99, true));
        transactions.add(new Transaction(4,"2024-01-12", "Fun", "AMC Theatres", 22.50, false));
        transactions.add(new Transaction(5,"2024-01-15", "Health", "CVS Pharmacy", 45.00, true));
        transactions.add(new Transaction(6,"2024-01-18", "Travel", "Delta Airlines", 342.10, true));
        transactions.add(new Transaction(7,"2024-01-20", "Groceries", "Trader Joe's", 56.47, false));
        transactions.add(new Transaction(8,"2024-01-22", "Bills", "Con Edison", 132.88, false));
        transactions.add(new Transaction(9,"2024-01-25", "Fun", "Steam Store", 19.99, false));
        transactions.add(new Transaction(10,"2024-01-28", "Health", "Planet Fitness", 24.99, true));
    }

    public List<Transaction> addTransaction(Transaction transaction) {
        transactions.add(transaction);
        return transactions;
    }

    public List<Transaction> deleteTransaction(Integer id) {
        Transaction transaction = getTransaction(id).getFirst();
        transactions.remove(transaction);
        return getAllTransactions();
    }
}
