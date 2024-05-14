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
@NoArgsConstructor
@AllArgsConstructor
public class Room {


    @Id
    @GeneratedValue
    private Long id;
    //String size
    private int roomSize;

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
