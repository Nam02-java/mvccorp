package com.example.Selenium.SpeechToText.Controller;

import com.example.Selenium.SpeechToText.Model.DataStoreModel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public abstract class BlockAdController implements Runnable{

    public WebDriver driver;
    public WebDriverWait wait;

    public CountDownLatch countDownLatch;
    public List<WebElement> element_solve;


    public BlockAdController(WebDriver driver, CountDownLatch countDownLatch) {
        this.driver = driver;
        this.countDownLatch = countDownLatch;
    }

    public BlockAdController(WebDriver driver, WebDriverWait wait, CountDownLatch countDownLatch) {
        this.driver = driver;
        this.wait = wait;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {

    }
}
