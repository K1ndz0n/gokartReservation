package com.gokarts.gokart_reservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class TestController {

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/test-i18n")
    public String testMessage(@RequestParam String lang) {
        Locale locale = new Locale(lang);
        return messageSource.getMessage("welcome.message", null, locale);
    }
}
