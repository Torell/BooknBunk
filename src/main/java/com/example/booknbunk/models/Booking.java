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
import java.time.Period;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue
    private Long id;
    private Period bookingPeriod;

    @ManyToOne
    @JoinColumn
    @NonNull
    private Room room;
    private int extraBed;

    @ManyToOne
    @JoinColumn
    @NonNull
    private Customer customer;

    public Booking(Period bookingPeriod, @NonNull Room room, int extraBed, @NonNull Customer customer) {

        this.room = room;
        this.extraBed = extraBed;
        this.customer = customer;
    }
}
