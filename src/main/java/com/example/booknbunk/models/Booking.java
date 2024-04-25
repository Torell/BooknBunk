package com.example.booknbunk.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;


@Entity
@Builder
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

    public Booking(LocalDate startDate,LocalDate endDate, @NonNull Room room, int extraBed, @NonNull Customer customer) {

        this.room = room;
        this.extraBed = extraBed;
        this.customer = customer;
    }
}
