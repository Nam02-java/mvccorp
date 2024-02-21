package com.example.Selenium.Package02;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.*;
import java.time.Duration;
import java.util.*;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.openqa.selenium.WebDriver;

import java.io.IOException;

import static com.example.Selenium.Package02.ReadFileNameCSV.*;
import static com.example.Selenium.Package02.ReadTextCSV.TextCSV;
import static com.example.Selenium.Package02.ReadTextCSV.flag_TextCSV_LessOrEqual4000Char;
import static com.example.Selenium.Package02.ReadVoiceCSV.*;


@RestController
@RequestMapping("/api/web")
public class Selenium extends TelegramLongPollingBot implements Runnable {

    protected static String text = null;
    private static SendMessage message = new SendMessage("1159534870", "");

    private static CountDownLatch latch = new CountDownLatch(1);
    private CountDownLatch countDownLatch;

    private String text_constructor = null;

    protected final int limitChar = 2000;

    private static ArrayList<String> arrayList_Char = new ArrayList<>();

    private static int count = 0;

    public Selenium(CountDownLatch countDownLatch, String text_constructor) {
        this.countDownLatch = countDownLatch;
        this.text_constructor = text_constructor;
    }

    public Selenium() {
    }

    @Override
    public void onUpdateReceived(Update update) {
        text = update.getMessage().getText();
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {

        super.onUpdatesReceived(updates);
    }

    @Override
    public String getBotUsername() {
        return "CaptchaSlove_bot";
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

    @Override
    public String getBotToken() {
        return "6928830332:AAGmv3fN_k8YdITzJeOyjqtsDQfWuviF308";
    }

    @RequestMapping("/photo")
    public String SendPhoto() throws TelegramApiException, InterruptedException {
        Thread.sleep(2000);
        String save_image = "E:\\CongViecHocTap\\Captcha\\captcha.png";
        SendPhoto photo = new SendPhoto();
        photo.setChatId("1159534870");
        photo.setPhoto(new InputFile(new File(String.valueOf(save_image))));
        try {
            this.execute(photo);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        return "Send photo 200ok";
    }

    @GetMapping("/SpeechToText")
    private ResponseEntity<?> SpeechToText() throws InterruptedException, IOException, TelegramApiException {
        count = 0;
        Direction();
        ResponseEntity<?> response = notification(); // Gọi testNotif2() và chờ phản hồi
        Object body = response.getBody(); // Lấy phần thân của phản hồi
        return ResponseEntity.ok(new String(String.valueOf(body)));
    }

    @GetMapping("/Direction")
    private ResponseEntity<?> Direction() throws InterruptedException, IOException, TelegramApiException {
        CountDownLatch latchCSV = new CountDownLatch(3);
        Thread thread_CSVFileName = new Thread(new ReadFileNameCSV(latchCSV));
        Thread thread_CSVVoice = new Thread(new ReadVoiceCSV(latchCSV));
        Thread thread_CSVText = new Thread(new ReadTextCSV(latchCSV));
        thread_CSVFileName.start();
        thread_CSVVoice.start();
        thread_CSVText.start();
        latchCSV.await();

        if (flag_FileNameCSV == false || flag_VoiceCSV == false) {
            String notification = null;
            if (VoiceCSV_notification != null && FileNameCSV_notification != null) {
                notification = VoiceCSV_notification + "\n" + FileNameCSV_notification;
            } else if (VoiceCSV_notification != null) {
                notification = VoiceCSV_notification;
            } else if (FileNameCSV_notification != null) {
                notification = FileNameCSV_notification;
            }
            return ResponseEntity.ok(new String(notification));
        }

        if (flag_TextCSV_LessOrEqual4000Char == true) {
            getChunksToArrayList(TextCSV, limitChar);
            LessOrEqual4000Char();
        }
        if (flag_TextCSV_LessOrEqual4000Char == false) {
            getChunksToArrayList(TextCSV, limitChar);
            MoreOrEqual4001Char();
        }
        ResponseEntity<?> response = notification(); // Gọi testNotif2() và chờ phản hồi
        Object body = response.getBody(); // Lấy phần thân của phản hồi
        return ResponseEntity.ok(new String(String.valueOf(body)));
    }

    private static ArrayList<String> getChunksToArrayList(String text, int chunkSize) {
        arrayList_Char.clear();
        int length = text.length();
        int index = 0;

        while (index < length) {
            int start = index;
            int end = Math.min(index + chunkSize, length);
            arrayList_Char.add(text.substring(start, end));
            index += chunkSize;
        }

        return arrayList_Char;
    }

    @GetMapping("/Initialization_Driver")
    public ResponseEntity<?> Initialization_Driver(@RequestParam Map<String, String> params) throws InterruptedException, IOException, TelegramApiException {

        System.out.println("params Text : " + params.get("Text"));
        System.out.println("params Voice : " + params.get("Voice"));
        System.out.println("params FileName : " + params.get("FileName"));

        List<WebElement> element_solve = null;
        String user_name = "nam03test"; // mô phỏng tên user
        String user_password = "IUtrangmaimai02"; // mô phỏng password user
        JavascriptExecutor js = null;
        WebElement webElement = null;
        WebDriverWait wait = null;

        System.setProperty("webdriver.http.factory", "jdk-http-client");
        System.setProperty("webdriver.chrome.driver", "E:\\CongViecHocTap\\ChromeDriver\\chromedriver-win64\\chromedriver.exe");

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("useAutomationExtension", false); // disable chrome running as automation
        chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation")); // disable chrome running as automation

        WebDriver driver = new ChromeDriver(chromeOptions);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2)); // số giây mà 1 driver chờ để load 1 phần tử nếu không có thiết lập của wait
        driver.manage().window().maximize();

        driver.get("https://ttsfree.com/login");

        ((JavascriptExecutor) driver).executeScript("var images = document.getElementsByTagName('img'); for(var i = 0; i < images.length; i++) { images[i].setAttribute('hidden', 'true'); }");

        driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys(user_name);
        driver.findElement(By.xpath("//input[@placeholder='Enter password']")).sendKeys(user_password);

        latch = new CountDownLatch(2); // thiết lập 2 Thread ( trường hợp sau khi send key password sẽ có 1 trong 2 hiển thị nên thiết lập 2 thread kiểm tra cùng 1 lúc )
        Thread threadCheckESC = new Thread(new CheckESC(driver, latch, null));
        Thread threadCheckHandAD = new Thread(new CheckHandAD(driver, latch, null));
        threadCheckESC.start();
        threadCheckHandAD.start();
        latch.await();

        driver.findElement(By.xpath("//ins[@class='iCheck-helper']")).click();
        driver.findElement(By.xpath("//input[@id='btnLogin']")).click();

        try {
            wait = new WebDriverWait(driver, Duration.ofSeconds(2));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("laptopaz.com"))).click();
        } catch (Exception exception) {
            if (flag_TextCSV_LessOrEqual4000Char == true) {
                Driver_Work(params, driver, js, webElement, threadCheckESC, threadCheckHandAD, wait, element_solve);
            } else if (flag_TextCSV_LessOrEqual4000Char == false) {
                for (int i = 0; i <= arrayList_Char.size() + 1; i++) {
                    System.out.println("size of arrayList_Char : " + arrayList_Char.size());
                    System.out.println("value of arraylist[index] : " + arrayList_Char.get(0));
                    Driver_Work(params, driver, js, webElement, threadCheckESC, threadCheckHandAD, wait, element_solve);
                    count += 1;
                    arrayList_Char.remove(0);
                }
                driver.close();
            }
        }


        /**
         * đổi tên file theo yêu cầu user ( đơn luồng thì hoạt động oke , đa luồng thì lỗi -> đang nghiên cứu login 1 lúc có request cùng đổi để đảm bảo không có lỗi xảy ra
         * đang nghiên cứu để update
         */
//        File folder = new File("E:\\New folder");
//        File[] files = folder.listFiles();
//        if (files != null && files.length > 0) {
//            Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());
//            File latestFile = files[0];
//            System.out.println(latestFile.getName());
//            String newFileName = params.get("FileName") + ".mp3";
//            File newFile = new File(folder, newFileName);
//            latestFile.renameTo(newFile);
//        }
//        try {
//            wait = new WebDriverWait(driver, Duration.ofSeconds(1));
//            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("laptopaz.com"))).click();
//        } catch (Exception exception) {
//            driver.close();
//        }

        ResponseEntity<?> response = notification(); // Gọi testNotif2() và chờ phản hồi
        Object body = response.getBody(); // Lấy phần thân của phản hồi
        return ResponseEntity.ok(new String(String.valueOf(body)));
    }

    @GetMapping("/LessOrEqual4000Char")
    public ResponseEntity<?> LessOrEqual4000Char() throws InterruptedException, IOException, TelegramApiException {
        CountDownLatch LATCH = new CountDownLatch(2);
        if (arrayList_Char.size() <= 1) {
            Thread thread1 = new Thread(new Selenium(LATCH, arrayList_Char.get(0)));
            thread1.start();
        } else if (arrayList_Char.size() >= 2) {
            Thread thread1 = new Thread(new Selenium(LATCH, arrayList_Char.get(0)));
            Thread thread2 = new Thread(new Selenium(LATCH, arrayList_Char.get(1)));
            thread1.start();
            thread2.start();
        }
        LATCH.await();
        ResponseEntity<?> response = notification(); // Gọi testNotif2() và chờ phản hồi
        Object body = response.getBody(); // Lấy phần thân của phản hồi
        return ResponseEntity.ok(new String(String.valueOf(body)));
    }

    @GetMapping("/MoreOrEqual4001Char")
    public ResponseEntity<?> MoreOrEqual4001Char() throws InterruptedException, IOException, TelegramApiException {
        Map<String, String> params = new HashMap<>();
        params.put("Text", "");
        params.put("Voice", VoiceCSV);
        params.put("FileName", FileNameCSV);
        Initialization_Driver(params);
        ResponseEntity<?> response = notification(); // Gọi testNotif2() và chờ phản hồi
        Object body = response.getBody(); // Lấy phần thân của phản hồi
        return ResponseEntity.ok(new String(String.valueOf(body)));
    }


    @Override
    public void run() {
        Map<String, String> params = new HashMap<>();
        params.put("Text", text_constructor);
        params.put("Voice", VoiceCSV);
        params.put("FileName", FileNameCSV);
        try {
            Initialization_Driver(params);
            countDownLatch.countDown();
        } catch (InterruptedException e) {
            countDownLatch.countDown();
            throw new RuntimeException(e);
        } catch (IOException e) {
            countDownLatch.countDown();
            throw new RuntimeException(e);
        } catch (TelegramApiException e) {
            countDownLatch.countDown();
            throw new RuntimeException(e);
        }
    }

    private static void changeFileName(@RequestParam Map<String, String> params) throws InterruptedException {
        File folder = new File("E:\\New folder");
        File[] files = folder.listFiles();
        if (files != null && files.length > 0) {
            Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());
            File latestFile = files[0];
            System.out.println(latestFile.getName());
            if (flag_TextCSV_LessOrEqual4000Char == true) {
                if (latestFile.getName().equals(params.get("FileName") + ".mp3")) {
                    String newFileName = params.get("FileName") + " (1) .mp3";
                    File newFile = new File(folder, newFileName);
                    latestFile.renameTo(newFile);
                } else {
                    String newFileName = params.get("FileName") + ".mp3";
                    File newFile = new File(folder, newFileName);
                    latestFile.renameTo(newFile);
                }
            } else if (flag_TextCSV_LessOrEqual4000Char == false) {
                if (count == 0) {
                    String newFileName = params.get("FileName") + ".mp3";
                    File newFile = new File(folder, newFileName);
                    latestFile.renameTo(newFile);
                } else if (count != 0) {
                    String newFileName = params.get("FileName") + " " + "(" + count + ")" + " .mp3";
                    File newFile = new File(folder, newFileName);
                    latestFile.renameTo(newFile);
                }
            }
        }
    }

    @GetMapping("/Driver_Work")
    public ResponseEntity<?> Driver_Work(@RequestParam Map<String, String> params,
                                         WebDriver driver,
                                         JavascriptExecutor js,
                                         WebElement webElement,
                                         Thread threadCheckESC,
                                         Thread threadCheckHandAD,
                                         WebDriverWait wait,
                                         List<WebElement> element_solve
    ) throws InterruptedException, IOException, TelegramApiException {
        driver.get("https://ttsfree.com/vn");
        ((JavascriptExecutor) driver).executeScript("var images = document.getElementsByTagName('img'); for(var i = 0; i < images.length; i++) { images[i].setAttribute('hidden', 'true'); }");
        ((JavascriptExecutor) driver).executeScript("var images = document.querySelectorAll('img[id=captcha_image]'); for(var i = 0; i < images.length; i++) { if(images[i].src.startsWith('https://ttsfree.com/voice/captcha.php?sign=?')) { images[i].removeAttribute('hidden'); } }");

        js = (JavascriptExecutor) driver;
        webElement = driver.findElement(By.xpath("//*[@id=\"input_text\"]"));
        js.executeScript("arguments[0].scrollIntoView();", webElement);

        latch = new CountDownLatch(1);
        Thread threadCheckAdsTOP = new Thread(new CheckAdsTOP(driver, latch));
        threadCheckAdsTOP.start();
        latch.await();

        latch = new CountDownLatch(1);
        threadCheckESC = new Thread(new CheckESC(driver, latch));
        threadCheckESC.start();
        latch.await();

        latch = new CountDownLatch(1);
        threadCheckHandAD = new Thread(new CheckHandAD(driver, latch));
        threadCheckHandAD.start();
        latch.await();

        if (flag_TextCSV_LessOrEqual4000Char == true) {
            driver.findElement(By.xpath("//textarea[@id='input_text']")).sendKeys(params.get("Text"));
        } else if (flag_TextCSV_LessOrEqual4000Char == false) {
            driver.findElement(By.xpath("//textarea[@id='input_text']")).sendKeys(arrayList_Char.get(0));
        }

        latch = new CountDownLatch(1);
        threadCheckHandAD = new Thread(new CheckHandAD(driver, latch));
        threadCheckHandAD.start();
        latch.await();

        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.equals("https://ttsfree.com/vn#google_vignette")) {
            System.out.println("da quay tro lai");
            driver.navigate().back();
            driver.findElement(By.xpath("//a[normalize-space()='TTS Server 2']")).click();
        }

        if (params.get("Voice").equals("Female")) {
            driver.findElement(By.xpath("(//label[@for='radioPrimaryvi-VN'])[1]")).click();
        } else if (params.get("Voice").equals("Male")) {
            driver.findElement(By.xpath("(//label[@for='radioPrimaryvi-VN2'])[1]")).click();
        }

        driver.findElement(By.xpath("//a[@class='btn mb-2 lg action-1 text-white convert-now']")).click();

        Boolean flag = false;
        try {
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='alert alert-danger alert-dismissable']"))).isDisplayed();
            System.out.println("displayed captcha");

            js = (JavascriptExecutor) driver;
            webElement = driver.findElement(By.xpath("(//a[normalize-space()='Confirm'])[1]"));
            js.executeScript("arguments[0].scrollIntoView();", webElement);

            SaveCaptcha_Image saveCaptchaImage = new SaveCaptcha_Image(driver, webElement, "E:\\CongViecHocTap\\Captcha\\", "captcha.png");
            saveCaptchaImage.getCaptcha();
            SendPhoto();

            System.out.println("done image");

            int countdownDuration = 30;
            SendMessage message = new SendMessage();
            message.setChatId("1159534870"); // update.getMessage().getChatId().toString()
            int zero = countdownDuration;
            for (int second = 0; second <= countdownDuration; second++) {
                zero = countdownDuration - second;
                System.out.println(zero);

                Thread.sleep(1000);

                if (text != null) {
                    System.out.println("text : " + text);
                    for (int i = 0; i < text.length(); i++) {
                        if (!Character.isDigit(text.charAt(i))) {
                            message.setText("Value of text has char");
                            execute(message);
                            text = null;
                            break;
                        }
                    }
                    if (text == null || text.isEmpty() || text.length() <= 3) {
                        if (text == null) {
                        } else {
                            message.setText("Text length must be 4 numbers or more");
                            execute(message);
                        }
                        text = null;
                        continue;
                    }

                    latch = new CountDownLatch(1);
                    threadCheckHandAD = new Thread(new CheckHandAD(driver, latch));
                    threadCheckHandAD.start();
                    latch.await();

                    driver.findElement(By.xpath("(//input[@id='captcha_input'])[1]")).sendKeys(text);
                    element_solve = driver.findElements(By.xpath("(//img[@title='Ad.Plus Advertising'])[1]"));
                    if (element_solve.size() > 0 && element_solve.get(0).isDisplayed()) {
                        driver.findElement(By.xpath("(//img[@title='Ad.Plus Advertising'])[1]")).click();
                    }
                    driver.findElement(By.xpath("(//a[normalize-space()='Confirm'])[1]")).click();
                    try {
                        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//h4[normalize-space()='Error!'])[1]")));
                        message.setText("Wrong number of captcha image , type again !");
                        execute(message);
                        text = null;
                        saveCaptchaImage.getCaptcha();
                        SendPhoto();
                        continue;

                    } catch (Exception exception) {
                        flag = true;
                        text = null;
                        message.setText("Valid captcha code");
                        execute(message);

                        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Tạo Voice')]"))).click();

                        break;
                    }
                }
                System.out.println("---------------------");
            }
            System.out.println("count here");
            System.out.println(zero);
            if (zero == 0) {
                System.out.println("count ");
                message.setText("End of time to solove captcha");
                execute(message);
                driver.close();
                //      return ResponseEntity.ok(new String("End of time to solove captcha"));
            }

        } catch (Exception e) {
            System.out.println("Non display captcha");
        }


        if (flag == true) {
            flag = false;
            System.out.println("flag == true");
            try {
                wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("laptopaz.com"))).click();
            } catch (Exception exception) {

            }
        } else {
        }

        latch = new CountDownLatch(1);
        threadCheckHandAD = new Thread(new CheckHandAD(driver, latch));
        threadCheckHandAD.start();
        latch.await();

        js = (JavascriptExecutor) driver;
        webElement = driver.findElement(By.xpath("//*[@id=\"input_text\"]"));
        js.executeScript("arguments[0].scrollIntoView();", webElement);

        wait = new WebDriverWait(driver, Duration.ofSeconds(120));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"progessResults\"]/div[2]/center[1]/div/a"))).click();
        latch = new CountDownLatch(1);
        Thread threadCheckHostAD = new Thread(new CheckHostAD(driver, latch));
        threadCheckHostAD.start();
        latch.await();


        /**
         * đổi tên file theo yêu cầu user ( đơn luồng thì hoạt động oke , đa luồng thì lỗi -> đang nghiên cứu login 1 lúc có request cùng đổi để đảm bảo không có lỗi xảy ra
         * đang nghiên cứu để update
         */
//        File folder = new File("E:\\New folder");
//        File[] files = folder.listFiles();
//        if (files != null && files.length > 0) {
//            Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());
//            File latestFile = files[0];
//            System.out.println(latestFile.getName());
//            String newFileName = params.get("FileName") + ".mp3";
//            File newFile = new File(folder, newFileName);
//            latestFile.renameTo(newFile);
//        }

        // changeFileName(params);

        Thread.sleep(10000);
        driver.close();

        ResponseEntity<?> response = notification(); // Gọi testNotif2() và chờ phản hồi
        Object body = response.getBody(); // Lấy phần thân của phản hồi
        return ResponseEntity.ok(new String(String.valueOf(body)));
//        try {
//            wait = new WebDriverWait(driver, Duration.ofSeconds(1));
//            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("laptopaz.com"))).click();
//        } catch (Exception exception) {
//            if (flag_TextCSV_LessOrEqual4000Char == true) {
//                driver.close();
//            } else if (flag_TextCSV_LessOrEqual4000Char == false) {
//            }
//        }
        //   return ResponseEntity.ok(new String("End of time to solove captcha"));
    }

    @GetMapping("/testNotif1")
    public ResponseEntity<?> testNotif1() throws InterruptedException, IOException, TelegramApiException {
        ResponseEntity<?> response = testNotif2(); // Gọi testNotif2() và chờ phản hồi
        Object body = response.getBody(); // Lấy phần thân của phản hồi
        return ResponseEntity.ok(new String(String.valueOf(body)));
    }

    @GetMapping("/testNotif2")
    public ResponseEntity<?> testNotif2() throws InterruptedException, IOException, TelegramApiException {
        return ResponseEntity.ok(new String("notif test2"));
    }

    @GetMapping("/notification")
    public ResponseEntity<?> notification() throws InterruptedException, IOException, TelegramApiException {
        return ResponseEntity.ok(new String("download 200 ok"));
    }
}

/**
 * cd C:\Program Files\Google\Chrome\Application
 * chrome.exe --remote-debugging-port=9222 --user-data-dir="E:\CongViecHocTap\ChromeData"
 * chrome.exe --remote-debugging-port=9222 --user-data-dir="D:\New folder\ChromeDriver\chromedriver-win64"
 * <p>
 * chrome://settings/content/popups
 */


class kao{
    public static void main(String[] args) throws TelegramApiException, IOException, InterruptedException {
        Map<String, String> params = new HashMap<>();
        Selenium selenium = new Selenium();
        selenium.Initialization_Driver(params);
    }
}