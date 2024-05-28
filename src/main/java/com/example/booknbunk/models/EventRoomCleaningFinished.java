package com.example.booknbunk.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("RoomCleaningFinished")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventRoomCleaningFinished extends Event{

    @JsonProperty("CleaningByUser")
    @Column(name = "cleaning_by_user")
    private String cleaningByUser;

    public EventRoomCleaningFinished(LocalDateTime timeStamp, Room room, String cleaningByUser) {
        super(room, timeStamp);
        this.cleaningByUser = cleaningByUser;
    }
}