package com.biy.finance.app.financeapp.controller;


import com.biy.finance.app.financeapp.data.Settings;
import com.biy.finance.app.financeapp.service.SettingsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Controller
@RequestMapping("/v1/settings")
public class SettingsController {

    @Autowired
    SettingsService settingsService;

    @GetMapping
    public ResponseEntity getAllSettings(){
        log.info("Getting settings");
        return ResponseEntity.ok(settingsService.getSettings());
    }

    @PatchMapping
    public ResponseEntity updateSettings(@RequestBody Settings settings){
        log.info("Updating settings to {}", settings);
        return ResponseEntity.ok(settingsService.setSettings(settings));
    }

    @DeleteMapping
    public ResponseEntity deleteSettings(){
        log.info("Deleting settings and restoring to default");
        return ResponseEntity.ok(settingsService.deleteSettings());
    }

}
