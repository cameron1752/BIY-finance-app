package com.biy.finance.app.financeapp.service;

import com.biy.finance.app.financeapp.model.Settings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SettingsService {
    // singleton so settings are consistent throughout instances
    Settings settings = new Settings();

    // return settings
    public Settings getSettings(){
        log.info("Settings service getting settings");
        return settings;
    }

    // edit settings
    public Settings setSettings(Settings newSettings){
        log.info("Settings service updating settings");
        settings.setCategories(newSettings.getCategories());
        settings.setStartPage(newSettings.getStartPage());

        return settings;
    }

    // delete settings (restore to default)
    public Settings deleteSettings(){
        settings = new Settings();
        return settings;
    }
}
