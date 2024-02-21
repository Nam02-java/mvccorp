package com.example.Selenium.SpeechToText.Controller;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

public class CheckHostAdController extends BlockAdController {


    public CheckHostAdController(WebDriver driver, CountDownLatch countDownLatch ) {
        super(driver, countDownLatch );
        this.wait=new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    @Override
    public void run() {

        if (wait != null) {
            //  logic for the second constructor
            try { // quảng cáo host 8 9
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[contains(@style,'width: 100vw')]"))); //iframe[contains(@style,'width: 100vw')]
                element_solve = driver.findElements(By.xpath("//iframe[contains(@style,'width: 100vw')]"));
                if (element_solve.size() > 0 && element_solve.get(0).isDisplayed()) {
                    WebElement frame1 = driver.findElement(By.xpath("//iframe[contains(@style,'width: 100vw')]"));
                    driver.switchTo().frame(frame1);
                    driver.findElement(By.xpath("//div[@aria-label='Đóng quảng cáo']")).click();
                    driver.switchTo().defaultContent(); // return default content
                }
                countDownLatch.countDown();
            } catch (Exception exception) {
                countDownLatch.countDown();
                System.out.println("Quảng cáo host 8 9 :  " + exception);
            }

        } else {
            // logic for the first constructor
        }
    }
}
