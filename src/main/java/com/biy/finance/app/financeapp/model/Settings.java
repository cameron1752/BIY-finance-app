package com.biy.finance.app.financeapp.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Data
public class Settings {
    @Value("${app.use.default}")
    private boolean useDefault;

    private List<String> categories = new ArrayList<>();
    private String startPage;

    public Settings(){

        log.info("{}", useDefault);
        if (true){
            categories.add("Eating Out");
            categories.add("Groceries");
            categories.add("Fun");
            categories.add("Bills");

            startPage = "Transactions";
        }
    }

    public void addCategory(String category){
        categories.add(category);
    }
}
