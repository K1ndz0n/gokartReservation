package com.gokarts.gokart_reservation.repository;

import com.gokarts.gokart_reservation.model.Reservations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface ReservationsRepository extends JpaRepository<Reservations, Integer> {
    List<Reservations> findByUserId(Integer userId);
    List<Reservations> findByDataStartuBetween(LocalDateTime start, LocalDateTime end);
}
