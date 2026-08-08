package com.biy.finance.app.financeapp.entity;

import com.biy.finance.app.financeapp.model.Account;
import com.biy.finance.app.financeapp.model.Settings;
import com.biy.finance.app.financeapp.model.Transaction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "accounts.sql", schema = "test")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @Column(name = "provider_id", nullable = false, unique = true)
    private String providerId;

    @Column(name = "username")
    private String username;

    @Column(name = "name")
    private String name;

    @Column(name = "settings")
    private String settings;
}
