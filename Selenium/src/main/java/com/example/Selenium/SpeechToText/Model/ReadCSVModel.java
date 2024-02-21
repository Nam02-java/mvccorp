package com.example.Selenium.SpeechToText.Model;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ReadCSVModel implements Runnable {

    public CSVStoreModel csvStoreModel;
    public DataStoreModel dataStoreModel;

    public ReadCSVModel(CSVStoreModel csvStoreModel) {
        this.csvStoreModel = csvStoreModel;
    }

    public ReadCSVModel(CSVStoreModel csvStoreModel, CountDownLatch latchCSV, String columnName) {
        this.csvStoreModel = csvStoreModel;
        this.csvStoreModel.countDownLatch = latchCSV;
        csvStoreModel.setColumnName(columnName);
    }

    public ReadCSVModel(CSVStoreModel csvStoreModel, DataStoreModel dataStoreModel, CountDownLatch latchCSV, String columnName) {
        this.csvStoreModel = csvStoreModel;
        this.dataStoreModel = dataStoreModel;
        this.csvStoreModel.countDownLatch = latchCSV;
        this.csvStoreModel.setColumnName(columnName);
    }


    public void run() {
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(csvStoreModel.getCsvFilePath())).build()) {
            // Lấy header để xác định index của cột cần đọc
            String[] headers = csvReader.readNext();
            int columnIndex = -1;
            for (int i = 0; i < headers.length; i++) {
                if (headers[i].equalsIgnoreCase(csvStoreModel.getColumnName())) {
                    columnIndex = i;
                    break;
                }
            }

            if (columnIndex == -1) {
                System.out.println("Không tìm thấy cột có tên: " + csvStoreModel.getCsvFilePath());
                return;
            }

            dataHandle(columnIndex, csvReader, csvStoreModel);

            csvStoreModel.countDownLatch.countDown();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException | CsvException e) {
            System.err.println("Can't read file : " + e.getMessage());
        }
    }

    public void dataHandle(int columnIndex, CSVReader csvReader, CSVStoreModel csvStoreModel) throws IOException, CsvException {

    }
}

