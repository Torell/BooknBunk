package com.example.booknbunk.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

//Todo: Lägg till kardinalitet

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue
    private Long id;
    //Ska vi ha Localdate för bokingar?
    LocalDate date;
    int extraBed;

    public Booking(LocalDate date, int extraBed){
        this.date = date;
        this.extraBed = extraBed;
    }
}
