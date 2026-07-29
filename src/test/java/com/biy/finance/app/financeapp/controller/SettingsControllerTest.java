package com.biy.finance.app.financeapp.controller;

import com.biy.finance.app.financeapp.data.Settings;
import com.biy.finance.app.financeapp.service.SettingsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
public class SettingsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    SettingsService settingsService;

    Settings settings = new Settings();
    Settings updatedSettings = new Settings();

    @Test
    public void getAllSettingsTest() throws Exception {
        when(settingsService.getSettings()).thenReturn(settings);

        this.mockMvc.perform(get("/v1/settings"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Transactions")))
                .andExpect(content().string(containsString("Eating Out")))
                .andExpect(content().string(containsString("Groceries")))
                .andExpect(content().string(containsString("Fun")))
                .andExpect(content().string(containsString("Bills")));
    }

    @Test
    public void updateSettingsTest() throws Exception {
        updatedSettings.setStartPage("Balances");
        updatedSettings.addCategory("Health");
        updatedSettings.addCategory("Travel");

        when(settingsService.setSettings(settings)).thenReturn(updatedSettings);

        this.mockMvc.perform(patch("/v1/settings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(updatedSettings)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Transactions")))
                .andExpect(content().string(containsString("Eating Out")))
                .andExpect(content().string(containsString("Groceries")))
                .andExpect(content().string(containsString("Fun")))
                .andExpect(content().string(containsString("Bills")))
                .andExpect(content().string(containsString("Health")))
                .andExpect(content().string(containsString("Travel")));
    }
}
