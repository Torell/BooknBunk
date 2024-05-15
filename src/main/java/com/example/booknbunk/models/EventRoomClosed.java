package com.example.booknbunk.models;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("RoomClosed")
@Data
@NoArgsConstructor
public class EventRoomClosed extends Event{

    public EventRoomClosed(LocalDateTime timeStamp, Room room) {
        super(timeStamp, room);
    }
}
