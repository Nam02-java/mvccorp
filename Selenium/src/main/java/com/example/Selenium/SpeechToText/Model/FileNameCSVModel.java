package com.example.Selenium.SpeechToText.Model;


import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.checkerframework.checker.units.qual.C;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class FileNameCSVModel extends ReadCSVModel {


    public FileNameCSVModel(CSVStoreModel csvStoreModel, DataStoreModel dataStoreModel, CountDownLatch latchCSV, String columnName) {
        super(csvStoreModel, dataStoreModel, latchCSV, columnName);
    }

    @Override
    public void dataHandle(int columnIndex, CSVReader csvReader, CSVStoreModel csvStoreModel) throws IOException, CsvException {
        csvStoreModel.setFlag(true);
        // Đọc dữ liệu từ cột và hiển thị nó
        List<String[]> allData = csvReader.readAll();
        for (String[] row : allData) {
            csvStoreModel.setReadTextOfColumn(row[columnIndex]);
            System.out.println("FileName : " + csvStoreModel.getReadTextOfColumn());
            csvStoreModel.setNotification(null);
        }

        /**
         * temporary blockade
         */
//        File directory = new File(dataStoreModel.getDownloadsFilePath());
//        File[] files = directory.listFiles(File::isFile);
//        for (int i = 0; i < files.length; i++) {
//            String name = files[i].getName();
//            String target = name.copyValueOf(".mp3".toCharArray());
//            name = name.replace(target, "");
//            if (name.equals(csvStoreModel.getReadTextOfColumn())) {
//                csvStoreModel.setNotification("Tên File bị trùng trong thư mục");
//                System.out.println(csvStoreModel.getNotification());
//                csvStoreModel.setFlag(false);
//                break;
//            }
//        }

        if (csvStoreModel.getReadTextOfColumn().length() >= 51) {
            System.out.println("Độ dài file dài quá 50 kí tự");
            csvStoreModel.setNotification("Độ dài file dài quá 50 kí tự");
            System.out.println(csvStoreModel.getNotification());
            csvStoreModel.setFlag(false);
        }
    }
}

class dmain{
    public static void main(String[] args) {
        DataStoreModel d = new DataStoreModel();
        CSVStoreModel c = new CSVStoreModel();
        CountDownLatch countDownLatch = new CountDownLatch(2);
        ArrayList<ReadCSVModel> arrayList = new ArrayList<>();
        arrayList.add(new TextCSVModel(c,countDownLatch,"A"));
    }
}