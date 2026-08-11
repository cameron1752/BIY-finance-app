package com.biy.finance.app.financeapp.model;

import com.biy.finance.app.financeapp.entity.AccountEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    public Account(AccountEntity accountEntity){
        this.id = accountEntity.getId();
        this.providerId = accountEntity.getProviderId();
        this.username = accountEntity.getUsername();
        this.name = accountEntity.getName();
        this.avatarUrl = accountEntity.getAvatarUrl();
        this.settings = accountEntity.getSettings();
    }

    private long id;
    private String providerId;
    private String username;
    private String name;
    private String avatarUrl;
    private String settings;
}
