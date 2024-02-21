package com.example.Selenium.SpeechToText.Controller;

import com.example.Selenium.Package02.Selenium;
import com.example.Selenium.SpeechToText.Model.*;
import com.example.Selenium.SpeechToText.View.Response;
import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import static com.example.Selenium.Package02.ReadFileNameCSV.FileNameCSV;
import static com.example.Selenium.Package02.ReadVoiceCSV.VoiceCSV;

public class AllProcessController {

    public DataStoreModel dataStoreModel;
    public DataStoreModel dataStoreModelSecond;
    CSVStoreModel voiceCSVModel;
    CSVStoreModel fileNameCSVModel;

    CSVStoreModel textCSVModel;
    TelegramDataStoreModel telegramDataStoreModel;
    protected final String columnName1 = "Text";
    protected final String columnName2 = "Voice";
    protected final String columnName3 = "FileName";


    public void work() throws InterruptedException {

        textCSVModel = new CSVStoreModel();
        voiceCSVModel = new CSVStoreModel();
        fileNameCSVModel = new CSVStoreModel();

        dataStoreModel = new DataStoreModel();

        dataStoreModel.addStatus(EnumController.APPLICATION_STATUS_OK);

        GetDataCSVModel getDataCSVModel = new GetDataCSVModel(voiceCSVModel, fileNameCSVModel, textCSVModel, dataStoreModel);

        getDataCSVModel.getDataFromColumn(columnName1, columnName2, columnName3);

        getDataCSVModel.getNotificationErrorCSV(voiceCSVModel, fileNameCSVModel, dataStoreModel);

        if (dataStoreModel.getStatusEnumSet().contains(EnumController.ERROR_IN_CSV)) {
            Response response = new Response();
            response.SpeechToText(dataStoreModel.getNotification());
        }

        GetChunksToArrayList getChunksToArrayListClass = new GetChunksToArrayList();
        getChunksToArrayListClass.getChunksToArrayList(dataStoreModel.getArrayList_Char(), textCSVModel.getReadTextOfColumn(), dataStoreModel.getLimitChar());

        if (textCSVModel.isFlag()) {
            if (dataStoreModel.getArrayList_Char().size() == 1) {
                dataStoreModel.setCountDownLatch(new CountDownLatch(1));
                WebDriver driver1 = new ChromeDriver();
                Thread thread1 = new Thread(new LessOrEqual4000CharController(dataStoreModel.getCountDownLatch(), dataStoreModel.getArrayList_Char().get(0), driver1, telegramDataStoreModel, dataStoreModel, textCSVModel, voiceCSVModel, fileNameCSVModel));
                thread1.start();
            } else if (dataStoreModel.getArrayList_Char().size() >= 2) {
                //    dataStoreModelSecond = new DataStoreModel(dataStoreModel);
                //  dataStoreModelSecond.setCountDownLatch(new CountDownLatch(1));
                WebDriver driver1 = null;
                dataStoreModel.setCountDownLatch(new CountDownLatch(2));
                dataStoreModel.setDriver(driver1);

                Thread thread1 = new Thread(new LessOrEqual4000CharController(dataStoreModel.getCountDownLatch(), dataStoreModel.getArrayList_Char().get(0), dataStoreModel.getDriver(), telegramDataStoreModel, dataStoreModel, textCSVModel, voiceCSVModel, fileNameCSVModel));
                WebDriver driver2 = null;
                dataStoreModel.setDriver(driver2);

                Thread thread2 = new Thread(new LessOrEqual4000CharController(dataStoreModel.getCountDownLatch(), dataStoreModel.getArrayList_Char().get(1),  dataStoreModel.getDriver(), telegramDataStoreModel, dataStoreModel, textCSVModel, voiceCSVModel, fileNameCSVModel));
                thread1.start();
                thread2.start();


            }
            dataStoreModel.getCountDownLatch().await();
            // dataStoreModelSecond.getCountDownLatch().await();
        }


        if (!textCSVModel.isFlag()) {
            WebDriver driver1 = new ChromeDriver();
            dataStoreModel.setCountDownLatch(new CountDownLatch(1));
            Thread thread = new Thread(new MoreOrEqual4001CharController(driver1,telegramDataStoreModel, dataStoreModel, textCSVModel, voiceCSVModel, fileNameCSVModel));
            thread.start();
            dataStoreModel.getCountDownLatch().await();
        }
    }
}

class work123 {
    public static void main(String[] args) {
        AllProcessController allProcessController = new
                AllProcessController();
        try {
            allProcessController.work();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

