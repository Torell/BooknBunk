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

    @JsonProperty("CleaningStatus")
    @Column(name = "cleaning_status")
    private String cleaningStatus;


    public EventRoomCleaning(LocalDateTime timeStamp, Room room, String cleaningByUser, String cleaningStatus) {
        super(room, timeStamp);
        this.cleaningByUser = cleaningByUser;
        this.cleaningStatus = cleaningStatus;
    }
}
