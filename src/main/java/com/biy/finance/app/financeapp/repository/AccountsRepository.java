package com.biy.finance.app.financeapp.repository;

import com.biy.finance.app.financeapp.entity.AccountEntity;
import com.biy.finance.app.financeapp.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<AccountEntity, Account> {

    Optional<AccountEntity> findByProviderId(String providerId);
}
