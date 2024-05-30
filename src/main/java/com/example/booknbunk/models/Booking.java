package com.example.booknbunk.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
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
    @NotNull
    @FutureOrPresent(message = "Start date should be present or future")
    private LocalDate startDate;
    @NotNull
    @FutureOrPresent(message = "End date should be present or future")
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn
    @NonNull
    private Room room;
    @PositiveOrZero(message = "Extra beds should be positive")
    private int extraBed;

    @ManyToOne
    @JoinColumn
    @NonNull
    private Customer customer;

    @PositiveOrZero(message = "Total price should be positive")
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
