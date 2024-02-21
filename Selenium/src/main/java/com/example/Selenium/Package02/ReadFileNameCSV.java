package com.example.Selenium.Package02;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ReadFileNameCSV implements Runnable {
    public static String FileNameCSV;
    private CountDownLatch countDownLatch;
    public static String FileNameCSV_notification;
    public static Boolean flag_FileNameCSV = true;

    public ReadFileNameCSV(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        flag_FileNameCSV = true;
        //FileNameCSV_notification = null;
        // Đường dẫn đến file CSV
        String csvFilePath = "E:\\CongViecHocTap\\Jmeter\\dulieu.csv";

        // Tên cột đọc
        String columnName = "FileName";

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
                FileNameCSV = row[columnIndex];
                System.out.println("FileName : " + FileNameCSV);
                FileNameCSV_notification = null;
            }

            File directory = new File("E:\\New folder\\");
            File[] files = directory.listFiles(File::isFile);
            for (int i = 0; i < files.length; i++) {
                String name = files[i].getName();
                String target = name.copyValueOf(".mp3".toCharArray());
                name = name.replace(target, "");
                if (name.equals(FileNameCSV)) {
                    FileNameCSV_notification = "Tên File bị trùng trong thư mục";
                    System.out.println(FileNameCSV_notification);
                    flag_FileNameCSV = false;
                    break;
                }
            }

            if (FileNameCSV.length() >= 51) {
                System.out.println("Độ dài file dài quá 50 kí tự");
                FileNameCSV_notification = "Độ dài file dài quá 50 kí tự";
                System.out.println(FileNameCSV_notification);
                flag_FileNameCSV = false;
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
}

//File directory = new File("E:\\New folder\\");
//        File[] files = directory.listFiles(File::isFile);
//        for (int i = 0; i < files.length; i++) {
//            String name = files[i].getName();
//            String target = name.copyValueOf(".mp3".toCharArray());
//            name = name.replace(target, "");
//            if (name.equals(fileName)) {
//                System.out.println("Tên File bị trùng trong dữ liệu của bạn , hãy đổi tên khác hoặc xóa file cũ của bạn");
//                flag_checkFileName = false;
//                break;
//            }
//        }
//        countDownLatch.countDown();