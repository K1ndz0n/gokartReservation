package com.gokarts.gokart_reservation.repository;

import com.gokarts.gokart_reservation.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveAndFindUserById() {
        User user = new User();
        user.setImie("Jan");
        user.setNazwisko("Kowalski");
        user.setEmail("jan@example.com");
        user.setHaslo("password123");

        User savedUser = userRepository.save(user);
        Optional<User> foundUser = Optional.ofNullable(userRepository.findById(savedUser.getId()));

        assertTrue(foundUser.isPresent());
        assertEquals("Jan", foundUser.get().getImie());
        assertEquals("Kowalski", foundUser.get().getNazwisko());
    }

    @Test
    void shouldFindUserByEmail() {
        User user = new User();
        user.setImie("Anna");
        user.setNazwisko("Nowak");
        user.setEmail("anna@example.com");
        user.setHaslo("securepass");
        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByEmail("anna@example.com");

        assertTrue(foundUser.isPresent());
        assertEquals("Anna", foundUser.get().getImie());
    }

    @Test
    void shouldReturnEmptyWhenUserNotFound() {
        Optional<User> foundUser = userRepository.findByEmail("notfound@example.com");

        assertFalse(foundUser.isPresent());
    }
}
