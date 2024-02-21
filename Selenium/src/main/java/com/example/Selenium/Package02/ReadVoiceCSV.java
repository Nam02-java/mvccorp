package com.example.Selenium.Package02;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ReadVoiceCSV implements Runnable {
    public static String VoiceCSV;
    private CountDownLatch countDownLatch;
    public static String VoiceCSV_notification;

    public static Boolean flag_VoiceCSV = true;

    public ReadVoiceCSV(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        flag_VoiceCSV = true;
       // VoiceCSV_notification = null;
        // Đường dẫn đến file CSV
        String csvFilePath = "E:\\CongViecHocTap\\Jmeter\\dulieu.csv";

        // Tên cột đọc
        String columnName = "Voice";

        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(csvFilePath)).build()) {
            // Lấy header để xác định index của cột cần đọc
            String[] headers = csvReader.readNext();
            int columnIndex = -1;
            for (int i = 0; i < headers.length; i++) {
                if (headers[i].equalsIgnoreCase(columnName)) {
                    columnIndex = i;
                    break;
                }
            }

            if (columnIndex == -1) {
                System.out.println("Không tìm thấy cột có tên: " + columnName);
                return;
            }

            // Đọc dữ liệu từ cột và hiển thị nó
            List<String[]> allData = csvReader.readAll();
            for (String[] row : allData) {
                VoiceCSV = row[columnIndex];
                System.out.println("Voice CSV : " + VoiceCSV);
                VoiceCSV_notification = null;
            }

            if (CheckGender(VoiceCSV)) {
            } else {
                flag_VoiceCSV = false;
                VoiceCSV_notification = "Thông tin về Voice không xác định , hãy nhập lại";
                System.out.println(VoiceCSV_notification);
            }
            countDownLatch.countDown();
        } catch (IOException e) {
            countDownLatch.countDown();
            e.printStackTrace();
        } catch (CsvValidationException e) {
            countDownLatch.countDown();
            throw new RuntimeException(e);
        } catch (CsvException e) {
            countDownLatch.countDown();
            throw new RuntimeException(e);
        }
    }

    private boolean CheckGender(String gender) {
        // Chuyển đổi giá trị của gioiTinh thành chữ thường để so sánh không phân biệt chữ hoa chữ thường
        gender = gender.toLowerCase();

        // Kiểm tra xem gioiTinh có thuộc vào các giá trị cho phép không
        return gender.equals("male") || gender.equals("female");
    }
}

