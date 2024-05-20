package com.example.booknbunk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("DoorEvent")
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventRoomDoor extends Event{

    @JsonProperty("DoorEventType")
    @Column(name = "door_event_type")
    private String doorEventType;

    public EventRoomDoor(LocalDateTime timeStamp, Room room, String eventDetail, String eventType) {
        super(timeStamp, room, eventDetail);
        this.doorEventType = eventType;
    }

}
