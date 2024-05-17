package com.example.booknbunk.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("RoomCleaning")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventRoomCleaning extends Event{


    @JsonProperty("CleaningByUser")
    @Column(name = "cleaning_by_user")
    private String cleaningByUser;


    public EventRoomCleaning(LocalDateTime timeStamp, Room room, String eventDetail, String cleaningByUser) {
        super(timeStamp, room, eventDetail);
        this.cleaningByUser = cleaningByUser;

    }
}
