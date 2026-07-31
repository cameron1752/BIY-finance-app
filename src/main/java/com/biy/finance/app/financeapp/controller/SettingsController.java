package com.biy.finance.app.financeapp.controller;


import com.biy.finance.app.financeapp.model.Settings;
import com.biy.finance.app.financeapp.service.SettingsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity getAllSettings(@RequestHeader(value = "traceId", required = true) String traceId){
        log.info("Getting settings");
        return ResponseEntity.ok(settingsService.getSettings());
    }

    @PatchMapping
    public ResponseEntity updateSettings(@RequestHeader(value = "traceId", required = true) String traceId,
                                        @RequestBody Settings settings){
        log.info("Updating settings to {}", settings);
        return ResponseEntity.ok(settingsService.setSettings(settings));
    }

    @DeleteMapping
    public ResponseEntity deleteSettings(@RequestHeader(value = "traceId", required = true) String traceId){
        log.info("Deleting settings and restoring to default");
        return ResponseEntity.ok(settingsService.deleteSettings());
    }

}
