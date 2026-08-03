package com.biy.finance.app.financeapp.data;

import com.biy.finance.app.financeapp.entity.TransactionsEntity;
import com.biy.finance.app.financeapp.model.Account;
import com.biy.finance.app.financeapp.model.Transaction;
import com.biy.finance.app.financeapp.repository.TransactionRepository;
import com.biy.finance.app.financeapp.service.SettingsService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
public class DataService {
    List<Transaction> transactions = new ArrayList<>();
    List<Transaction> bills = new ArrayList<>();
    List<Transaction> incomes = new ArrayList<>();
    Account account = new Account();

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    SettingsService settingsService;

    @PostConstruct
    public void init(){
        fillTransactions();
        fillBills();
        fillIncomes();
        fillAccount();
    }

    public Account getAccounts(String id){
        return account;
    }

    // in the future this will connect to APIs or database or something cool
    public List<Transaction> getAllTransactions(){

        List<TransactionsEntity> entities = transactionRepository.findByAccountId("2038010339");

        return entities.stream()
                .map(Transaction::new)
                .toList();
    }

    public List<Transaction> getTransaction(String id){
        List<TransactionsEntity> entities =
                transactionRepository.findByAccountIdAndId("2038010339", id);

        return entities.stream()
                .map(Transaction::new)
                .toList();
    }

    public List<Transaction> addTransaction(Transaction transaction) {

        transactionRepository.save(transaction.toEntity());
        return getAllTransactions();
    }

    public List<Transaction> deleteTransaction(String id) {
        transactionRepository.deleteById(getTransaction(id).getFirst());
        return getAllTransactions();
    }

    public List<Transaction> editTransaction(Transaction transaction) {
        log.info("Editing transaction {}", transaction.getId());
        List<Transaction> foundTransactions = getTransaction(transaction.getId());

        log.info("Removing old transaction {}", foundTransactions.getFirst());
        // remove old un-updated bill
        deleteTransaction(foundTransactions.getFirst().getId());

        log.info("Adding new transaction {}", transaction);
        // add new, updated bill
        addTransaction(foundTransactions.getFirst().updateFrom(transaction));

        return getAllTransactions();
    }

    public List<Transaction> getAllBills(){
        return bills;
    }

    public List<Transaction> getBill(String id){
        Optional<Transaction> found = bills.stream().filter(
                t -> Objects.equals(t.getId(), id)).findFirst();

        return found.stream().toList();
    }

    public List<Transaction> addBill(Transaction bill) {
        bills.add(bill);
        return bills;
    }

    public List<Transaction> deleteBill(String id) {
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

    public List<Transaction> getAllIncomes(){
        return incomes;
    }

    public List<Transaction> getIncome(String id){
        Optional<Transaction> found = incomes.stream().filter(
                t -> Objects.equals(t.getId(), id)).findFirst();

        return found.stream().toList();
    }

    public List<Transaction> addIncome(Transaction income) {
        incomes.add(income);
        return incomes;
    }

    public List<Transaction> deleteIncome(String id) {
        Transaction income = getIncome(id).getFirst();
        incomes.remove(income);
        return getAllIncomes();
    }

    public List<Transaction> editIncome(Transaction income){
        log.info("Editing income {}", income.getId());
        List<Transaction> foundIncomes = getIncome(income.getId());

        log.info("Removing old income {}", foundIncomes.getFirst());
        // remove old un-updated bill
        incomes.remove(foundIncomes.getFirst());

        log.info("Adding new income {}", income);
        // add new, updated bill
        incomes.add(foundIncomes.getFirst().updateFrom(income));

        return getAllIncomes();
    }

    private void fillAccount(){
        account.setId("2038010339");
        account.setTransactions(transactions);
        account.setBills(bills);
        account.setIncomes(incomes);
        account.setSettings(settingsService.getSettings());
    }

    // temp data
    private void fillTransactions(){
        transactions.add(new Transaction(LocalDate.parse("2024-01-05"), "Groceries", "Whole Foods Market", 84.32, false));
        transactions.add(new Transaction(LocalDate.parse("2024-01-07"), "Eating Out", "Chipotle", 12.75, false));
        transactions.add(new Transaction(LocalDate.parse("2024-01-10"), "Bills", "Comcast Internet", 79.99, true));
        transactions.add(new Transaction(LocalDate.parse("2024-01-12"), "Fun", "AMC Theatres", 22.50, false));
        transactions.add(new Transaction(LocalDate.parse("2024-01-15"), "Health", "CVS Pharmacy", 45.00, true));
        transactions.add(new Transaction(LocalDate.parse("2024-01-18"), "Travel", "Delta Airlines", 342.10, true));
        transactions.add(new Transaction(LocalDate.parse("2024-01-20"), "Groceries", "Trader Joe's", 56.47, false));
        transactions.add(new Transaction(LocalDate.parse("2024-01-22"), "Bills", "Con Edison", 132.88, false));
        transactions.add(new Transaction(LocalDate.parse("2024-01-25"), "Fun", "Steam Store", 19.99, false));
        transactions.add(new Transaction(LocalDate.parse("2024-01-28"), "Health", "Planet Fitness", 24.99, true));
    }

    // temp data
    private void fillBills(){
        bills.add(new Transaction(LocalDate.parse("2024-01-18"), "Essential", "Rent", 2600.00, true));
        bills.add(new Transaction(LocalDate.parse("2024-01-20"), "Loan", "Car Note", 400.00, false));
        bills.add(new Transaction(LocalDate.parse("2024-01-22"), "Subscription", "Netflix", 26.99, false));
        bills.add(new Transaction(LocalDate.parse("2024-01-25"), "Essential", "Electric", 105.32, false));
    }

    // temp data
    private void fillIncomes(){
        incomes.add(new Transaction(LocalDate.parse("2024-01-01"), "Income", "Farm Stand Check", 3500.00, false));
        incomes.add(new Transaction(LocalDate.parse("2024-01-15"), "Income", "Farm Stand Check", 3500.00, true));
        incomes.add(new Transaction(LocalDate.parse("2024-01-01"), "Income", "Other Check", 4500.00, false));
        incomes.add(new Transaction(LocalDate.parse("2024-01-15"), "Income", "Other Check", 4500.00, true));

    }
}
