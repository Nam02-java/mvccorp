package com.example.Selenium.SpeechToText.Controller;

import com.example.Selenium.SpeechToText.Controller.BotTelegramSetUpController;
import com.example.Selenium.SpeechToText.Model.CSVStoreModel;
import com.example.Selenium.SpeechToText.Model.DataStoreModel;
import com.example.Selenium.SpeechToText.Model.ReadCSVModel;
import com.example.Selenium.SpeechToText.Model.TelegramDataStoreModel;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class BotTelegramActionController extends BotTelegramSetUpController {
    private File imageFile;

    public BotTelegramActionController(WebDriver driver, TelegramDataStoreModel telegramDataStoreModel, DataStoreModel dataStoreModel) {
        super(driver, telegramDataStoreModel, dataStoreModel);
    }
    public void getCaptChaImage() {
        dataStoreModel.setWebDriverWait(new WebDriverWait(dataStoreModel.getDriver(), Duration.ofSeconds(10)));
        dataStoreModel.getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"captcha_image\"]")));

        dataStoreModel.setWebElement(dataStoreModel.getDriver().findElement(By.xpath("//*[@id=\"captcha_image\"]")));
        imageFile = dataStoreModel.getWebElement().getScreenshotAs(org.openqa.selenium.OutputType.FILE);
    }

    public void saveImageCaptcha() {
        try {
            File destinationFolder = new File(dataStoreModel.getImageCaptchaFilePath());

            if (!destinationFolder.exists()) {
                destinationFolder.mkdirs();
            }

            File destinationFile = new File(destinationFolder, "captcha.png");

            FileUtils.copyFile(imageFile, destinationFile);

            System.out.println("Đã lưu trữ ảnh thành công!");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Lỗi khi lưu trữ ảnh.");
        }
    }

    public void sendPhoto() throws InterruptedException {
        Thread.sleep(2000);
        SendPhoto photo = new SendPhoto();
        photo.setChatId(telegramDataStoreModel.message.getChatId());
        photo.setPhoto(new InputFile(new File(String.valueOf(telegramDataStoreModel.getFilePathSaveCaptchaImage()))));
        try {
            this.execute(photo);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
