package com.gokarts.gokart_reservation.controller;

import com.gokarts.gokart_reservation.model.User;
import com.gokarts.gokart_reservation.repository.UserRepository;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class HomeController {
    @GetMapping
    public String showLoginForm() {
        return "index";
    }
}
