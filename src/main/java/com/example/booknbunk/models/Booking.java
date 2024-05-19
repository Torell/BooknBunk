package com.example.booknbunk.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;


@Entity
@Builder
@Data
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
    private double totalPrice;


    public Booking(Long id, LocalDate startDate, LocalDate endDate, @NonNull Room room, int extraBed, @NonNull Customer customer, double totalPrice) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.room = room;
        this.extraBed = extraBed;
        this.customer = customer;
        this.totalPrice = totalPrice;
    }

    public Booking(Long id, LocalDate startDate, LocalDate endDate, @NonNull Room room, int extraBed, @NonNull Customer customer) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.room = room;
        this.extraBed = extraBed;
        this.customer = customer;
    }

    public Booking(LocalDate startDate, LocalDate endDate, @NonNull Room room, int extraBed, @NonNull Customer customer) {

        this.room = room;
        this.extraBed = extraBed;
        this.customer = customer;
    }


}
