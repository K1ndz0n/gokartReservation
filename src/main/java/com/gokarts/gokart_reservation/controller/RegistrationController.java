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
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    MessageSource messageSource;

    @GetMapping
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
    @PostMapping
    public String registerUser(@Valid @ModelAttribute User user, Model model, Locale locale) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("error", messageSource.getMessage("error.email.exists", null, locale));
            return "register";
        }

        user.setHaslo(passwordEncoder.encode(user.getHaslo()));
        userRepository.save(user);

        return "redirect:/login";
    }
}
