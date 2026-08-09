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
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AccountsService {

    @Autowired
    private AccountsRepository accountsRepository;

    public Account createOrUpdateAccount(String provider, OAuth2User oauthUser){

        switch (provider) {
            case "github":
                return createOrUpdateGithubAccount(oauthUser);
            case "google":
                return createOrUpdateGoogleAccount(oauthUser);
            default:
                throw new RuntimeException("Unknown provider");
        }

    }

    private Account createOrUpdateGoogleAccount(OAuth2User oauthUser){
        String googleId =
                oauthUser.getAttribute("sub").toString();

        Optional<AccountEntity> user = accountsRepository.findByProviderId(googleId);

        if (user.isEmpty()){
            log.info("New account being created");
            AccountEntity account = new AccountEntity();
            account.setAvatarUrl(oauthUser.getAttribute("picture"));
            account.setProviderId(googleId);
            account.setUsername(oauthUser.getAttribute("email"));
            account.setName(oauthUser.getAttribute("name"));
            return new Account(accountsRepository.save(account));
        } else {
            log.info("Here's the ole dog: {}", user.get());
            return new Account(user.get());
        }
    }

    private Account createOrUpdateGithubAccount(OAuth2User oauthUser){
        String githubId =
                oauthUser.getAttribute("id").toString();

        Optional<AccountEntity> user = accountsRepository.findByProviderId(githubId);

        if (user.isEmpty()){
            log.info("New account being created");
            AccountEntity account = new AccountEntity();
            account.setAvatarUrl(oauthUser.getAttribute("avatar_url"));
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

        // convert to token to get provider smh
        OAuth2AuthenticationToken token =
                (OAuth2AuthenticationToken) authentication;
        // get token provider
        String provider = token.getAuthorizedClientRegistrationId();

        // if github else if google
        String providerId = null;

        switch (provider){
            case "github":
                providerId = oauthUser.getAttribute("id").toString();
                break;
            case "google":
                providerId = oauthUser.getAttribute("sub").toString();
                break;
            default:
                throw new RuntimeException("Provider not found");
        }

        Optional<AccountEntity> accountEntity = accountsRepository.findByProviderId(providerId);

        if (accountEntity.isEmpty()){
            throw new RuntimeException("No user found");
        }
        return new Account(accountEntity.get());
    }
}

