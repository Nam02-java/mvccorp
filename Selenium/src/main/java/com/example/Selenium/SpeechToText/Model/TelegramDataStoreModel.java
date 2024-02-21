package com.example.Selenium.SpeechToText.Model;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.File;

public class TelegramDataStoreModel {

    public String textFromUserTelegram = null;
    public String botUserName = "CaptchaSlove_bot";
    public String botToken = "6928830332:AAGmv3fN_k8YdITzJeOyjqtsDQfWuviF308";
    public String filePathSaveCaptchaImage = "E:\\CongViecHocTap\\Captcha\\captcha.png";
    public SendMessage message ;

    public int countDownDuration = 30;




    public TelegramDataStoreModel() {
    }


    public TelegramDataStoreModel(String textFromUserTelegram, String botUserName, String botToken, String filePathSaveCaptchaImage, SendMessage message) {
        this.textFromUserTelegram = textFromUserTelegram;
        this.botUserName = botUserName;
        this.botToken = botToken;
        this.filePathSaveCaptchaImage = filePathSaveCaptchaImage;
        this.message = message;
    }


    public int getCountDownDuration() {
        return countDownDuration;
    }

    public void setCountDownDuration(int countDownDuration) {
        this.countDownDuration = countDownDuration;
    }

    public String getTextFromUserTelegram() {
        return textFromUserTelegram;
    }

    public void setTextFromUserTelegram(String textFromUserTelegram) {
        this.textFromUserTelegram = textFromUserTelegram;
    }

    public String getBotUserName() {
        return botUserName;
    }

    public void setBotUserName(String botUserName) {
        this.botUserName = botUserName;
    }

    public String getBotToken() {
        return botToken;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    public String getFilePathSaveCaptchaImage() {
        return filePathSaveCaptchaImage;
    }

    public void setFilePathSaveCaptchaImage(String filePathSaveCaptchaImage) {
        this.filePathSaveCaptchaImage = filePathSaveCaptchaImage;
    }

    public SendMessage getMessage() {
        return message;
    }

    public void setMessage(SendMessage message) {
        this.message = message;
    }
}
