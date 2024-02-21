package com.example.Selenium.SpeechToText.Model;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class VoiceCSVModel extends ReadCSVModel {


    public VoiceCSVModel(CSVStoreModel csvStoreModel, CountDownLatch latchCSV, String columnName) {
        super(csvStoreModel, latchCSV, columnName);
    }

    @Override
    public void dataHandle(int columnIndex, CSVReader csvReader, CSVStoreModel csvStoreModel) throws IOException, CsvException {
        csvStoreModel.setFlag(true);
        // Đọc dữ liệu từ cột và hiển thị nó
        List<String[]> allData = csvReader.readAll();
        for (String[] row : allData) {
            csvStoreModel.setReadTextOfColumn(row[columnIndex]);
            System.out.println("Voice CSV : " + csvStoreModel.getReadTextOfColumn());
        }

        if (CheckGender(csvStoreModel.getReadTextOfColumn())) {
        } else {
            csvStoreModel.setFlag(false);
            csvStoreModel.setNotification("Thông tin về Voice không xác định , hãy nhập lại");
            System.out.println(csvStoreModel.getNotification());
        }
    }

    private boolean CheckGender(String gender) {
        // Chuyển đổi giá trị của gioiTinh thành chữ thường để so sánh không phân biệt chữ hoa chữ thường
        gender = gender.toLowerCase();

        // Kiểm tra xem gioiTinh có thuộc vào các giá trị cho phép không
        return gender.equals("male") || gender.equals("female");
    }
}


