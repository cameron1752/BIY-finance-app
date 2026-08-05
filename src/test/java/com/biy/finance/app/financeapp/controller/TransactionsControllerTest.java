package com.biy.finance.app.financeapp.controller;

import com.biy.finance.app.financeapp.model.Transaction;
import com.biy.finance.app.financeapp.service.TransactionsService;
import com.biy.finance.app.financeapp.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Notes:
 * - Uses @WebMvcTest so only the web layer is loaded; TransactionsService is mocked.
 * - Assumes Transaction is a simple POJO (e.g. Lombok @Data) with a no-arg constructor,
 *   so an empty JSON body "{}" deserializes without error for POST/PATCH tests.
 * - Missing required headers (traceId, accountId, id where required=true) are handled by
 *   Spring itself and result in HTTP 400 before the controller method body executes.
 * - Since the controller has no @ExceptionHandler / @ControllerAdvice, an unhandled
 *   exception thrown by the service bubbles up as an HTTP 500 in these tests.
 */
@WebMvcTest(TransactionsController.class)
class TransactionsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TransactionsService transactionsService;

    private static final String BASE_URL = "/v1/transactions";
    private static final String TRACE_ID = "trace-123";
    private static final String ACCOUNT_ID = "account-456";
    private static final String TRANSACTION_ID = "txn-789";

    private Transaction transaction;

    @BeforeEach
    void setUp() {
        transaction = new Transaction();
        transaction.setAccountId("123abc456def");
        transaction.setCategory("test");
        transaction.setDate(LocalDate.now());
    }

    // ---------------------------------------------------------------------
    // GET /v1/transactions
    // ---------------------------------------------------------------------

    @Test
    void getTransactions_withoutId_returnsAllTransactionsForAccount() throws Exception {
        List<Transaction> transactions = Collections.singletonList(transaction);
        when(transactionsService.getAllTransactions(ACCOUNT_ID, Constants.TRANSACTION)).thenReturn(transactions);

        mockMvc.perform(get(BASE_URL)
                        .header("traceId", TRACE_ID)
                        .header("accountId", ACCOUNT_ID))
                .andExpect(status().isOk());

        verify(transactionsService, times(1)).getAllTransactions(ACCOUNT_ID, Constants.TRANSACTION);
        verify(transactionsService, never()).getTransaction(anyString(), anyString());
    }

    @Test
    void getTransactions_withId_returnsSingleTransaction() throws Exception {
        when(transactionsService.getTransaction(ACCOUNT_ID, TRANSACTION_ID)).thenReturn(Collections.singletonList(transaction));

        mockMvc.perform(get(BASE_URL)
                        .header("id", TRANSACTION_ID)
                        .header("traceId", TRACE_ID)
                        .header("accountId", ACCOUNT_ID))
                .andExpect(status().isOk());

        verify(transactionsService, times(1)).getTransaction(ACCOUNT_ID, TRANSACTION_ID);
        verify(transactionsService, never()).getAllTransactions(anyString(), anyString());
    }

    @Test
    void getTransactions_missingTraceId_returnsBadRequest() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .header("accountId", ACCOUNT_ID))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(transactionsService);
    }

    @Test
    void getTransactions_missingAccountId_returnsBadRequest() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .header("traceId", TRACE_ID))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(transactionsService);
    }

    @Test
    void getTransactions_serviceThrowsException_returnsServerError() throws Exception {
        when(transactionsService.getAllTransactions(ACCOUNT_ID, Constants.TRANSACTION))
                .thenThrow(new RuntimeException("boom"));

        mockMvc.perform(get(BASE_URL)
                        .header("traceId", TRACE_ID)
                        .header("accountId", ACCOUNT_ID))
                .andExpect(status().isInternalServerError());
    }

    // ---------------------------------------------------------------------
    // POST /v1/transactions
    // ---------------------------------------------------------------------

    @Test
    void addTransaction_validRequest_returnsCreatedTransaction() throws Exception {
        when(transactionsService.addTransaction(any(Transaction.class))).thenReturn(Collections.singletonList(transaction));

        mockMvc.perform(post(BASE_URL)
                        .header("traceId", TRACE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaction)))
                .andExpect(status().isOk());

        verify(transactionsService, times(1)).addTransaction(any(Transaction.class));
    }

    @Test
    void addTransaction_missingTraceId_returnsBadRequest() throws Exception {
        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaction)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(transactionsService);
    }

    @Test
    void addTransaction_malformedBody_returnsBadRequest() throws Exception {
        mockMvc.perform(post(BASE_URL)
                        .header("traceId", TRACE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("not-valid-json"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(transactionsService);
    }

    @Test
    void addTransaction_serviceThrowsException_returnsServerError() throws Exception {
        when(transactionsService.addTransaction(any(Transaction.class)))
                .thenThrow(new RuntimeException("boom"));

        mockMvc.perform(post(BASE_URL)
                        .header("traceId", TRACE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaction)))
                .andExpect(status().isInternalServerError());
    }

    // ---------------------------------------------------------------------
    // DELETE /v1/transactions
    // ---------------------------------------------------------------------

    @Test
    void deleteTransaction_validRequest_returnsOk() throws Exception {
        when(transactionsService.deleteTransaction(ACCOUNT_ID, TRANSACTION_ID)).thenReturn(Collections.singletonList(transaction));

        mockMvc.perform(delete(BASE_URL)
                        .header("traceId", TRACE_ID)
                        .header("id", TRANSACTION_ID)
                        .header("accountId", ACCOUNT_ID))
                .andExpect(status().isOk());

        verify(transactionsService, times(1)).deleteTransaction(ACCOUNT_ID, TRANSACTION_ID);
    }

    @Test
    void deleteTransaction_missingId_returnsBadRequest() throws Exception {
        mockMvc.perform(delete(BASE_URL)
                        .header("traceId", TRACE_ID)
                        .header("accountId", ACCOUNT_ID))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(transactionsService);
    }

    @Test
    void deleteTransaction_missingAccountId_returnsBadRequest() throws Exception {
        mockMvc.perform(delete(BASE_URL)
                        .header("traceId", TRACE_ID)
                        .header("id", TRANSACTION_ID))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(transactionsService);
    }

    @Test
    void deleteTransaction_serviceThrowsException_returnsServerError() throws Exception {
        when(transactionsService.deleteTransaction(ACCOUNT_ID, TRANSACTION_ID))
                .thenThrow(new RuntimeException("boom"));

        status();

        mockMvc.perform(delete(BASE_URL)
                        .header("traceId", TRACE_ID)
                        .header("id", TRANSACTION_ID)
                        .header("accountId", ACCOUNT_ID))
                .andExpect(status().isInternalServerError());
    }

    // ---------------------------------------------------------------------
    // PATCH /v1/transactions
    // ---------------------------------------------------------------------

    @Test
    void editTransaction_validRequest_returnsUpdatedTransaction() throws Exception {
        when(transactionsService.editTransaction(any(Transaction.class))).thenReturn(Collections.singletonList(transaction));

        mockMvc.perform(patch(BASE_URL)
                        .header("traceId", TRACE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaction)))
                .andExpect(status().isOk());

        verify(transactionsService, times(1)).editTransaction(any(Transaction.class));
    }

    @Test
    void editTransaction_missingTraceId_returnsBadRequest() throws Exception {
        mockMvc.perform(patch(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaction)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(transactionsService);
    }

    @Test
    void editTransaction_serviceThrowsException_returnsServerError() throws Exception {
        when(transactionsService.editTransaction(any(Transaction.class)))
                .thenThrow(new RuntimeException("boom"));

        mockMvc.perform(patch(BASE_URL)
                        .header("traceId", TRACE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaction)))
                .andExpect(status().isInternalServerError());
    }
}