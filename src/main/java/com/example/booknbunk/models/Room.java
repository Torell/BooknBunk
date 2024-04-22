package com.example.booknbunk.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {

    //Todo: Lägg till kardinalitet

    @Id
    @GeneratedValue
    private Long id;
    //String size
    private int roomSize;

    public Room(int roomSize) {
        this.roomSize = roomSize;
    }
}
