package com.gokarts.gokart_reservation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")

public class User {

    public User() {}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Name is required")
    private String imie;

    @NotBlank(message = "Surname is required")
    private String nazwisko;

    @NotBlank(message = "Email is required")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Password is required")
    private String haslo;

    @OneToMany(mappedBy = "user")
    private List<Reservations> reservations;

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getHaslo() {
        return haslo;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }
}
