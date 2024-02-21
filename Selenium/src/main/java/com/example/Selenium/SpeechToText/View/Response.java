package com.example.Selenium.SpeechToText.View;

import com.example.Selenium.SpeechToText.Controller.AllProcessController;
import com.example.Selenium.SpeechToText.Model.DataStoreModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/web")
public class Response {

    @GetMapping("/SpeechToText")
    public ResponseEntity<?> SpeechToText(String content) {
//        AllProcessController allProcessController = new AllProcessController();
//        allProcessController.work();
        return ResponseEntity.ok(new String(content));
    }
}
