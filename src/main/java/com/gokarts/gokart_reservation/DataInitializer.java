package com.gokarts.gokart_reservation;

import com.gokarts.gokart_reservation.model.User;
import com.gokarts.gokart_reservation.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    public DataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            User user = new User();
            user.setImie("Adam");
            user.setNazwisko("Nowak");
            user.setEmail("testuser@test.com");
            user.setHaslo("123");
            userRepository.save(user);
            System.out.println("User created successfully.");
        }
    }
}
