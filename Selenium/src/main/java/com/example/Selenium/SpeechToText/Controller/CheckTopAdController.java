package com.example.Selenium.SpeechToText.Controller;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

public class CheckTopAdController extends BlockAdController {

    public CheckTopAdController(WebDriver driver, CountDownLatch countDownLatch) {
        super(driver, countDownLatch);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Override
    public void run() {
        if (wait != null) {
            //  logic for the second constructor
            try {
                WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2));
                webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@aria-label='Đóng quảng cáo']"))).click();
            } catch (Exception exception) {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='grippy-host'])[1]"))).click();
            }
        } else {
            // logic for the first constructor
            element_solve = driver.findElements(By.xpath("(//div[@class='grippy-host'])[1]"));
            if (element_solve.size() > 0 && element_solve.get(0).isDisplayed()) {
                driver.findElement(By.xpath("(//div[@class='grippy-host'])[1]")).click();
            } else {
            }
        }
        countDownLatch.countDown();
    }
}
