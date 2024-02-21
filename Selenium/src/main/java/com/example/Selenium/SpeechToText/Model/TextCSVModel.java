package com.example.Selenium.SpeechToText.Model;


import com.example.Selenium.SpeechToText.Controller.ExceptionController;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.checkerframework.checker.units.qual.C;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class TextCSVModel extends ReadCSVModel {


    public TextCSVModel(CSVStoreModel csvStoreModel, CountDownLatch latchCSV, String columnName) {
        super(csvStoreModel, latchCSV, columnName);
    }

    @Override
    public void dataHandle(int columnIndex, CSVReader csvReader, CSVStoreModel csvStoreModel) throws IOException, CsvException {
        // Đọc dữ liệu từ cột và hiển thị nó
        List<String[]> allData = csvReader.readAll();
        for (String[] row : allData) {
            csvStoreModel.setReadTextOfColumn(row[columnIndex]);

            //check length
            if (csvStoreModel.getReadTextOfColumn().length() >= 4001) {
                System.out.println("TextCSV : " + csvStoreModel.getReadTextOfColumn());
                csvStoreModel.setFlag(false);
            } else if (csvStoreModel.getReadTextOfColumn().length() <= 4000) {
                System.out.println("TextCSV : " + csvStoreModel.getReadTextOfColumn());
                csvStoreModel.setFlag(true);
            }
        }
    }
}

class test extends ReadCSVModel {

    public test(CSVStoreModel csvStoreModel, CountDownLatch latchCSV, String columnName) {
        super(csvStoreModel, latchCSV, columnName);
        run();

    }

    @Override
    public void run() {
        for (int i = 1 ;  i <= 30 ; i++){
            System.out.println(i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class test2 extends ReadCSVModel {

    public test2(CSVStoreModel csvStoreModel, CountDownLatch latchCSV, String columnName) {
        super(csvStoreModel, latchCSV, columnName);
        run();

    }

    @Override
    public void run() {
        for (int i = 1 ;  i <= 30 ; i++){
            System.out.println(i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class kh {
    public static void main(String[] args) {
        CSVStoreModel cs = new CSVStoreModel();
        CountDownLatch c = new CountDownLatch(2);
        Thread thread = new Thread(new test(cs, c, "asd"));
        Thread thread2 = new Thread(new test2(cs, c, "asd"));
        thread2.start();
        thread.start();
    }
}

class runtest {
    public static void main(String[] args) {
        CountDownLatch latchCSV = new CountDownLatch(3);

        CSVStoreModel textCSVStoreModel = new CSVStoreModel();
        CSVStoreModel voiceCSVStoreModel = new CSVStoreModel();
        CSVStoreModel fileNameCSVStoreModel = new CSVStoreModel();
        DataStoreModel fileNameDataStoreModel = new DataStoreModel();

        Thread t1 = new Thread(new TextCSVModel(textCSVStoreModel, latchCSV, "Text"));
        Thread t2 = new Thread(new VoiceCSVModel(voiceCSVStoreModel, latchCSV, "Voice"));
        Thread t3 = new Thread(new FileNameCSVModel(fileNameCSVStoreModel, fileNameDataStoreModel, latchCSV, "FileName"));

        t1.start();
        t2.start();
        t3.start();

        try {
            latchCSV.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(textCSVStoreModel.isFlag());
        System.out.println(fileNameCSVStoreModel.isFlag());
        System.out.println(voiceCSVStoreModel.isFlag());
    }
}

