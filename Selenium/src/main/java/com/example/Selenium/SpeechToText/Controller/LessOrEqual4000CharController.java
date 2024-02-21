package com.example.Selenium.SpeechToText.Controller;

import com.example.Selenium.SpeechToText.Model.CSVStoreModel;
import com.example.Selenium.SpeechToText.Model.DataStoreModel;
import com.example.Selenium.SpeechToText.Model.TelegramDataStoreModel;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.CountDownLatch;


public class LessOrEqual4000CharController extends InitializationDriverController {


    public LessOrEqual4000CharController(CountDownLatch countDownLatch, String textFromCsvFile, WebDriver driver, TelegramDataStoreModel telegramDataStoreModel, DataStoreModel dataStoreModel, CSVStoreModel textCsvStoreModel, CSVStoreModel voiceCsvStoreModel, CSVStoreModel fileNameCsvStoreModel) {
        super(driver, telegramDataStoreModel, dataStoreModel, textCsvStoreModel, voiceCsvStoreModel, fileNameCsvStoreModel);
        this.dataStoreModel.countDownLatch = countDownLatch;
        this.textCsvStoreModel.readTextOfColumn = textFromCsvFile;
    }
}


class A implements Runnable {


    String eat = "eat";

    public A(String eat) {
        this.eat = eat;
    }

    @Override
    public void run() {
        System.out.println("run");
    }
}

class B extends A {

    public B(String eat) {
        super(eat);
    }
}

class fire {
    public static void main(String[] args) {
        Thread t = new Thread(new B("eat"));
        t.run();
    }
}