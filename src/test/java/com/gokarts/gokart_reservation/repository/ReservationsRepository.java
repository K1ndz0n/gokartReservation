package com.gokarts.gokart_reservation.repository;

import com.gokarts.gokart_reservation.model.Reservations;
import com.gokarts.gokart_reservation.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")
class ReservationsRepositoryTest {

    @Autowired
    private ReservationsRepository reservationsRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveAndFindReservation() {
        User user = new User();
        user.setImie("testuser");
        user.setNazwisko("nazwisko");
        user.setHaslo("password");
        user.setEmail("test@example.com");
        user = userRepository.save(user);

        Reservations reservation = new Reservations();
        reservation.setUser(user);
        reservation.setIloscOsob(4);
        reservation.setDataStartu(LocalDateTime.now().plusDays(1));
        reservation.setDataKonca(LocalDateTime.now().plusDays(1).plusHours(2));
        reservation.setStatus("potwierdzona");
        reservation.setCenaCalkowita(new BigDecimal("199.99"));

        Reservations savedReservation = reservationsRepository.save(reservation);

        assertThat(savedReservation.getId()).isNotNull();
        assertThat(savedReservation.getUser().getId()).isEqualTo(user.getId());
        assertThat(savedReservation.getIloscOsob()).isEqualTo(4);
    }

    @Test
    void testFindByUserId() {
        User user = new User();
        user.setImie("john_doe");
        user.setHaslo("securepass");
        user.setNazwisko("nazwisko");
        user.setEmail("john@example.com");
        user = userRepository.save(user);

        Reservations res1 = new Reservations();
        res1.setUser(user);
        res1.setIloscOsob(2);
        res1.setDataStartu(LocalDateTime.now().plusDays(2));
        res1.setDataKonca(LocalDateTime.now().plusDays(2).plusHours(1));
        res1.setStatus("potwierdzona");
        res1.setCenaCalkowita(new BigDecimal("99.99"));
        reservationsRepository.save(res1);

        Reservations res2 = new Reservations();
        res2.setUser(user);
        res2.setIloscOsob(3);
        res2.setDataStartu(LocalDateTime.now().plusDays(3));
        res2.setDataKonca(LocalDateTime.now().plusDays(3).plusHours(2));
        res2.setStatus("anulowana");
        res2.setCenaCalkowita(new BigDecimal("149.99"));
        reservationsRepository.save(res2);

        List<Reservations> reservations = reservationsRepository.findByUserId(user.getId());
        assertThat(reservations).hasSize(2);
    }

    @Test
    void testFindByDataStartuBetween() {
        User user = new User();
        user.setImie("time_tester");
        user.setHaslo("passtest");
        user.setEmail("time@example.com");
        user.setNazwisko("nazwisko");
        user = userRepository.save(user);

        Reservations res1 = new Reservations();
        res1.setUser(user);
        res1.setIloscOsob(2);
        res1.setDataStartu(LocalDateTime.of(2024, 6, 1, 10, 0));
        res1.setDataKonca(LocalDateTime.of(2024, 6, 1, 12, 0));
        res1.setStatus("anulowana");
        res1.setCenaCalkowita(new BigDecimal("100.00"));
        reservationsRepository.save(res1);

        Reservations res2 = new Reservations();
        res2.setUser(user);
        res2.setIloscOsob(4);
        res2.setDataStartu(LocalDateTime.of(2024, 6, 5, 14, 0));
        res2.setDataKonca(LocalDateTime.of(2024, 6, 5, 16, 0));
        res2.setStatus("potwierdzona");
        res2.setCenaCalkowita(new BigDecimal("250.00"));
        reservationsRepository.save(res2);

        List<Reservations> reservations = reservationsRepository.findByDataStartuBetween(
                LocalDateTime.of(2024, 6, 1, 0, 0),
                LocalDateTime.of(2024, 6, 4, 23, 59)
        );

        assertThat(reservations).hasSize(1);
        assertThat(reservations.get(0).getDataStartu()).isEqualTo(res1.getDataStartu());
    }
}
