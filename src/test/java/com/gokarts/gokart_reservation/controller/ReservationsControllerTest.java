package com.gokarts.gokart_reservation.controller;

import com.gokarts.gokart_reservation.CustomUserDetails;
import com.gokarts.gokart_reservation.model.Reservations;
import com.gokarts.gokart_reservation.model.User;
import com.gokarts.gokart_reservation.repository.ReservationsRepository;
import com.gokarts.gokart_reservation.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationsControllerTest {

    @Mock
    private ReservationsRepository reservationsRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ReservationsController reservationsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAvailableTimes() {
        String date = "2025-02-14";
        LocalDateTime start = LocalDateTime.parse(date + "T00:00:00");
        LocalDateTime end = start.plusHours(23).plusMinutes(59);

        Reservations res1 = new Reservations();
        res1.setDataStartu(start.plusHours(10));
        res1.setDataKonca(start.plusHours(11));
        res1.setStatus("zatwierdzona");

        when(reservationsRepository.findByDataStartuBetween(start, end)).thenReturn(List.of(res1));

        List<LocalTime> availableTimes = reservationsController.getAvailableTimes(date);
        assertFalse(availableTimes.contains(LocalTime.of(10, 0)));
        assertTrue(availableTimes.contains(LocalTime.of(9, 0)));
    }

    @Test
    void testBookReservation() {
        CustomUserDetails userDetails = mock(CustomUserDetails.class);
        when(userDetails.getId()).thenReturn(1);

        User mockUser = mock(User.class);

        when(userRepository.findById(1)).thenReturn(mockUser);

        String result = reservationsController.bookReservation(userDetails, "2025-02-14", "10:00", 2, 3);

        verify(reservationsRepository, times(1)).save(any(Reservations.class));
        assertEquals("redirect:/reservations?success", result);
    }
}
