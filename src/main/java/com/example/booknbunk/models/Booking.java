package com.example.booknbunk.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn
    @NonNull
    private Room room;
    private int extraBed;

    @ManyToOne
    @JoinColumn
    @NonNull
    private Customer customer;

    public Booking(LocalDate startDate, LocalDate endDate, @NonNull Room room, int extraBed, @NonNull Customer customer) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.room = room;
        this.extraBed = extraBed;
        this.customer = customer;
    }
}
