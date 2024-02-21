package com.example.Selenium.SpeechToText.Controller;

import com.example.Selenium.SpeechToText.Model.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;


public abstract class InitializationDriverController extends BotTelegramActionController implements Runnable {
    private static int count = 0;
    public CSVStoreModel textCsvStoreModel;
    public CSVStoreModel voiceCsvStoreModel;
    public CSVStoreModel fileNameCsvStoreModel;


    public InitializationDriverController(WebDriver driver, TelegramDataStoreModel telegramDataStoreModel, DataStoreModel dataStoreModel, CSVStoreModel textCsvStoreModel, CSVStoreModel voiceCsvStoreModel, CSVStoreModel fileNameCsvStoreModel) {
        super(driver, telegramDataStoreModel, dataStoreModel);
        this.textCsvStoreModel = textCsvStoreModel;
        this.voiceCsvStoreModel = voiceCsvStoreModel;
        this.fileNameCsvStoreModel = fileNameCsvStoreModel;
    }


    public void printParams() {
        System.out.println("Params text : " + dataStoreModel.getParams().get("Text") + "\n"
                + "Params voice : " + dataStoreModel.getParams().get("Voice") + "\n"
                + "Params file name : " + dataStoreModel.getParams().get("FileName"));
    }

    public void initializationDriver() {

        printParams();

        System.setProperty("webdriver.http.factory", "jdk-http-client");
        System.setProperty("webdriver.chrome.driver", "E:\\CongViecHocTap\\ChromeDriver\\chromedriver-win64\\chromedriver.exe");

        dataStoreModel.setChromeOptions(new ChromeOptions());
        dataStoreModel.getChromeOptions().setExperimentalOption("useAutomationExtension", false); // Disable chrome running as automation
        dataStoreModel.getChromeOptions().setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation")); // Disable chrome running as automation


        driver = new ChromeDriver(dataStoreModel.getChromeOptions());
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2)); // The number of seconds that a driver waits to load an element without the wait setting
        driver.manage().window().maximize();
    }

    public void configureDriverOnTheLogin() throws TelegramApiException, InterruptedException {

        dataStoreModel.getDriver().get("https://ttsfree.com/login");

        ((JavascriptExecutor) dataStoreModel.getDriver()).executeScript("var images = document.getElementsByTagName('img'); for(var i = 0; i < images.length; i++) { images[i].setAttribute('hidden', 'true'); }");

        dataStoreModel.getDriver().findElement(By.xpath("//input[@placeholder='Username']")).sendKeys(dataStoreModel.getUserName());
        dataStoreModel.getDriver().findElement(By.xpath("//input[@placeholder='Enter password']")).sendKeys(dataStoreModel.getUserPassWord());

        dataStoreModel.setCountDownLatch(new CountDownLatch(2));
        Thread threadCheckEscAd = new Thread(new CheckEscAdController(dataStoreModel.getDriver(), null, dataStoreModel.getCountDownLatch()));
        Thread threadCheckHandAD = new Thread(new CheckHandAdController(dataStoreModel.getDriver(), null, dataStoreModel.getCountDownLatch()));
        threadCheckEscAd.start();
        threadCheckHandAD.start();
        try {
            dataStoreModel.getCountDownLatch().await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        dataStoreModel.getDriver().findElement(By.xpath("//ins[@class='iCheck-helper']")).click();
        dataStoreModel.getDriver().findElement(By.xpath("//input[@id='btnLogin']")).click();

        try {
            dataStoreModel.setWebDriverWait(new WebDriverWait(dataStoreModel.getDriver(), Duration.ofSeconds(2)));
            dataStoreModel.getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("laptopaz.com"))).click();
        } catch (Exception exception) {
            ConnectionTransferBetweenloginAndHomePage();
        }
    }

    public void ConnectionTransferBetweenloginAndHomePage() throws TelegramApiException, InterruptedException {
        if (textCsvStoreModel.isFlag()) {
            configureActionDriverOnTheWebsite();
        } else if (!textCsvStoreModel.isFlag()) {
            for (int i = 0; i <= dataStoreModel.getArrayList_Char().size() + 1; i++) {
                System.out.println("size of arrayList_Char : " + dataStoreModel.getArrayList_Char().size());
                System.out.println("value of arraylist[index] : " + dataStoreModel.getArrayList_Char().get(0));
                configureActionDriverOnTheWebsite();
                count += 1;
                dataStoreModel.getArrayList_Char().remove(0);
            }
            dataStoreModel.getDriver().close();
        }
    }

    public void configureActionDriverOnTheWebsite() throws InterruptedException, TelegramApiException {
        dataStoreModel.getDriver().get("https://ttsfree.com/vn");
        ((JavascriptExecutor) dataStoreModel.getDriver()).executeScript("var images = document.getElementsByTagName('img'); for(var i = 0; i < images.length; i++) { images[i].setAttribute('hidden', 'true'); }");
        ((JavascriptExecutor) dataStoreModel.getDriver()).executeScript("var images = document.querySelectorAll('img[id=captcha_image]'); for(var i = 0; i < images.length; i++) { if(images[i].src.startsWith('https://ttsfree.com/voice/captcha.php?sign=?')) { images[i].removeAttribute('hidden'); } }");

        dataStoreModel.setCountDownLatch(new CountDownLatch(1));

        Thread threadCheckAdTop = new Thread(new CheckTopAdController(dataStoreModel.getDriver(), dataStoreModel.getCountDownLatch()));
        threadCheckAdTop.start();
        dataStoreModel.getCountDownLatch().await();

        Thread threadCheckEscAd = new Thread(new CheckEscAdController(dataStoreModel.getDriver(), dataStoreModel.getCountDownLatch()));
        threadCheckEscAd.start();
        dataStoreModel.getCountDownLatch().await();

        Thread threadCheckHandAd = new Thread(new CheckHandAdController(dataStoreModel.getDriver(), dataStoreModel.getCountDownLatch()));
        threadCheckHandAd.start();
        dataStoreModel.getCountDownLatch().await();

        if (textCsvStoreModel.isFlag()) {
            dataStoreModel.getDriver().findElement(By.xpath("//textarea[@id='input_text']")).sendKeys(dataStoreModel.getParams().get("Text"));
        } else if (!textCsvStoreModel.isFlag()) {
            dataStoreModel.getDriver().findElement(By.xpath("//textarea[@id='input_text']")).sendKeys(dataStoreModel.getArrayList_Char().get(0));
        }

        threadCheckHandAd = new Thread(new CheckHandAdController(dataStoreModel.getDriver(), dataStoreModel.getCountDownLatch()));
        threadCheckHandAd.start();
        dataStoreModel.getCountDownLatch().await();

        if (dataStoreModel.getDriver().equals("https://ttsfree.com/vn#google_vignette")) {
            dataStoreModel.getDriver().navigate().back();
            dataStoreModel.getDriver().findElement(By.xpath("//a[normalize-space()='TTS Server 2']")).click();
        }

        if (dataStoreModel.getParams().get("Voice").equals("Female")) {
            dataStoreModel.getDriver().findElement(By.xpath("(//label[@for='radioPrimaryvi-VN'])[1]")).click();
        } else if (dataStoreModel.getParams().get("Voice").equals("Male")) {
            dataStoreModel.getDriver().findElement(By.xpath("(//label[@for='radioPrimaryvi-VN2'])[1]")).click();
        }
        dataStoreModel.getDriver().findElement(By.xpath("//a[@class='btn mb-2 lg action-1 text-white convert-now']")).click();

        checkAndProcessCaptcha();

        if (dataStoreModel.isFlag()) {
            dataStoreModel.setFlag(false);
            System.out.println("flag == true");
            try {
                dataStoreModel.setWebDriverWait(new WebDriverWait(dataStoreModel.getDriver(), Duration.ofSeconds(5)));
                dataStoreModel.getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("laptopaz.com"))).click();
            } catch (Exception exception) {

            }
        } else {
        }

        dataStoreModel.setCountDownLatch(new CountDownLatch(1));
        threadCheckHandAd = new Thread(new CheckHandAdController(dataStoreModel.getDriver(), dataStoreModel.getCountDownLatch()));
        threadCheckHandAd.start();
        dataStoreModel.getCountDownLatch().await();

        dataStoreModel.setWebElement(dataStoreModel.getDriver().findElement(By.xpath("//*[@id=\"input_text\"]")));
        dataStoreModel.getJavascriptExecutor().executeScript("arguments[0].scrollIntoView();", dataStoreModel.getWebElement());

        dataStoreModel.setWebDriverWait(new WebDriverWait(dataStoreModel.getDriver(), Duration.ofSeconds(120)));
        dataStoreModel.getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"progessResults\"]/div[2]/center[1]/div/a"))).click();
        dataStoreModel.setCountDownLatch(new CountDownLatch(1));
        Thread threadCheckHostAD = new Thread(new CheckHostAdController(dataStoreModel.getDriver(), dataStoreModel.getCountDownLatch()));
        threadCheckHostAD.start();
        dataStoreModel.getCountDownLatch().await();

        /**
         * continue
         */
    }

    public void checkAndProcessCaptcha() throws InterruptedException, TelegramApiException {


        dataStoreModel.setFlag(false);
        try {
            dataStoreModel.setWebDriverWait(new WebDriverWait(dataStoreModel.getDriver(), Duration.ofSeconds(10)));
            dataStoreModel.getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='alert alert-danger alert-dismissable']"))).isDisplayed();
            System.out.println("displayed captcha");

            dataStoreModel.setJavascriptExecutor((JavascriptExecutor) dataStoreModel.getDriver());
            dataStoreModel.setWebElement(dataStoreModel.getDriver().findElement(By.xpath("(//a[normalize-space()='Confirm'])[1]")));
            dataStoreModel.getJavascriptExecutor().executeScript("arguments[0].scrollIntoView();", dataStoreModel.getWebElement());

            getCaptChaImage();
            saveImageCaptcha();
            sendPhoto();

            telegramDataStoreModel.setMessage(new SendMessage());
            telegramDataStoreModel.getMessage().setChatId("1159534870");

            int zero = telegramDataStoreModel.getCountDownDuration(); // 30 seconds to count down

            for (int second = 0; second <= telegramDataStoreModel.getCountDownDuration(); second++) {
                zero = telegramDataStoreModel.getCountDownDuration() - second;
                System.out.println(zero);

                Thread.sleep(1000);

                if (telegramDataStoreModel.getTextFromUserTelegram() != null) {
                    System.out.println("Text : " + telegramDataStoreModel.getTextFromUserTelegram());
                    for (int i = 0; i < telegramDataStoreModel.getTextFromUserTelegram().length(); i++) {
                        if (!Character.isDigit(telegramDataStoreModel.getTextFromUserTelegram().charAt(i))) {
                            telegramDataStoreModel.getMessage().setText("Value of text has char");
                            execute(telegramDataStoreModel.getMessage());
                            telegramDataStoreModel.setTextFromUserTelegram(null);
                            break;
                        }
                    }
                    if (telegramDataStoreModel.getTextFromUserTelegram() == null || telegramDataStoreModel.getTextFromUserTelegram().isEmpty() || telegramDataStoreModel.getTextFromUserTelegram().length() <= 3) {
                        if (telegramDataStoreModel.getTextFromUserTelegram() == null) {
                        } else {
                            telegramDataStoreModel.getMessage().setText("Text length must be 4 numbers or more");
                            execute(telegramDataStoreModel.getMessage());
                        }
                        telegramDataStoreModel.setTextFromUserTelegram(null);
                        continue;
                    }
                    dataStoreModel.setCountDownLatch(new CountDownLatch(1));
                    Thread threadCheckHandAd = new Thread(new CheckHandAdController(dataStoreModel.getDriver(), dataStoreModel.getCountDownLatch()));
                    threadCheckHandAd.start();
                    dataStoreModel.getCountDownLatch().await();

                    dataStoreModel.getDriver().findElement(By.xpath("(//input[@id='captcha_input'])[1]")).sendKeys(telegramDataStoreModel.getTextFromUserTelegram());
                    dataStoreModel.setElement_solve(dataStoreModel.getDriver().findElements(By.xpath("(//img[@title='Ad.Plus Advertising'])[1]")));
                    if (dataStoreModel.getElement_solve().size() > 0 && dataStoreModel.getElement_solve().get(0).isDisplayed()) {
                        dataStoreModel.getDriver().findElement(By.xpath("(//img[@title='Ad.Plus Advertising'])[1]")).click();
                    }
                    dataStoreModel.getDriver().findElement(By.xpath("(//a[normalize-space()='Confirm'])[1]")).click();
                    try {
                        dataStoreModel.setWebDriverWait(new WebDriverWait(dataStoreModel.getDriver(), Duration.ofSeconds(10)));
                        dataStoreModel.getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//h4[normalize-space()='Error!'])[1]")));
                        telegramDataStoreModel.getMessage().setText("Wrong number of captcha image , type again !");
                        execute(telegramDataStoreModel.getMessage());
                        telegramDataStoreModel.setTextFromUserTelegram(null);
                        getCaptChaImage();
                        sendPhoto();
                        continue;

                    } catch (Exception exception) {
                        dataStoreModel.setFlag(true);
                        telegramDataStoreModel.setTextFromUserTelegram(null);
                        telegramDataStoreModel.getMessage().setText("Valid captcha code");
                        execute(telegramDataStoreModel.getMessage());

                        dataStoreModel.setWebDriverWait(new WebDriverWait(dataStoreModel.getDriver(), Duration.ofSeconds(10)));

                        dataStoreModel.getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Tạo Voice')]"))).click();

                        break;
                    }
                }
                System.out.println("---------------------");
            }
            System.out.println("count here");
            System.out.println(zero);
            if (zero == 0) {
                System.out.println("count ");
                telegramDataStoreModel.getMessage().setText("End of time to solove captcha");
                execute(telegramDataStoreModel.getMessage());
            }
        } catch (Exception exception) {
            System.out.println("Non display captcha");
        }
    }

    @Override
    public void run() {
        dataStoreModel.setParams(new HashMap<>());
        if (textCsvStoreModel.isFlag()) {
            dataStoreModel.getParams().put("Text", textCsvStoreModel.getReadTextOfColumn());
        } else if (!textCsvStoreModel.isFlag()) {
            dataStoreModel.getParams().put("Text", "");
        }
        dataStoreModel.getParams().put("Voice", voiceCsvStoreModel.getReadTextOfColumn());
        dataStoreModel.getParams().put("FileName", fileNameCsvStoreModel.getReadTextOfColumn());

        initializationDriver();
        try {
            configureDriverOnTheLogin();
            configureActionDriverOnTheWebsite();
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

//class beetween extends InitializationDriverController {
//
//
//    public beetween(TelegramDataStoreModel telegramDataStoreModel, DataStoreModel dataStoreModel, CSVStoreModel textCsvStoreModel, CSVStoreModel voiceCsvStoreModel, CSVStoreModel fileNameCsvStoreModel) {
//        super(telegramDataStoreModel, dataStoreModel, textCsvStoreModel, voiceCsvStoreModel, fileNameCsvStoreModel);
//    }
//
//    public static void main(String[] args) throws InterruptedException, TelegramApiException {
//        DataStoreModel driverThread = new DataStoreModel();
//        TelegramDataStoreModel telegramDataStoreModel1 = new TelegramDataStoreModel();
//
////        Thread thread1 = new Thread(new beetween(telegramDataStoreModel1, driverThread));
////        thread1.start();
//    }
//}


