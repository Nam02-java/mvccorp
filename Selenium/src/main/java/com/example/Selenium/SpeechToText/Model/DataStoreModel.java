package com.example.Selenium.SpeechToText.Model;

import com.example.Selenium.SpeechToText.Controller.EnumController;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

public class DataStoreModel {
    private EnumSet<EnumController> statusEnumSet = EnumSet.noneOf(EnumController.class);
    public ConcurrentHashMap<Integer, String> concurrentMap_Char = new ConcurrentHashMap<>();

    public String userName = "nam03test";
    public String userPassWord = "IUtrangmaimai02";
    public String text, text_constructor, notification = null;
    public CountDownLatch latch = new CountDownLatch(1);
    public CountDownLatch countDownLatch;
    public final int limitChar = 20;
    public ArrayList<String> arrayList_Char = new ArrayList<>();
    public int count = 0;
    public Map<String, String> params ;
    public String DownloadsFilePath = "E:\\New folder\\";
    public String ImageCaptchaFilePath = "E:\\CongViecHocTap\\Captcha\\";
    public WebElement webElement;
    public WebDriverWait webDriverWait;
    public JavascriptExecutor javascriptExecutor;
    public List<WebElement> element_solve ;
    public WebDriver driver;
    public ChromeOptions chromeOptions;
    public boolean flag;

    public DataStoreModel dataStoreModel;

    public DataStoreModel(DataStoreModel dataStoreModel) {
        this.statusEnumSet = EnumSet.copyOf(dataStoreModel.statusEnumSet);
        this.userName = dataStoreModel.userName;
        this.userPassWord = dataStoreModel.userPassWord;
        this.text = dataStoreModel.text;
        this.text_constructor = dataStoreModel.text_constructor;
        this.notification = dataStoreModel.notification;
        this.countDownLatch = new CountDownLatch(1);
        this.arrayList_Char = new ArrayList<>(dataStoreModel.arrayList_Char);
        this.count = dataStoreModel.count;
        this.params = new HashMap<>();
        this.DownloadsFilePath = dataStoreModel.DownloadsFilePath;
        this.ImageCaptchaFilePath = dataStoreModel.ImageCaptchaFilePath;
        this.webElement = dataStoreModel.webElement;
        this.webDriverWait = dataStoreModel.webDriverWait;
        this.javascriptExecutor = dataStoreModel.javascriptExecutor;
        this.element_solve = new ArrayList<>(); // Sao chép List
        this.driver = dataStoreModel.driver;
        this.chromeOptions = dataStoreModel.chromeOptions;
        this.flag = dataStoreModel.flag;
        // Sao chép các thuộc tính khác tương tự
        // ...
    }

    public DataStoreModel(WebDriver driver) {
        this.driver = driver;
    }



    public ConcurrentHashMap<Integer, String> getConcurrentMap_Char() {
        return concurrentMap_Char;
    }

    public void setConcurrentMap_Char(ConcurrentHashMap<Integer, String> concurrentMap_Char) {
        this.concurrentMap_Char = concurrentMap_Char;
    }

    public String getImageCaptchaFilePath() {
        return ImageCaptchaFilePath;
    }

    public void setImageCaptchaFilePath(String imageCaptchaFilePath) {
        ImageCaptchaFilePath = imageCaptchaFilePath;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void setStatusEnumSet(EnumSet<EnumController> statusEnumSet) {
        this.statusEnumSet = statusEnumSet;
    }

    public ChromeOptions getChromeOptions() {
        return chromeOptions;
    }

    public void setChromeOptions(ChromeOptions chromeOptions) {
        this.chromeOptions = chromeOptions;
    }

    public DataStoreModel() {
    }

    public EnumSet<EnumController> getStatusEnumSet() {
        return statusEnumSet;
    }

    public void addStatus(EnumController status) {
        statusEnumSet.add(status);
    }

    public void removeStatus(EnumController status) {
        statusEnumSet.remove(status);
    }


    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }


    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public WebElement getWebElement() {
        return webElement;
    }

    public void setWebElement(WebElement webElement) {
        this.webElement = webElement;
    }

    public WebDriverWait getWebDriverWait() {
        return webDriverWait;
    }

    public void setWebDriverWait(WebDriverWait webDriverWait) {
        this.webDriverWait = webDriverWait;
    }

    public JavascriptExecutor getJavascriptExecutor() {
        return javascriptExecutor;
    }

    public void setJavascriptExecutor(JavascriptExecutor javascriptExecutor) {
        this.javascriptExecutor = javascriptExecutor;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public List<WebElement> getElement_solve() {
        return element_solve;
    }

    public void setElement_solve(List<WebElement> element_solve) {
        this.element_solve = element_solve;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText_constructor() {
        return text_constructor;
    }

    public void setText_constructor(String text_constructor) {
        this.text_constructor = text_constructor;
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }

    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public int getLimitChar() {
        return limitChar;
    }

    public ArrayList<String> getArrayList_Char() {
        return arrayList_Char;
    }

    public void setArrayList_Char(ArrayList<String> arrayList_Char) {
        this.arrayList_Char = arrayList_Char;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public String getDownloadsFilePath() {
        return DownloadsFilePath;
    }

    public void setDownloadsFilePath(String downloadsFilePath) {
        DownloadsFilePath = downloadsFilePath;
    }

    public String getUserPassWord() {
        return userPassWord;
    }

    public void setUserPassWord(String userPassWord) {
        this.userPassWord = userPassWord;
    }
}

