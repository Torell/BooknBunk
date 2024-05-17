package com.example.booknbunk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("RoomDoor")
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventRoomDoor extends Event{

    public EventRoomDoor(LocalDateTime timeStamp, Room room, String eventDetail) {
        super(timeStamp, room, eventDetail);
    }

}
