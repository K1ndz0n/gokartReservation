package com.gokarts.gokart_reservation.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
public class Reservations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "ilosc_osob", nullable = false)
    private Integer iloscOsob;

    @Column(name = "data_startu", nullable = false)
    private LocalDateTime dataStartu;

    @Column(name = "data_konca", nullable = false)
    private LocalDateTime dataKonca;

    @Column(name = "status", length = 15, nullable = false)
    private String status;

    @Column(name = "cena_calosciowa", precision = 10, scale = 2, nullable = false)
    private BigDecimal cenaCalkowita;

    public Reservations() {}

    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Integer getIloscOsob() {
        return iloscOsob;
    }

    public LocalDateTime getDataStartu() {
        return dataStartu;
    }

    public LocalDateTime getDataKonca() {
        return dataKonca;
    }

    public String getStatus() {
        return status;
    }

    public BigDecimal getCenaCalkowita() {
        return cenaCalkowita;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setIloscOsob(Integer iloscOsob) {
        this.iloscOsob = iloscOsob;
    }

    public void setDataStartu(LocalDateTime dataStartu) {
        this.dataStartu = dataStartu;
    }

    public void setDataKonca(LocalDateTime dataKonca) {
        this.dataKonca = dataKonca;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCenaCalkowita(BigDecimal cenaCalkowita) {
        this.cenaCalkowita = cenaCalkowita;
    }
}
