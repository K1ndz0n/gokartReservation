package com.gokarts.gokart_reservation.controller;

import com.gokarts.gokart_reservation.model.User;
import com.gokarts.gokart_reservation.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;

import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegistrationControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MessageSource messageSource;

    @Mock
    private Model model;

    @InjectMocks
    private RegistrationController registrationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser_Success() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setHaslo("plainPassword");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("plainPassword")).thenReturn("hashedPassword");

        String viewName = registrationController.registerUser(user, model, Locale.ENGLISH);

        assertEquals("redirect:/login", viewName);
        assertEquals("hashedPassword", user.getHaslo());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testRegisterUser_EmailExists() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setHaslo("plainPassword");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(new User()));
        when(messageSource.getMessage("error.email.exists", null, Locale.ENGLISH))
                .thenReturn("Email already exists");

        String viewName = registrationController.registerUser(user, model, Locale.ENGLISH);

        assertEquals("register", viewName);
        verify(userRepository, never()).save(any(User.class));
        verify(model, times(1)).addAttribute("error", "Email already exists");
    }
}
