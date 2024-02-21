package com.example.Selenium.SpeechToText.Controller;

import com.example.Selenium.SpeechToText.Model.CSVStoreModel;
import com.example.Selenium.SpeechToText.Model.DataStoreModel;
import com.example.Selenium.SpeechToText.Model.TelegramDataStoreModel;
import org.openqa.selenium.WebDriver;

public class MoreOrEqual4001CharController extends InitializationDriverController {
    public MoreOrEqual4001CharController(WebDriver driver, TelegramDataStoreModel telegramDataStoreModel, DataStoreModel dataStoreModel, CSVStoreModel textCsvStoreModel, CSVStoreModel voiceCsvStoreModel, CSVStoreModel fileNameCsvStoreModel) {
        super(driver, telegramDataStoreModel, dataStoreModel, textCsvStoreModel, voiceCsvStoreModel, fileNameCsvStoreModel);
    }
}
