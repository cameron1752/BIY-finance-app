package com.biy.finance.app.financeapp.service;

import com.biy.finance.app.financeapp.data.DataService;
import com.biy.finance.app.financeapp.entity.AccountEntity;
import com.biy.finance.app.financeapp.model.Account;
import com.biy.finance.app.financeapp.model.Settings;
import com.biy.finance.app.financeapp.repository.AccountsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AccountsService {

    @Autowired
    private AccountsRepository accountsRepository;

    public Account createOrUpdateAccount(OAuth2User oauthUser){
        String githubId =
                oauthUser.getAttribute("id").toString();

        Optional<AccountEntity> user = accountsRepository.findByProviderId(githubId);

        if (user.isEmpty()){
            log.info("New account being created");
            AccountEntity account = new AccountEntity();
            account.setProviderId(githubId);
            account.setUsername(oauthUser.getAttribute("login"));
            account.setName(oauthUser.getAttribute("name"));
            return new Account(accountsRepository.save(account));
        } else {
            log.info("Here's the ole dog: {}", user.get());
            return new Account(user.get());
        }
    }

    public Account getCurrentAccount(){
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated()) {
            throw new RuntimeException("No authenticated user");
        }

        OAuth2User oauthUser =
                (OAuth2User) authentication.getPrincipal();

        String githubId =
                oauthUser.getAttribute("id").toString();

        Optional<AccountEntity> accountEntity = accountsRepository.findByProviderId(githubId);

        if (accountEntity.isEmpty()){
            throw new RuntimeException("No user found");
        }
        return new Account(accountEntity.get());
    }
}

