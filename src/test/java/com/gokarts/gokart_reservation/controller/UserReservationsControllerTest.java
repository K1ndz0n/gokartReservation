package com.gokarts.gokart_reservation.controller;

import com.gokarts.gokart_reservation.CustomUserDetails;
import com.gokarts.gokart_reservation.model.Reservations;
import com.gokarts.gokart_reservation.repository.ReservationsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserReservationsControllerTest {

    @Mock
    private ReservationsRepository reservationsRepository;

    @Mock
    private Model model;

    @Mock
    private CustomUserDetails userDetails;

    @InjectMocks
    private UserReservationsController userReservationsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHistory() {
        int userId = 1;
        List<Reservations> reservationsList = List.of(new Reservations());
        when(userDetails.getId()).thenReturn(userId);
        when(reservationsRepository.findByUserId(userId)).thenReturn(reservationsList);

        String viewName = userReservationsController.history(userDetails, model);

        assertEquals("userreservations", viewName);
        verify(model, times(1)).addAttribute("userreservations", reservationsList);
    }

    @Test
    void testPayReservation() {
        int reservationId = 10;
        Reservations reservation = new Reservations();
        reservation.setStatus("oczekująca");

        when(reservationsRepository.findById(reservationId)).thenReturn(Optional.of(reservation));

        String viewName = userReservationsController.payReservation(reservationId);

        assertEquals("redirect:/userreservations", viewName);
        assertEquals("potwierdzona", reservation.getStatus());
        verify(reservationsRepository, times(1)).save(reservation);
    }

    @Test
    void testCancelReservation() {
        int reservationId = 10;
        Reservations reservation = new Reservations();
        reservation.setStatus("oczekująca");

        when(reservationsRepository.findById(reservationId)).thenReturn(Optional.of(reservation));

        String viewName = userReservationsController.cancelReservation(reservationId);

        assertEquals("redirect:/userreservations", viewName);
        assertEquals("anulowana", reservation.getStatus());
        verify(reservationsRepository, times(1)).save(reservation);
    }

    @Test
    void testPayReservation_NotFound() {
        int reservationId = 99;
        when(reservationsRepository.findById(reservationId)).thenReturn(Optional.empty());

        String viewName = userReservationsController.payReservation(reservationId);

        assertEquals("redirect:/userreservations", viewName);
        verify(reservationsRepository, never()).save(any());
    }

    @Test
    void testCancelReservation_NotFound() {
        int reservationId = 99;
        when(reservationsRepository.findById(reservationId)).thenReturn(Optional.empty());

        String viewName = userReservationsController.cancelReservation(reservationId);

        assertEquals("redirect:/userreservations", viewName);
        verify(reservationsRepository, never()).save(any());
    }
}
