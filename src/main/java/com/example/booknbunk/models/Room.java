package com.example.booknbunk.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
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

    @OneToMany(mappedBy = "room")
    private List<Booking> bookings;



    public Room(int roomSize) {
        this.roomSize = roomSize;
    }


}
