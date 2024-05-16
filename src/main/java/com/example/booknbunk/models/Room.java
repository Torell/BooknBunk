package com.example.booknbunk.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("room_id")
    private Long id;
    //String size
    private int roomSize;
    private double pricePerNight;

    @OneToMany(mappedBy = "room")
    private List<Booking> bookings;

    //ett rum kan ha många events
    @OneToMany(mappedBy = "room")
    private List<Event> events;

    public Room(int roomSize) {
        this.roomSize = roomSize;
    }

    public Room(Long id, int roomSize, List<Booking> bookings) {
        this.id = id;
        this.roomSize = roomSize;
        this.bookings = bookings;
    }

    public Room(Long id, int roomSize) {
        this.id = id;
        this.roomSize = roomSize;
    }
}
