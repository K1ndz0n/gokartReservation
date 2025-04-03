package com.gokarts.gokart_reservation.controller;

import com.gokarts.gokart_reservation.CustomUserDetails;
import com.gokarts.gokart_reservation.model.Reservations;
import com.gokarts.gokart_reservation.repository.ReservationsRepository;
import com.gokarts.gokart_reservation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/reservations")
public class ReservationsController {

    @Autowired
    private ReservationsRepository reservationsRepository;
    @Autowired
    private UserRepository userRepository;

    public List<LocalTime> getAvailableTimes(@RequestParam String date) {
        LocalDateTime startOfDay = LocalDateTime.parse(date + "T00:00:00");
        LocalDateTime endOfDay = startOfDay.plusHours(23).plusMinutes(59);

        List<Reservations> reservations = reservationsRepository.findByDataStartuBetween(startOfDay, endOfDay)
                .stream()
                .filter(reservation -> !reservation.getStatus().equals("anulowana"))
                .toList();

        List<LocalTime> takenSlots = new ArrayList<>();
        for (Reservations reservation : reservations) {
            LocalTime time = reservation.getDataStartu().toLocalTime();
            while (time.isBefore(reservation.getDataKonca().toLocalTime())) {
                takenSlots.add(time);
                time = time.plusMinutes(15);
            }
        }

        return generateAvailableSlots(takenSlots);
    }


    private List<LocalTime> generateAvailableSlots(List<LocalTime> takenSlots) {
        List<LocalTime> allSlots = new ArrayList<>();
        LocalTime time = LocalTime.of(9, 0);

        while (time.isBefore(LocalTime.of(22, 0))) {
            allSlots.add(time);
            time = time.plusMinutes(15);
        }

        return allSlots.stream()
                .filter(slot -> !takenSlots.contains(slot))
                .toList();
    }

    @GetMapping
    public String showReservationForm(Model model) {
        LocalDate today = LocalDate.now();
        List<LocalTime> availableTimes = getAvailableTimes(today.toString());
        model.addAttribute("availableTimes", availableTimes);
        model.addAttribute("selectedDate", today);
        return "reservations";
    }

    @PostMapping("/book")
    public String bookReservation(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam String date,
            @RequestParam String startTime,
            @RequestParam int numCourses,
            @RequestParam Integer peopleAmount) {

        LocalDateTime startDateTime = LocalDateTime.parse(date + "T" + startTime);
        LocalDateTime endDateTime = startDateTime.plusMinutes(numCourses * 15);


        Reservations reservation = new Reservations();
        reservation.setUser(userRepository.findById(userDetails.getId()));
        reservation.setIloscOsob(peopleAmount);
        reservation.setDataStartu(startDateTime);
        reservation.setDataKonca(endDateTime);
        reservation.setStatus("oczekująca");
        reservation.setCenaCalkowita(BigDecimal.valueOf(numCourses * 40));

        reservationsRepository.save(reservation);

        return "redirect:/reservations?success";
    }




}
