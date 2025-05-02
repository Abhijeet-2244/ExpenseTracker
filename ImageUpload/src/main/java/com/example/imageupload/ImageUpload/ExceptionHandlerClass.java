package com.example.imageupload.ImageUpload;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
public class ExceptionHandlerClass {

    @GetMapping("/test")
    public String generateException() {
        String str = null;
        int size = str.length(); // This will throw NullPointerException when this endpoint is hit
        return "Size is: " + size;
    }

    @ExceptionHandler(NullPointerException.class)
    public String handleException(NullPointerException exc) {
        return "NullPointerException occurred: " + exc.getMessage();
    }
}
