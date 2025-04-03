package com.gokarts.gokart_reservation.controller;

import com.gokarts.gokart_reservation.model.User;
import com.gokarts.gokart_reservation.repository.UserRepository;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Set;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    MessageSource messageSource;
    @GetMapping
    public String login(@RequestParam(value = "error", required = false) String error, Model model, Locale locale) {
        if (error != null) {
            model.addAttribute("error", messageSource.getMessage("error.login.invalid", null, locale));
        }
        return "login";
    }
}
