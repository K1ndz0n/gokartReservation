package com.gokarts.gokart_reservation.controller;

import com.gokarts.gokart_reservation.CustomUserDetails;
import com.gokarts.gokart_reservation.model.Reservations;
import com.gokarts.gokart_reservation.repository.ReservationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/userreservations")
public class UserReservationsController {

    @Autowired
    private ReservationsRepository reservationsRepository;
    @GetMapping
    public String history(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        List<Reservations> userReservations = reservationsRepository.findByUserId(userDetails.getId());
        model.addAttribute("userreservations", userReservations);
        return "userreservations";
    }

    @PostMapping("/pay")
    public String payReservation(@RequestParam("id") Integer reservationId) {
        Optional<Reservations> optionalReservation = reservationsRepository.findById(reservationId);
        if (optionalReservation.isPresent()) {
            Reservations reservation = optionalReservation.get();
            reservation.setStatus("potwierdzona");
            reservationsRepository.save(reservation);
        }
        return "redirect:/userreservations";
    }

    @PostMapping("/cancel")
    public String cancelReservation(@RequestParam("id") Integer reservationId) {
        Optional<Reservations> optionalReservation = reservationsRepository.findById(reservationId);
        if (optionalReservation.isPresent()) {
            Reservations reservation = optionalReservation.get();
            reservation.setStatus("anulowana");
            reservationsRepository.save(reservation);
        }
        return "redirect:/userreservations";
    }


}
