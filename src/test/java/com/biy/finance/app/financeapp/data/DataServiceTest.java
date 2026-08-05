package com.biy.finance.app.financeapp.data;

import com.biy.finance.app.financeapp.entity.TransactionsEntity;
import com.biy.finance.app.financeapp.model.Account;
import com.biy.finance.app.financeapp.model.Transaction;
import com.biy.finance.app.financeapp.repository.TransactionRepository;
import com.biy.finance.app.financeapp.service.SettingsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DataServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private SettingsService settingsService;

    @InjectMocks
    private DataService dataService;

    private static final String ACCOUNT_ID = "acct-123";
    private static final String TRANSACTION_ID = "txn-1";
    private static final String TYPE = "TRANSACTION";

    private TransactionsEntity entity;
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        transaction = new Transaction();
        transaction.setId(TRANSACTION_ID);
        transaction.setAccountId(ACCOUNT_ID);
        transaction.setType(TYPE);
        transaction.setDate(LocalDate.now());
        transaction.setCategory("Groceries");
        transaction.setDescription("Trader Joe's");
        transaction.setAmount(50.00);
        transaction.setPending(false);
    }

    private TransactionsEntity mockEntity() {
        TransactionsEntity entity = mock(TransactionsEntity.class);
        when(entity.getId()).thenReturn(TRANSACTION_ID);
        when(entity.getAccountId()).thenReturn(ACCOUNT_ID);
        when(entity.getType()).thenReturn(TYPE);
        when(entity.getDate()).thenReturn(LocalDate.now());
        when(entity.getCategory()).thenReturn("Groceries");
        when(entity.getDescription()).thenReturn("Trader Joe's");
        when(entity.getAmount()).thenReturn(50.00);
        when(entity.getPending()).thenReturn(false);
        return entity;
    }

    @Test
    void getAccounts_returnsAccountInstance() {
        Account result = dataService.getAccounts(ACCOUNT_ID);

        assertThat(result).isNotNull();
    }

    @Test
    void getAllTransactions_returnsMappedList() {
        TransactionsEntity entity = mockEntity();
        when(transactionRepository.fetchAllByType(ACCOUNT_ID, TYPE))
                .thenReturn(List.of(entity));

        List<Transaction> result = dataService.getAllTransactions(ACCOUNT_ID, TYPE);

        assertThat(result).hasSize(1);
        verify(transactionRepository).fetchAllByType(ACCOUNT_ID, TYPE);
    }

    @Test
    void getAllTransactions_emptyRepository_returnsEmptyList() {
        when(transactionRepository.fetchAllByType(ACCOUNT_ID, TYPE))
                .thenReturn(List.of());

        List<Transaction> result = dataService.getAllTransactions(ACCOUNT_ID, TYPE);

        assertThat(result).isEmpty();
    }

    @Test
    void getTransaction_returnsMappedList() {
        TransactionsEntity entity = mockEntity();
        when(transactionRepository.fetchByType(ACCOUNT_ID, TRANSACTION_ID))
                .thenReturn(List.of(entity));

        List<Transaction> result = dataService.getTransaction(ACCOUNT_ID, TRANSACTION_ID);

        assertThat(result).hasSize(1);
        verify(transactionRepository).fetchByType(ACCOUNT_ID, TRANSACTION_ID);
    }

    @Test
    void addTransaction_savesAndReturnsAllTransactions() {
        TransactionsEntity entity = mockEntity();
        when(transactionRepository.fetchAllByType(ACCOUNT_ID, TYPE))
                .thenReturn(List.of(entity));

        List<Transaction> result = dataService.addTransaction(transaction);

        verify(transactionRepository).save(any(TransactionsEntity.class));
        assertThat(result).hasSize(1);
    }

    @Test
    void deleteTransaction_deletesAndReturnsRemainingTransactions() {
        TransactionsEntity entity = mockEntity();
        when(transactionRepository.fetchByType(ACCOUNT_ID, TRANSACTION_ID))
                .thenReturn(List.of(entity));
        when(transactionRepository.fetchAllByType(eq(ACCOUNT_ID), anyString()))
                .thenReturn(List.of());

        List<Transaction> result = dataService.deleteTransaction(ACCOUNT_ID, TRANSACTION_ID);

        verify(transactionRepository).deleteById(any());
        assertThat(result).isEmpty();
    }

    @Test
    void editTransaction_deletesOldAndAddsUpdatedTransaction() {
        TransactionsEntity entity = mockEntity();
        when(transactionRepository.fetchByType(ACCOUNT_ID, TRANSACTION_ID))
                .thenReturn(List.of(entity));
        when(transactionRepository.fetchAllByType(eq(ACCOUNT_ID), anyString()))
                .thenReturn(List.of(entity));

        List<Transaction> result = dataService.editTransaction(transaction);

        verify(transactionRepository).deleteById(any());
        verify(transactionRepository).save(any(TransactionsEntity.class));
        assertThat(result).hasSize(1);
    }
}