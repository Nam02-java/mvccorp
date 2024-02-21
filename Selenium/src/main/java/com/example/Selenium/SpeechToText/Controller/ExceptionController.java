package com.example.Selenium.SpeechToText.Controller;

public class ExceptionController extends Exception{

    public ExceptionController(String exceptionNotification) {
        super(exceptionNotification);
    }

    public ExceptionController(String exceptionNotification, Throwable cause) {
        super(exceptionNotification, cause);
    }

    public ExceptionController(Throwable cause) {
        super(cause);
    }
}
class test{
    public static void main(String[] args) {
        System.err.println("File không tồn tại: ");
    }
}