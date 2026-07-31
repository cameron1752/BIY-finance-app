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
    List<Transaction> bills = new ArrayList<>();

    @PostConstruct
    public void init(){
        fillTransactions();
        fillBills();
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

    public List<Transaction> addTransaction(Transaction transaction) {
        transactions.add(transaction);
        return transactions;
    }

    public List<Transaction> deleteTransaction(Integer id) {
        Transaction transaction = getTransaction(id).getFirst();
        transactions.remove(transaction);
        return getAllTransactions();
    }

    public List<Transaction> editTransaction(Transaction transaction) {
        log.info("Editing transaction {}", transaction.getId());
        List<Transaction> foundTransactions = getTransaction(transaction.getId());

        log.info("Removing old transaction {}", foundTransactions.getFirst());
        // remove old un-updated bill
        transactions.remove(foundTransactions.getFirst());

        log.info("Adding new transaction {}", transaction);
        // add new, updated bill
        bills.add(foundTransactions.getFirst().updateFrom(transaction));

        return getAllBills();
    }

    public List<Transaction> getAllBills(){
        return bills;
    }

    public List<Transaction> getBill(int id){
        Optional<Transaction> found = bills.stream().filter(
                t -> t.getId() == (id)).findFirst();

        return found.stream().toList();
    }

    public List<Transaction> addBill(Transaction bill) {
        bills.add(bill);
        return bills;
    }

    public List<Transaction> deleteBill(int id) {
        Transaction bill = getBill(id).getFirst();
        bills.remove(bill);
        return getAllBills();
    }

    public List<Transaction> editBill(Transaction bill){
        log.info("Editing bill {}", bill.getId());
        List<Transaction> foundBills = getBill(bill.getId());

        log.info("Removing old bill {}", foundBills.getFirst());
        // remove old un-updated bill
        bills.remove(foundBills.getFirst());

        log.info("Adding new bill {}", bill);
        // add new, updated bill
        bills.add(foundBills.getFirst().updateFrom(bill));

        return getAllBills();
    }


    // temp data
    private void fillTransactions(){
        transactions.add(new Transaction("2024-01-05", "Groceries", "Whole Foods Market", 84.32, false));
        transactions.add(new Transaction("2024-01-07", "Eating Out", "Chipotle", 12.75, false));
        transactions.add(new Transaction("2024-01-10", "Bills", "Comcast Internet", 79.99, true));
        transactions.add(new Transaction("2024-01-12", "Fun", "AMC Theatres", 22.50, false));
        transactions.add(new Transaction("2024-01-15", "Health", "CVS Pharmacy", 45.00, true));
        transactions.add(new Transaction("2024-01-18", "Travel", "Delta Airlines", 342.10, true));
        transactions.add(new Transaction("2024-01-20", "Groceries", "Trader Joe's", 56.47, false));
        transactions.add(new Transaction("2024-01-22", "Bills", "Con Edison", 132.88, false));
        transactions.add(new Transaction("2024-01-25", "Fun", "Steam Store", 19.99, false));
        transactions.add(new Transaction("2024-01-28", "Health", "Planet Fitness", 24.99, true));
    }

    // temp data
    private void fillBills(){
        bills.add(new Transaction("2024-01-18", "Essential", "Rent", 2600.00, true));
        bills.add(new Transaction("2024-01-20", "Loan", "Car Note", 400.00, false));
        bills.add(new Transaction("2024-01-22", "Subscription", "Netflix", 26.99, false));
        bills.add(new Transaction("2024-01-25", "Essential", "Electric", 105.32, false));

    }
}
