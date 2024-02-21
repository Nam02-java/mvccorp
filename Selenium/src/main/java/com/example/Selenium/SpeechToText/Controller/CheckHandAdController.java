package com.example.Selenium.SpeechToText.Controller;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class CheckHandAdController extends BlockAdController {

    public CheckHandAdController(WebDriver driver, CountDownLatch countDownLatch) {
        super(driver, countDownLatch);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    }

    public CheckHandAdController(WebDriver driver, WebDriverWait webDriverWait, CountDownLatch countDownLatch) {
        super(driver, webDriverWait, countDownLatch);
    }

    @Override
    public void run() {
        if (wait != null) {
            //  logic for the second constructor
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='×']"))).click();
            } catch (Exception exception) {
            }
        } else {
            // logic for the first constructor
            element_solve = driver.findElements(By.xpath("//button[normalize-space()='×']"));
            if (element_solve.size() > 0 && element_solve.get(0).isDisplayed()) {
                driver.findElement(By.xpath("//button[normalize-space()='×']")).click();
            } else {
            }
        }
        countDownLatch.countDown();
    }
}
